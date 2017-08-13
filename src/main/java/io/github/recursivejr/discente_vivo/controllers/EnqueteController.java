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

public class EnqueteController{


	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("enquetes/")
    public List<Enquete> listarEnquetes(){
    	EnqueteDaoPostgres enquetesDao = new EnqueteDaoPostgres();
    	return enquetesDao.listar():
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("enquete/{id}")
    public Enquete getEnquete(@PathParam("id") String id){

    	EnqueteDaoPostgres enquetesDao = new EnqueteDaoPostgres();

    	Enquete enquete = enquetesDao.buscar(id);

    	return enquete;
    }

}