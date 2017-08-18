package io.github.recursivejr.discenteVivo.controllers;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;
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
	public Response cadastrarAluno(Aluno aluno) {
		
		try {
			AlunoDaoPostgres alunoDao = new AlunoDaoPostgres();
			alunoDao.adicionar(aluno);

			return Response.status(Response.Status.OK).build();
		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarAdmin/")
	public Response cadastrarAdministrador(Administrador admin) {

		try {
			AdministradorDaoPostgres adminDao = new AdministradorDaoPostgres();
			adminDao.adicionar(admin);

			return Response.status(Response.Status.OK).build();
		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarEnquete/")
	public Response cadastrarEnquete(Enquete enquete) {

		try {
			EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
			enqueteDao.adicionar(enquete);

			return Response.status(Response.Status.OK).build();
		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
}