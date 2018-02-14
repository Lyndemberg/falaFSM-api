package io.github.recursivejr.discenteVivo.controllers;

import io.github.recursivejr.discenteVivo.dao.Interface.FormularioDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.infraSecurity.AcessControll;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.Formulario;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("formulario")
public class FormularioController {

    @GET
    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("formularios/")
    public Response listarFormularios(@Context SecurityContext securityContext) {

        //Cria uma Lista de formularios
        List<Formulario> formularios = new ArrayList<>();

        //Recupera o Token de Acesso e o Nivel de Acesso
        String token = TokenManagement.getToken(securityContext);
        NivelAcesso nivelAcesso = AcessControll.buscarNivelPermissao(token);

        try {
            //Cria objeto FormularioDao
            FormularioDaoInterface formularioDao = Fabrica.criarFabricaDaoPostgres()
                                                                .criarFormularioDao();

            //Se o nivel de Acesso foi NIVEL_1 Entao e um Administrador Listando Assim
                //todos os Formularios
            if (nivelAcesso.equals(NivelAcesso.NIVEL_1))
                formularios.addAll(formularioDao.listar());
            else {
                //Por causa da limitacao @Security(Nivel_1, Nivel2)
                //Se nao e de Nivel_1 entao so pode ser Nivel_2, logo um Aluno
                formularios.addAll(formularioDao.listarPorAluno(token));
            }

            //Caso Ocorra tudo normalmente Retorna Status 200 de OK
            System.gc();
            return Response.ok(formularios).build();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger("FormularioController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("formulario/{idFormulario}/")
    public Response getFormulario(@PathParam("idFormulario") int idFormulario,
                               @Context SecurityContext securityContext) {

        //Cria um formulario inicialmente null
        Formulario formulario = null;

        //Recupera o Token de Acesso e o Nivel de Acesso
        String token = TokenManagement.getToken(securityContext);
        NivelAcesso nivelAcesso = AcessControll.buscarNivelPermissao(token);

        try {
            //Cria objeto FormularioDao
            FormularioDaoInterface formularioDao = Fabrica.criarFabricaDaoPostgres()
                    .criarFormularioDao();

            //Se o nivel de Acesso foi NIVEL_1 Entao e um Administrador Listando Assim
            //todos os Formularios
            if (nivelAcesso.equals(NivelAcesso.NIVEL_1))
                formulario = formularioDao.buscar(idFormulario, null);
            else {
                //Por causa da limitacao @Security(Nivel_1, Nivel2)
                //Se nao e de Nivel_1 entao so pode ser Nivel_2, logo um Aluno
                formulario = formularioDao.buscar(idFormulario, token);
            }

            //Caso Ocorra tudo normalmente Retorna Status 200 de OK
            System.gc();
            return Response.ok(formulario).build();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger("FormularioController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    
}
