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
    public Response gerarRelatoriosEnquete(@PathParam("idEnquete") int idEnquete,
                                    @Context Request request) {

        if (idEnquete <= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

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
            builder = Response.ok(relatorio);
            builder.tag(etag);
        }

        builder.cacheControl(CacheController.getCacheControl());
        return builder.build();
    }

    @GET
    @Security(NivelAcesso.NIVEL_1)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gerarRelatorios/formulario/{idFormulario}")
    public Response gerarRelatoriosFormulario(@PathParam("idFormulario") int idFormulario,
                                    @Context Request request) {


        if (idFormulario <= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        //Cria um Relatorio contando nada
        Relatorio relatorio = null;

        try {
            //Intancia um relatorioDaoPostgres usando a fabrica acessando somente os metodos definidos na Interface
            RelatorioDaoInterface relatorioDao = Fabrica.criarFabricaDaoPostgres().criarRelatorioDao();

            //tenta gerar os relatorios com base no ID da idEnquete
            relatorio = relatorioDao.gerarRelatorioFormulario(idFormulario);

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
            builder = Response.ok(relatorio);
            builder.tag(etag);
        }

        builder.cacheControl(CacheController.getCacheControl());
        return builder.build();
    }

}
