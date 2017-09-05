package io.github.recursivejr.discenteVivo.controllers;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.*;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.models.Enquete;

@Path("administrador")
public class AdministradorController {

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("cadastrarAluno/")
	public Response cadastrarAluno(Aluno aluno, @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso token seja válido tenta salvar o aluno no BD
		try {
			//Cria um ALunoDaoPostgres
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(alunoDao.adicionar(aluno) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		} 
		
	}

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarAdmin/")
	public Response cadastrarAdministrador(Administrador admin,  @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		//Caso token seja válido tenta salvar o administrador no BD
		try {
			//Cria um AdministradorDaoPostgres
			AdministradorDaoInterface adminDao = new FabricaDaoPostgres().criarAdministradorDao();

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(adminDao.adicionar(admin) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarEnquete/")
	public Response cadastrarEnquete(Enquete enquete, @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();
				
		//Caso token seja válido tenta salvar a enquete no BD		
		try {
			//Cria um EnqueteDao com base na interface
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			//Seta o email do admin que está criando a Enquete com base no token
			enquete.setEmailAdmin(
					requestContext
						.getSecurityContext()
							.getUserPrincipal()
								.getName());

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(enqueteDao.adicionar(enquete) == false)
					throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("atualizarAluno")
	public Response atualizarAluno(Aluno aluno, @Context ContainerRequestContext requestContext) {

		//Checa se e administrador
		if(!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Tenta atualizar o Aluno
		try {
			//Cria um alunoDao com base na Interface
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			//Tenta atualizar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(alunoDao.atualizar(aluno) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo ocorreu corretamente retorna codigo 200 de OK para o Cliente
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
}