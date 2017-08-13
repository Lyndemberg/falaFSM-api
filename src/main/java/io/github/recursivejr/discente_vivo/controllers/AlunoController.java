package io.github.recursivejr.discente_vivo.controllers;

import io.github.recursivejr.discente_vivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.CursoDaoPostgres;
import io.github.recursivejr.discente_vivo.models.Aluno;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("aluno")
public class AlunoController{

	//public boolean alterarSenha(){}

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("resonder/{idEnquete}/{idAluno}")
	public boolean responderEnquete(@PathParam("idEnquete") int idEnquete, @PathParam("idAluno") int idAluno, String resposta){

		try{
			EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
			enqueteDao.adicionarResposta(idEnquete, idAluno, resposta);

			return true
		} cath(Exception ex){
			//Jogar no log
			return false;
		}
	}

}
