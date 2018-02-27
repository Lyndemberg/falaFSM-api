package io.github.recursivejr.discenteVivo.controllers;

import io.github.recursivejr.discenteVivo.dao.Interface.CampoDaoInterface;
import io.github.recursivejr.discenteVivo.dao.Interface.OpcaoDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.Campo;
import io.github.recursivejr.discenteVivo.models.Opcao;
import io.github.recursivejr.discenteVivo.resources.FotoManagement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Path("campo")
public class CampoController {

    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("campo/{idCampo}")
    public Response getCampos(@PathParam("idCampo") int idCampo) {

        if (idCampo <= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            CampoDaoInterface campoDao = Fabrica.criarFabricaDaoPostgres().criarCampoDao();

            Campo campo = campoDao.buscar(idCampo);

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
    public Response getCamposByFormulario(@PathParam("idFormulario") int idFormulario) {

        if (idFormulario <= 0)
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            CampoDaoInterface campoDao = Fabrica.criarFabricaDaoPostgres().criarCampoDao();

            List<Campo> campos = campoDao.listarPorFormulario(idFormulario);

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
            OpcaoDaoInterface opcaoDao = new FabricaDaoPostgres().criarOpcaoCampoDao();

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

    @PUT
    @Security(NivelAcesso.NIVEL_1)
    @Consumes("image/jpeg")
    @Path("enviarFoto/{idCampo}")
    public Response setFotoCampo(File foto,
                                 @PathParam("idCampo") int idCampo) {

        //Cria uma StringFoto para receber a Foto em Base64 inicialmente null
        String stringFoto = null;

        //Tenta Converter a imagem em Base64
        try {
            stringFoto = FotoManagement.encodeFoto(foto);

            //Caso de IOExcetion ao tentar Converter a Foto entao Retorna Bad Request
            //Pois a foto enviada possui problemas
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        //Tenta Salvar a Imagem em Base64 no Banco de Dados
        try {
            CampoDaoInterface campoDao = Fabrica.criarFabricaDaoPostgres().criarCampoDao();

            //Se ao atualizar a Foto Retornar True entao Retorna Codigo 200
            if (campoDao.atualizarFoto(stringFoto, idCampo)) {
                System.gc();
                return Response.ok().build();

                //caso false entao Retorna BAD_REQUEST
            } else
                return Response.status(Response.Status.BAD_REQUEST).build();

            //Caso disparado uma Exception entao Mostro a Exception ao Terminal
            //Cria-se um Log
            //Limpa a Memoria
            //Retorna Erro do Servidor ao Cliente
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    @Produces("image/jpeg")
    @Path("recuperarFoto/{idCampo}")
    public Response getImagem(@PathParam("idCampo") int idCampo) {

        String stringFoto = null;

        //Tenta Criar uma campoDao
        try {
            CampoDaoInterface campoDao = Fabrica.criarFabricaDaoPostgres().criarCampoDao();

            //Recupera a foto do campo do BD em Base64
            stringFoto = campoDao.retornarFoto(idCampo);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger("CampoController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        //Limpa objeto CampoDao
        System.gc();

        //Se variavel StringFoto for nula entao este campo nao possui Foto, logo retorno Codigo 204 de OK mas No Content
        if (stringFoto == null)
            return Response.status(Response.Status.NO_CONTENT).build();

        //Se nao for null entao decodifica o campo e retorna ele com o codigo 200
        try {
            File foto = FotoManagement.decodeFoto(stringFoto);
            return Response.ok(foto).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger("CampoController-log").info("Erro:" + ex.getStackTrace());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
