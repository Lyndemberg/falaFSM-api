package io.github.recursivejr.falaFSM.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import io.github.recursivejr.falaFSM.dao.Interface.EnqueteDaoInterface;
import io.github.recursivejr.falaFSM.factories.Fabrica;
import io.github.recursivejr.falaFSM.infraSecurity.AcessControll;
import io.github.recursivejr.falaFSM.infraSecurity.CacheController;
import io.github.recursivejr.falaFSM.infraSecurity.TokenManagement;
import io.github.recursivejr.falaFSM.infraSecurity.Security;
import io.github.recursivejr.falaFSM.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.falaFSM.models.Enquete;
import io.github.recursivejr.falaFSM.resources.FotoManagement;

@Path("enquete")
public class EnqueteController {

	@GET
	@Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
	@Produces(MediaType.APPLICATION_JSON)
    @Path("enquetes/")
    public Response listarEnquetes(@Context SecurityContext securityContext,
								   @Context Request request) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = Fabrica.criarFabricaDaoPostgres().criarEnqueteDao();
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

			enquetes = enquetesDao.listarPorAluno(matAluno);

		//Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas as Enquete
		} else if (nivelAcesso == NivelAcesso.NIVEL_1)
			enquetes = enquetesDao.listar();

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
							   @Context SecurityContext securityContext,
							   @Context Request request) {

		EnqueteDaoInterface enquetesDao = null;
		Enquete enquete = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = Fabrica.criarFabricaDaoPostgres().criarEnqueteDao();
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

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/curso/")
	public Response enquetesByCurso(@FormParam("nomeCurso") String nome,
									@Context SecurityContext securityContext,
									@Context Request request) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = Fabrica.criarFabricaDaoPostgres().criarEnqueteDao();
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

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/setor/")
	public Response enquetesBySetor(@FormParam("nomeSetor") String nome,
									@Context SecurityContext securityContext,
									@Context Request request) {

		EnqueteDaoInterface enquetesDao = null;
		List<Enquete> enquetes = null;

		//Tenta criar um EnqueteDao
		try {
			enquetesDao = Fabrica.criarFabricaDaoPostgres().criarEnqueteDao();
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

		File foto = FotoManagement.verifyExistsFoto(FotoManagement.TIPO_ENQUETE, idEnquete);

		if (foto != null)
			return Response.ok(foto).build();

		//Tenta Criar uma enqueteDao
		try {
			EnqueteDaoInterface enqueteDao = Fabrica.criarFabricaDaoPostgres().criarEnqueteDao();

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
			foto = FotoManagement.decodeFoto(stringFoto, FotoManagement.TIPO_ENQUETE, idEnquete);
			return Response.ok(foto).build();
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}
}