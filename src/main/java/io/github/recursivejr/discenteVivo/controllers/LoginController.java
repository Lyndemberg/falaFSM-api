package io.github.recursivejr.discenteVivo.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import io.github.recursivejr.discenteVivo.dao.AlunoDaoPostgres;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("login")
public class LoginController {
	
	private final String SECRETKEY = "FSM#STUD3NT-V01C3@K3Y/CR1PT";  
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("loginAluno/{login}/{senha}")
	public Response loginAluno(@PathParam("login") String login, @PathParam("senha") String senha) {
		
		try {
			AlunoDaoPostgres alunoDao = new AlunoDaoPostgres();
			
			alunoDao.login(login, senha);
			
			String token = gerarToken(login, 1);
			
			return Response.ok(token).build();
		} catch(Exception ex) {
			Logger.getLogger(ex.getMessage());
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

}
