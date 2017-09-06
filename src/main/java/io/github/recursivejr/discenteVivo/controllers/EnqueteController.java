package io.github.recursivejr.discenteVivo.controllers;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.github.recursivejr.discenteVivo.dao.EnqueteDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.models.Enquete;

@Path("enquete")
public class EnqueteController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("enquetes/")
    public List<Enquete> listarEnquetes() {

		EnqueteDaoInterface enquetesDao = null;
    	try {
    		enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
    	} catch (Exception ex) {
    		Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
		}
    	return enquetesDao.listar();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("enquete/{id}/")
    public Enquete getEnquete(@PathParam("id") String id) {

		EnqueteDaoInterface enquetesDao = null;
    	try {
    		enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
    	} catch (Exception ex) {
    		Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
    		return null;
		}
    	Enquete enquete = enquetesDao.buscar(id);
    	return enquete;
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/curso/{nomeCurso}/")
	public List<Enquete> EnquetesByCurso(@PathParam("nomeCurso") String nome) {

		EnqueteDaoInterface enquetesDao = null;
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
		}
		return enquetesDao.enquetesPorCurso(nome);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enquetes/setor/{nomeSetor}/")
	public List<Enquete> EnquetesBySetor(@PathParam("nomeSetor") String nome) {

		EnqueteDaoInterface enquetesDao = null;
		try {
			enquetesDao = new FabricaDaoPostgres().criarEnqueteDao();
		} catch (Exception ex) {
			Logger.getLogger("EnqueteController-log").info("Erro:" + ex.getStackTrace());
		}
		return enquetesDao.enquetesPorSetor(nome);
	}

}