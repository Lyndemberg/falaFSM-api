package io.github.recursivejr.discenteVivo.controllers;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;

@Path("aluno")
public class AlunoController{

	//public boolean alterarSenha(){}
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("responder/{idEnquete}/{idAluno}/{resposta}")
	public Response responderEnquete(@PathParam("idEnquete") int idEnquete, @PathParam("idAluno") int idAluno, @PathParam("resposta") String resposta){

		try {
			EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
			enqueteDao.adicionarResposta(idEnquete, idAluno, resposta);

			return Response.status(Response.Status.OK).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

}
