package io.github.recursivejr.discenteVivo.controllers;

import io.github.recursivejr.discenteVivo.dao.Interface.CursoDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Curso;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("curso")
public class CursoController {

    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cursos/")
    public Response getCursos() {

        //Cria uma instancia cursoDao como null
        CursoDaoInterface cursoDao = null;

        //Tenta criar um cursodao, caso dispare uma Exception Entao retorna Erro do Servidor
        try {
            cursoDao = Fabrica.criarFabricaDaoPostgres().criarCursoDao();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("CursoController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        //List os Setores, caso dispare uma Exception entao retorna Bad_Request
        try {
            List<Curso> cursos = cursoDao.listar();

            System.gc();
            return Response.ok(cursos).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("CursoController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
