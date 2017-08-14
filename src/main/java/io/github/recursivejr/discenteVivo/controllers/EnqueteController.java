package io.github.recursivejr.discenteVivo.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discenteVivo.models.Enquete;

public class EnqueteController{


	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("enquetes/")
    public List<Enquete> listarEnquetes(){
		EnqueteDaoPostgres enquetesDao = null;
    	try {
    		enquetesDao = new EnqueteDaoPostgres();
    	} catch (Exception e) {
			// TODO: handle exception
		}
    	return enquetesDao.listar();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("enquete/{id}")
    public Enquete getEnquete(@PathParam("id") String id){

    	EnqueteDaoPostgres enquetesDao = null;
    	try {
    		enquetesDao = new EnqueteDaoPostgres();
    	} catch (Exception e) {
			// TODO: handle exception
    		return null;
		}

    	Enquete enquete = enquetesDao.buscar(id);

    	return enquete;
    }

}