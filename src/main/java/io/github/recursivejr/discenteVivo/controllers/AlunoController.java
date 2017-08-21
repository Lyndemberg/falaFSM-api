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

import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;

@Path("aluno")
public class AlunoController{

	//public boolean alterarSenha(){}
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("responder/{idEnquete}/{idAluno}/{resposta}")
	public Response responderEnquete(@PathParam("idEnquete") int idEnquete, @PathParam("idAluno") int idAluno, 
			@PathParam("resposta") String resposta, @Context ContainerRequestContext requestContext) {
		
		//Passa o Request pelo filtro de Token, se lançar a exeption entao o token não é valido
		try {			
			FilterDetect fd = new FilterDetect();
			fd.filter(requestContext);
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ioEx.getStackTrace());
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		//Caso token seja válido tenta salvar a nova resposta no BD
		try {
			//Cria um EnqueteDaoPostgres
			EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(enqueteDao.adicionarResposta(idEnquete, idAluno, resposta) == false)
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
