package io.github.recursivejr.discenteVivo.controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import io.github.recursivejr.discenteVivo.dao.Interface.RelatorioDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.infraSecurity.CacheController;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.Relatorio;

@Path("relatorio")
public class RelatorioController {
	
    @GET
    @Security(NivelAcesso.NIVEL_1)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gerarRelatorios/enquete/{idEnquete}")
    //Variavel param pode ser a Id da Enquete ou o Nome da Enquete
    public Response gerarRelatorios(@PathParam("idEnquete") int idEnquete,
                                    @Context Request request) {

        //Cria um Relatorio contando nada
        Relatorio relatorio = null;

        try {
            //Intancia um relatorioDaoPostgres usando a fabrica acessando somente os metodos definidos na Interface
            RelatorioDaoInterface relatorioDao = Fabrica.criarFabricaDaoPostgres().criarRelatorioDao();

            //tenta gerar os relatorios com base no ID da idEnquete
            relatorio = relatorioDao.gerarRelatorioEnquete(idEnquete);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.gc();
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        System.gc();
        //Sempre retorna relatorios sendo ele null ou preenchido

        EntityTag etag = new EntityTag(Integer.toString(relatorio.hashCode()));
        Response.ResponseBuilder builder = request.evaluatePreconditions(etag);

        if (builder == null) {
            builder.status(Response.Status.OK);
            builder.tag(etag);
        }

        builder.cacheControl(CacheController.getCacheControl());
        return builder.build();
    }

}
