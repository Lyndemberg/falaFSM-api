package io.github.recursivejr.discenteVivo.controllers;

import io.github.recursivejr.discenteVivo.dao.Interface.SetorDaoInterface;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.filters.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Setor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("setor")
public class SetorController {

    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setores/")
    public Response getSetores(@Context ContainerRequestContext requestContext) {


        //Verifica o token, Se nao for um Admin e nao for um Aluno entao retorna Nao Autorizado
        if(!FilterDetect.checkAdmin(requestContext) && !FilterDetect.checkAluno(requestContext))
            return Response.status(Response.Status.UNAUTHORIZED).build();

        //Caso seja autorizado instancia setorDao como null
        SetorDaoInterface setorDao = null;

        //Tenta criar um setor dao, caso dispare uma Exception Entao retorna Erro do Servidor
        try {
            setorDao = new FabricaDaoPostgres().criarSetorDao();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("CursoController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        //List os Setores, caso dispare uma Exception entao retorna Bad_Request
        try {
            List<Setor> setores = setorDao.listar();

            System.gc();
            return Response.ok(setores).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("SetorController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
