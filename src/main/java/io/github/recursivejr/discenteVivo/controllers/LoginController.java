package io.github.recursivejr.discenteVivo.controllers;

import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.Interface.AdministradorDaoInterface;
import io.github.recursivejr.discenteVivo.dao.Interface.AlunoDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Aluno;

@Path("login")
public class LoginController {
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("loginAluno/")
	public Response loginAluno(@FormParam("login") String login,
							   @FormParam("senha") String senha) {
		
		try {
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();
			
			Aluno aluno = alunoDao.login(login, senha);
			
			String token = new TokenManagement().gerarToken(aluno.getMatricula(), 1);

			aluno.setSenha(token);

			System.gc();
			return Response.ok(aluno).build();

		} catch(Exception ex) {
			Logger.getLogger(ex.getMessage());
			System.gc();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("loginAdmin/")
	public Response loginAdmin(@FormParam("login") String login,
							   @FormParam("senha") String senha) {
		
		try {
			AdministradorDaoInterface adminDao = new FabricaDaoPostgres().criarAdministradorDao();
			
			Administrador admin = adminDao.login(login, senha);
			
			String token = new TokenManagement().gerarToken(admin.getEmail(), 1);

			admin.setSenha(token);

			System.gc();
			return Response.ok(admin).build();

		} catch(Exception ex) {
			Logger.getLogger(ex.getMessage());
			System.gc();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}	
	}

}
