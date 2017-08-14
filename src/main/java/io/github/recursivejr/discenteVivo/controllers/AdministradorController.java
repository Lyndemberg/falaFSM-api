package io.github.recursivejr.discenteVivo.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import io.github.recursivejr.discenteVivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.models.Enquete;

@Path("Administrador")
public class AdministradorController {


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("cadastrarAluno/")
	public boolean cadastrarAluno(Aluno aluno){
		
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
	@Path("cadastrarAdmin/")
	public boolean cadastrarAdministrador(Administrador admin){

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
	@Path("cadastrarEnquete/")
	public boolean cadastrarEnquete( Enquete enquete){

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