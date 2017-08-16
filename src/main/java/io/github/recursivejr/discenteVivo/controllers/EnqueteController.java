package io.github.recursivejr.discenteVivo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discenteVivo.models.Enquete;

@Path("enquete")
public class EnqueteController{


	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("enquetes/")
    public List<Enquete> listarEnquetes(){
		EnqueteDaoPostgres enquetesDao = null;
    	try {
    		enquetesDao = new EnqueteDaoPostgres();
    	} catch (Exception ex) {
    		Logger.getLogger(ex.getMessage());
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
    	} catch (Exception ex) {
    		Logger.getLogger(ex.getMessage());
    		return null;
		}

    	Enquete enquete = enquetesDao.buscar(id);

    	return enquete;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("enqueteRelatoria/{nome}")
    public List<Integer> enqueteRelatorio(@PathParam("nome") String nome) {
    	
    	EnqueteDaoPostgres enquetesDao;
    	List<Integer> relatorio = new ArrayList<>();
    	
    	try {
    		enquetesDao = new EnqueteDaoPostgres();
    		String respostas[][] = enquetesDao.relatorio(nome);
    		
    		relatorio.add(Integer.parseInt(respostas[0][1]) + Integer.parseInt(respostas[1][1]));
    		relatorio.add(Integer.parseInt(respostas[0][1]));
    		relatorio.add(Integer.parseInt(respostas[1][1]));
    		
    		return relatorio;
    	} catch (Exception ex) {
    		Logger.getLogger(ex.getMessage());
    		return null;
		}
    	
    	
    }

}