package io.github.recursivejr.discenteVivo.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.Interface.RelatorioDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.filters.FilterSecurityAuthentication;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Relatorio;

@Path("relatorio")
public class RelatorioController {
	
    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gerarRelatorios/enquete/{param}/")
    //Variavel param pode ser a Id da Enquete ou o Nome da Enquete
    public Response gerarRelatorios(@PathParam("param") String param, @Context ContainerRequestContext requestContext) {

        //Cria um Relatorio contando nada
        Relatorio relatorio = null;

        //Checa se e Administrador, se nao for retorna UNAUTHORIZED
        if (!FilterSecurityAuthentication.checkAdmin(requestContext))
            return Response.status(Response.Status.UNAUTHORIZED).build();


        try {
            //Intancia um relatorioDaoPostgres usando a fabrica acessando somente os metodos definidos na Interface
            RelatorioDaoInterface relatorioDao = new FabricaDaoPostgres().criarRelatorioDao();

           try {
               //tenta gerar os relatorios com base no ID, se ao converter o param de String para Integer retornar erro
                    //entao o param contem letras e deve gerar os relatorios com base no nome
               relatorio = relatorioDao.gerarRelatorio(Integer.parseInt(param));
           } catch (NumberFormatException nFE) {
                //caso tenha sido gerado uma Exception na conversao para Integer entao trata-se o param como o nome da enquete
                    //e gera-se os relatorios com base nela
                relatorio = relatorioDao.gerarRelatorio(param);
           }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.gc();
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        System.gc();
        //Sempre retorna relatorios sendo ele null ou preenchido
        return Response.ok(relatorio).build();
    }

}
