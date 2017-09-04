package io.github.recursivejr.discenteVivo.controllers;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.RespostaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.models.Resposta;

@Path("aluno")
public class AlunoController{

	//public boolean alterarSenha(){}
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("responder/{idEnquete}/{resposta}")
	public Response responderEnquete(@PathParam("idEnquete") int idEnquete, @PathParam("resposta") String resposta,
			@Context ContainerRequestContext requestContext) {

		//Verifica se e um Aluno, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		//Caso token seja válido tenta salvar a nova resposta no BD
		try {
			//Cria um EnqueteDaoPostgres
			RespostaDaoPostgres respostaDao = new RespostaDaoPostgres();
			//Pega a matricula do aluno que esta respondendo a enquete pelo token de acesso
			String matAluno = requestContext
					.getSecurityContext()
						.getUserPrincipal()
							.getName();			
			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			Resposta resp = new Resposta(idEnquete, resposta, matAluno);
			if(respostaDao.adicionar(resp) == false)
				throw new Exception("ERRO DE SQL");
			//Se tudo certo retorna status 200
			return Response.status(Response.Status.OK).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

}
