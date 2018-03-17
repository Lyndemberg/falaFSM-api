package io.github.recursivejr.discenteVivo.controllers;

import io.github.recursivejr.discenteVivo.dao.Interface.CampoDaoInterface;
import io.github.recursivejr.discenteVivo.dao.Interface.OpcaoDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.infraSecurity.AcessControll;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.Campo;
import io.github.recursivejr.discenteVivo.models.Opcao;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("campo")
public class CampoController {

    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("campo/{idCampo}")
    public Response getCampos(@PathParam("idCampo") int idCampo,
                              @Context Request request) {

        if (idCampo <= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            CampoDaoInterface campoDao = Fabrica.criarFabricaDaoPostgres().criarCampoDao();

            Campo campo = campoDao.buscar(idCampo);

            System.gc();
            return Response.ok(campo).build();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("campos/{idFormulario}")
    public Response getCamposByFormulario(@PathParam("idFormulario") int idFormulario,
                                          @Context SecurityContext securityContext,
                                          @Context Request request) {

        if (idFormulario <= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            CampoDaoInterface campoDao = Fabrica.criarFabricaDaoPostgres().criarCampoDao();

            List<Campo> campos = new ArrayList<>();

            //Recupera o Nivel de Permissao do Usuario
            NivelAcesso nivelAcesso = AcessControll.buscarNivelPermissao(
                    TokenManagement.getToken(securityContext));

            //Verifica se e um aluno, caso seja entao Recupera os Campos para este Aluno com as Respostas
            if(nivelAcesso == NivelAcesso.NIVEL_2) {
                String matAluno = TokenManagement.getToken(securityContext);

                campos = campoDao.listarPorFormulario(idFormulario, matAluno);

                //Caso nao seja Verifica se e um Admin, caso seja entao Recupera todas os Campos
            } else if (nivelAcesso == NivelAcesso.NIVEL_1)
                campos = campoDao.listarPorFormulario(idFormulario, null);

            System.gc();
            return Response.ok(campos).build();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Security(NivelAcesso.NIVEL_1)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("adicionarOpcao/{idCampo}/")
    public Response adicionarCampoOpcao(@PathParam("idCampo") Integer idCampo,
                                        List<Opcao> opcoes) {

        //Caso nao tenha sido Enviada as Opçoes entao retorna BAD_REQUEST
        if (opcoes.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            //Cria uma opcaoDao para Campo
            OpcaoDaoInterface opcaoDao = Fabrica.criarFabricaDaoPostgres().criarOpcaoCampoDao();

            //Recebe todas as opcoes ja salvas para este campo
            List<Opcao> opcoesSalvas = opcaoDao.listarPorChave(idCampo);

            //Remove das novas opcoes que seram adicionadas todas aquelas que ja estao na lista de
            //opcoes salvas no banco para este Campo
            for(int aux = 0; aux < opcoesSalvas.size(); aux++) {
                if (opcoes.get(aux).getOpcao().equals(
                        opcoesSalvas.get(aux).getOpcao())) {
                    opcoes.remove(aux);
                }
            }

            //Percorre todas as novas opcoes setando sua id de Foreing Key
            //(No Caso id do Campo) e salvando-as
            for(int aux = 0; aux < opcoes.size(); aux++) {
                opcoes.get(aux).setIdFK(idCampo);

                if(!opcaoDao.adicionar(opcoes.get(aux)))
                    return Response.status(Response.Status.BAD_REQUEST).build();
            }

            //Se tudo der certo retorna ao Cliente o Codigo 200 de OK
            System.gc();
            return Response.status(Response.Status.OK).build();

            //Caso disparado uma Exception entao Mostro a Exception ao Terminal
            //Cria-se um Log
            //Limpa a Memoria
            //Retorna Erro do Servidor ao Cliente
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

}
