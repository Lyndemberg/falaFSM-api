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

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Verifica se e um aluno, caso seja entao Recupera as Enquetes para este Aluno
		if(FilterDetect.checkAluno(requestContext)) {
			String matAluno = FilterDetect.getToken(requestContext);

			enquetes = enquetesDao.listar(matAluno);

		//Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas as Enquete
		} else if (FilterDetect.checkAdmin(requestContext)) {
			enquetes = enquetesDao.listar(null);

		//Caso nao seja Nenhum retorna Nao Autorizado
		} else
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquetes).build();

    }

    @GET
	@Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("enquete/{id}/")
    public Response getEnquete(@PathParam("id") int id, @Context ContainerRequestContext requestContext) {

		EnqueteDaoInterface enquetesDao = null;
		Enquete enquete = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Verifica se e um aluno, caso seja entao Recupera a Enquete com tal ID para este Aluno
		if(FilterDetect.checkAluno(requestContext)) {
			String matAluno = FilterDetect.getToken(requestContext);

			enquete = enquetesDao.buscar(id, matAluno);

		//Caso nao seja Verifica se e um Admin, caso seja entao Recupera a Enquete
		} else if (FilterDetect.checkAdmin(requestContext)) {
			enquete = enquetesDao.buscar(id, null);

		//Caso nao seja Nenhum retorna Nao Autorizado
		} else
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquete).build();
    }

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/curso/{nomeCurso}/")
	public Response EnquetesByCurso(@PathParam("nomeCurso") String nome, @Context ContainerRequestContext requestContext) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Verifica se e um aluno, caso seja entao Recupera as Enquetes para este Aluno com base no Curso
		if(FilterDetect.checkAluno(requestContext)) {
			String matAluno = FilterDetect.getToken(requestContext);

			enquetes = enquetesDao.enquetesPorCurso(nome, matAluno);

			//Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas as Enquetes do Curso
		} else if (FilterDetect.checkAdmin(requestContext)) {
			enquetes = enquetesDao.enquetesPorCurso(nome, null);

			//Caso nao seja Nenhum retorna Nao Autorizado
		} else
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquetes).build();
	}

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/setor/{nomeSetor}/")
	public Response EnquetesBySetor(@PathParam("nomeSetor") String nome, @Context ContainerRequestContext requestContext) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Verifica se e um aluno, caso seja entao Recupera as Enquetes para este Aluno
		if(FilterDetect.checkAluno(requestContext)) {
			String matAluno = FilterDetect.getToken(requestContext);

			enquetes = enquetesDao.enquetesPorSetor(nome, matAluno);

			//Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas as Enquete
		} else if (FilterDetect.checkAdmin(requestContext)) {
			enquetes = enquetesDao.enquetesPorSetor(nome,null);

			//Caso nao seja Nenhum retorna Nao Autorizado
		} else
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquetes).build();
	}

}