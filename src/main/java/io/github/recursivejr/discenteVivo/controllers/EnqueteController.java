package io.github.recursivejr.discenteVivo.controllers;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.org.apache.regexp.internal.RE;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Enquete;

@Path("enquete")
public class EnqueteController {

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
    @Path("enquetes/")
    public Response listarEnquetes(@Context ContainerRequestContext requestContext) {

		//Verifica se e um aluno, caso nao seja retorna Nao Autorizado
		if(!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		EnqueteDaoInterface enquetesDao = null;

    	try {
    		enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
    	} catch (Exception ex) {
    		Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		String matAluno = FilterDetect.getToken(requestContext);

		List<Enquete> enquetes = enquetesDao.listar(matAluno);
    	System.gc();
    	return Response.ok(enquetes).build();
    }

    @GET
	@Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("enquete/{id}/")
    public Response getEnquete(@PathParam("id") int id, @Context ContainerRequestContext requestContext) {

		//Verifica se e um aluno, caso nao seja retorna Nao Autorizado
		if(!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		EnqueteDaoInterface enquetesDao = null;

    	try {
    		enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
    	} catch (Exception ex) {
    		Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		String matAluno = FilterDetect.getToken(requestContext);

    	Enquete enquete = enquetesDao.buscar(id, matAluno);
    	System.gc();
    	return Response.ok(enquete).build();
    }

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/curso/{nomeCurso}/")
	public Response EnquetesByCurso(@PathParam("nomeCurso") String nome, @Context ContainerRequestContext requestContext) {

		//Verifica se e um aluno, caso nao seja retorna Nao Autorizado
		if(!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		EnqueteDaoInterface enquetesDao = null;

		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		String matAluno = FilterDetect.getToken(requestContext);

		List<Enquete> enquetes = enquetesDao.enquetesPorCurso(nome, matAluno);
		System.gc();
		return Response.ok(enquetes).build();
	}

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/setor/{nomeSetor}/")
	public Response EnquetesBySetor(@PathParam("nomeSetor") String nome, @Context ContainerRequestContext requestContext) {

		//Verifica se e um aluno, caso nao seja retorna Nao Autorizado
		if(!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		EnqueteDaoInterface enquetesDao = null;

		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
		}

		String matAluno = FilterDetect.getToken(requestContext);

		List<Enquete> enquetes = enquetesDao.enquetesPorSetor(nome, matAluno);
		System.gc();
		return Response.ok(enquetes).build();
	}

}