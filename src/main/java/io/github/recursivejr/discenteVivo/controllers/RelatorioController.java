package io.github.recursivejr.discenteVivo.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.RelatorioDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Relatorio;

@Path("relatorio")
public class RelatorioController {
	
	@GET
	@Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getRelatorios")
    public List<Relatorio> getRelatorios(@Context ContainerRequestContext requestContext) {

	    return generateRelatios(null, requestContext);
    }

    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getRelatorios/enquete/{param}")
    //Variavel param pode ser a Id da Enquete ou o Nome da Enquete
    public List<Relatorio> getRelatios(@PathParam("param") String param, @Context ContainerRequestContext requestContext) {

        return generateRelatios(param, requestContext);
    }

    public List<Relatorio> generateRelatios(String param, ContainerRequestContext requestContext) {

        //Cria uma lista de relatorios contando nada
        List<Relatorio> relatorios = null;

        //Checa se e Administrador, se nao for retorna UNAUTHORIZED
        if (!FilterDetect.checkAdmin(requestContext)) {
            Response.status(Response.Status.UNAUTHORIZED).build();
            return relatorios;
        }

        try {
            //Intancia um relatorioDaoPostgres usando a fabrica acessando somente os metodos definidos na Interface
            RelatorioDaoInterface relatorioDao = new FabricaDaoPostgres().criarRelatorioDao();

            //Se for null entao nao foi recebido nenhum parametro logo deve-se retornar todos os relatorios
            if(param == null)
                //A Lista de Relatorios recebe todos os relatorios pelo Objeto RelatorioDaoPostgres
                relatorios = relatorioDao.gerarRelatorio();
            //caso param nao seja null entao foi passado algum parametro dai gera-se apenas relatorios de uma enquete esppecifica
            else {
                try {
                    //tenta gerar os relatorios com base no ID, se ao converter o param de String para Integer retornar erro
                    //entao o param contem letras e deve gerar os relatorios com base no nome
                    relatorios = relatorioDao.gerarRelatorio(Integer.parseInt(param));
                } catch (NumberFormatException nFE) {
                    //caso tenha sido gerado uma Exception na conversao para Integer entao trata-se o param como o nome da enquete
                    //e gera-se os relatorios com base nela
                    relatorios = relatorioDao.gerarRelatorio(param);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        //Sempre retorna relatorios sendo ele null ou preenchido
        return relatorios;
    }

}
