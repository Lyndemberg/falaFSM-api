package io.github.recursivejr.discenteVivo.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.models.Enquete;

@Path("administrador")
public class AdministradorController {

	@Security
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("cadastrarAluno/")
	public Response cadastrarAluno(Aluno aluno, @Context ContainerRequestContext requestContext) {
		
		//Passa o Request pelo filtro de Token, se lançar a exeption entao o token não é valido
		try {			
			FilterDetect fd = new FilterDetect();
			fd.filter(requestContext);
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ioEx.getStackTrace());
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		//Caso token seja válido tenta salvar o aluno no BD
		try {
			//Cria um ALunoDaoPostgres
			AlunoDaoPostgres alunoDao = new AlunoDaoPostgres();
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarAdmin/")
	public Response cadastrarAdministrador(Administrador admin,  @Context ContainerRequestContext requestContext) {
		
		//Passa o Request pelo filtro de Token, se lançar a exeption entao o token não é valido
		try {			
			FilterDetect fd = new FilterDetect();
			fd.filter(requestContext);
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ioEx.getStackTrace());
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		//Caso token seja válido tenta salvar o administrador no BD
		try {
			//Cria um AdministradorDaoPostgres
			AdministradorDaoPostgres adminDao = new AdministradorDaoPostgres();
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarEnquete/")
	public Response cadastrarEnquete(Enquete enquete, @Context ContainerRequestContext requestContext) {

		//Passa o Request pelo filtro de Token, se lançar a exeption entao o token não é valido
		try {			
			FilterDetect fd = new FilterDetect();
			fd.filter(requestContext);
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ioEx.getStackTrace());
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
				
		//Caso token seja válido tenta salvar a enquete no BD		
		try {
			EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
			//Cria um AdministradorDaoPostgres
			if(enqueteDao.adicionar(enquete) == false)
					throw new Exception("ERRO DE SQL");
			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			return Response.status(Response.Status.OK).build();
			//Se tudo certo retorna status 200
		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
}