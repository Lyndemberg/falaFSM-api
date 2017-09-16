package io.github.recursivejr.discenteVivo.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import io.github.recursivejr.discenteVivo.dao.AdministradorDaoInterface;
import io.github.recursivejr.discenteVivo.dao.AlunoDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("login")
public class LoginController {
	
	private final String SECRETKEY = "FSM#STUD3NT-V01C3@K3Y/CR1PT";  
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("loginAluno/{login}/{senha}/")
	public Response loginAluno(@PathParam("login") String login, @PathParam("senha") String senha) {
		
		try {
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();
			
			Aluno aluno = alunoDao.login(login, senha);
			
			String token = gerarToken(aluno.getMatricula(), 1);

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
			
			String token = gerarToken(admin.getEmail(), 1);

			admin.setSenha(token);

			System.gc();
			return Response.ok(admin).build();

		} catch(Exception ex) {
			Logger.getLogger(ex.getMessage());
			System.gc();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}	
	}
	
	private String gerarToken(String login, int limiteDias) {
		//Gera algoritmo de criptografia em SHA512
		try {
			SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
			
			Date agora = new Date();
			
			Calendar expira = Calendar.getInstance();
			expira.add(Calendar.DAY_OF_MONTH, limiteDias);
			
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);
			
			SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());

			JwtBuilder construtor = Jwts.builder()
				.setIssuedAt(agora)
				.setIssuer(login)
				.signWith(algorithm, key)
				.setExpiration(expira.getTime());
				return construtor.compact();	
			
			
		} catch (IllegalArgumentException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}		
	}
	
	public Claims validaToken(String token) {
		try {
		   Claims claims = Jwts.parser()
			     .setSigningKey(DatatypeConverter.parseBase64Binary(SECRETKEY))
			     .parseClaimsJws(token).getBody();
		   return claims;
		} catch(Exception ex) {
				throw ex;
		}
	}

}
