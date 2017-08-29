package io.github.recursivejr.discenteVivo.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.github.recursivejr.discenteVivo.infraSecurity.Security;

@Path("relatorio")
public class RelatorioController {
	
	@GET
	@Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getRelatorios/{nome}")
    public List<Integer> getRelatorios(@PathParam("nome") String nome) {
    	
    	//IMPLEMENTAR
		return null;
    }

}
