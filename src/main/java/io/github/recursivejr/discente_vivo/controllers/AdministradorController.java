package io.github.recursivejr.discente_vivo.controllers;

import io.github.recursivejr.discente_vivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discente_vivo.models.Administrador;
import io.github.recursivejr.discente_vivo.models.Aluno;
import io.github.recursivejr.discente_vivo.models.Enquete;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("Administrador")
public class AdministradorController {


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("cadastrarAluno/{aluno}")
	public boolean cadastrarAluno(@PathParam("aluno") String aluno){
		
		try {
			AlunoDaoPostgres alunoDao = new AlunoDaoPostgres();

			//alunoDao.adicionar(aluno);

			return true;
		} catch (Exception ex){
			//guardar exeption num log ou retornar no console
			return false;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarAdmin/{admin}")
	public boolean cadastrarAdministrador(@PathParam("admin") Administrador admin){

		try{
			AdministradorDaoPostgres adminDao = new AdministradorDaoPostgres();

			adminDao.adicionar(admin);

			return true;
		} catch (Exception e) {
			//guardar exeption num log ou retornar no console
			return false;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarEnquete/{enquete}")
	public boolean cadastrarEnquete(@PathParam("enquete") Enquete enquete){

		try{
			EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();

			enqueteDao.adicionar(enquete);

			return true;
		}  catch (Exception e) {
			//guardar exeption num log ou retornar no console
			return false;
		}
	}
}