package io.github.recursivejr.discenteVivo.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.github.recursivejr.discenteVivo.dao.Interface.EnqueteDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.AcessControll;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.Enquete;
import io.github.recursivejr.discenteVivo.resources.FotoManagement;

@Path("enquete")
public class EnqueteController {

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
    @Path("enquetes/")
    public Response listarEnquetes(@Context SecurityContext securityContext) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Recupera o Nivel de Permissao do Usuario
		NivelAcesso nivelAcesso = AcessControll.buscarNivelPermissao(
				TokenManagement.getToken(securityContext));

		//Verifica se e um aluno, caso seja entao Recupera as Enquetes para este Aluno
		if(nivelAcesso == NivelAcesso.NIVEL_2) {
			String matAluno = TokenManagement.getToken(securityContext);

			enquetes = enquetesDao.listar(matAluno);

		//Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas as Enquete
		} else if (nivelAcesso == NivelAcesso.NIVEL_1)
			enquetes = enquetesDao.listar(null);

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquetes).build();

    }

    @GET
	@Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("enquete/{id}/")
    public Response getEnquete(@PathParam("id") int id,
							   @Context SecurityContext securityContext) {

		EnqueteDaoInterface enquetesDao = null;
		Enquete enquete = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Recupera o Nivel de Permissao do Usuario
		NivelAcesso nivelAcesso = AcessControll.buscarNivelPermissao(
				TokenManagement.getToken(securityContext));

		//Verifica se e um aluno, caso seja entao Recupera a Enquete com tal ID para este Aluno
		if(nivelAcesso == NivelAcesso.NIVEL_2) {
			String matAluno = TokenManagement.getToken(securityContext);

			enquete = enquetesDao.buscar(id, matAluno);

		//Caso nao seja Verifica se e um Admin, caso seja entao Recupera a Enquete
		} else if (nivelAcesso == NivelAcesso.NIVEL_1)
			enquete = enquetesDao.buscar(id, null);

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquete).build();
    }

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/curso/{nomeCurso}/")
	public Response EnquetesByCurso(@PathParam("nomeCurso") String nome,
									@Context SecurityContext securityContext) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Recupera o Nivel de Permissao do Usuario
		NivelAcesso nivelAcesso = AcessControll.buscarNivelPermissao(
				TokenManagement.getToken(securityContext));

		//Verifica se e um aluno, caso seja entao Recupera as Enquetes para este Aluno com base no Curso
		if(nivelAcesso == NivelAcesso.NIVEL_2) {
			String matAluno = TokenManagement.getToken(securityContext);

			enquetes = enquetesDao.enquetesPorCurso(nome, matAluno);

			//Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas as Enquetes do Curso
		} else if (nivelAcesso == NivelAcesso.NIVEL_1)
			enquetes = enquetesDao.enquetesPorCurso(nome, null);

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquetes).build();
	}

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/setor/{nomeSetor}/")
	public Response EnquetesBySetor(@PathParam("nomeSetor") String nome,
									@Context SecurityContext securityContext) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Recupera o Nivel de Permissao do Usuario
		NivelAcesso nivelAcesso = AcessControll.buscarNivelPermissao(
				TokenManagement.getToken(securityContext));

		//Verifica se e um aluno, caso seja entao Recupera as Enquetes para este Aluno
		if(nivelAcesso == NivelAcesso.NIVEL_2) {
			String matAluno = TokenManagement.getToken(securityContext);

			enquetes = enquetesDao.enquetesPorSetor(nome, matAluno);

			//Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas as Enquete
		} else if (nivelAcesso == NivelAcesso.NIVEL_1)
			enquetes = enquetesDao.enquetesPorSetor(nome,null);

		//Limpa Memoria
		System.gc();

		//Se tudo ocorreu corretamente entao retorna status 200 com OK
		return Response.ok(enquetes).build();
	}

	@GET
	@Produces("image/jpeg")
	@Path("enquete/foto/{idEnquete}")
	public Response getImagem(@PathParam("idEnquete") int idEnquete) {

		String stringFoto = null;

		//Tenta Criar uma enqueteDao
		try {
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			//Recupera a foto da enquete do BD em Base64
			stringFoto = enqueteDao.retornarFoto(idEnquete);

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		//Limpa objeto EnqueteDao
		System.gc();

		//Se variavel StringFoto for nula entao esta enquete nao possui Foto, logo retorno Codigo 204 de OK mas No Content
		if (stringFoto == null)
			return Response.status(Response.Status.NO_CONTENT).build();

		//Se nao for null entao decodifica a enquete e retorna ela com o codigo 200
		try {
			File foto = FotoManagement.decodeFoto(stringFoto);
			return Response.ok(foto).build();
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}
}