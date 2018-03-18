package io.github.recursivejr.discenteVivo.controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import io.github.recursivejr.discenteVivo.dao.Interface.RelatorioDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.RelatorioEnquete;

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

        //Cria um RelatorioEnquete contando nada
        RelatorioEnquete relatorio = null;

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

        //Sempre retorna relatorios sendo ele null ou preenchido
        System.gc();
        return Response.ok(relatorio).build();

    }

    @GET
    @Security(NivelAcesso.NIVEL_1)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gerarRelatorios/formulario/{idFormulario}")
    public Response gerarRelatoriosFormulario(@PathParam("idFormulario") int idFormulario,
                                    @Context Request request) {


        if (idFormulario <= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        //Cria um RelatorioEnquete contando nada
        RelatorioEnquete relatorio = null;

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

        //Sempre retorna relatorios sendo ele null ou preenchido
        System.gc();
        return Response.ok(relatorio).build();
    }

}
