package io.github.recursivejr.discenteVivo.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import io.github.recursivejr.discenteVivo.dao.Interface.AdministradorDaoInterface;
import io.github.recursivejr.discenteVivo.dao.Interface.AlunoDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("login")
public class LoginController {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("loginAluno/{login}/{senha}/")
	public Response loginAluno(@PathParam("login") String login, @PathParam("senha") String senha) {
		
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
	@Produces(MediaType.APPLICATION_JSON)
	@Path("loginAdmin/{login}/{senha}/")
	public Response loginAdmin(@PathParam("login") String login, @PathParam("senha") String senha) {
		
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

	public NivelAcesso buscarNivelPermissao(String login) {

		/*
			Verifica com base no token se é um administrador, apenas administradores posuem email no token
				logo a condição de parada é possuir um "@" no token.
			Caso seja um Administrador e retornado o Nivel de Acesso 1,
			*/

		if (login.contains("@"))
			return NivelAcesso.NIVEL_1;
		else
			return NivelAcesso.NIVEL_2;

	}

}
