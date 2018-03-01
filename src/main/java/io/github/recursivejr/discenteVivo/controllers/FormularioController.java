package io.github.recursivejr.discenteVivo.controllers;

import io.github.recursivejr.discenteVivo.dao.Interface.CampoDaoInterface;
import io.github.recursivejr.discenteVivo.dao.Interface.FormularioDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.infraSecurity.AcessControll;
import io.github.recursivejr.discenteVivo.infraSecurity.CacheController;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.Campo;
import io.github.recursivejr.discenteVivo.models.Formulario;
import io.github.recursivejr.discenteVivo.resources.FotoManagement;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("formulario")
public class FormularioController {

    @POST
    @Security(NivelAcesso.NIVEL_1)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("cadastrarFormulario/")
    public Response cadastrarFormulario(Formulario formulario,
                                        @Context SecurityContext securityContext) {

        try {
            //Cria Objeto FormularioDao
            FormularioDaoInterface formularioDao = Fabrica.criarFabricaDaoPostgres().criarFormularioDao();

            //Recupera o Email do Admin e Setta ele no Formulario
            formulario.setEmailAdmin(
                    TokenManagement.getToken(securityContext)
            );

            //Recebe o retorno do Metodo de Adicionar o Formulario
            Integer idFormulario = formularioDao.adicionar(formulario);

            //Caso o retorno seja null entao houve um problema e retorna-se BAD_REQUEST
            if (idFormulario == null)
                return Response.status(Response.Status.BAD_REQUEST).build();

            //Caso tudo tenha ocorrido sem problemas entao Retorna Status 200 de OK
            System.gc();
            return Response.ok(idFormulario).build();

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

    @POST
    @Security(NivelAcesso.NIVEL_1)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("adicionarCampos/{idFormulario}/")
    public Response adicionarCampos(@PathParam("idFormulario") Integer idFormulario,
                                    List<Campo> campos) {

        //Caso nao tenha sido Enviado nenhum Campo entao retorna BAD_REQUEST
        if (campos.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            //Cria uma campoDao
            CampoDaoInterface campoDao = Fabrica.criarFabricaDaoPostgres().criarCampoDao();

            //Recebe todas os campos ja salvas para neste Formulario
            List<Campo> camposSalvos = campoDao.listarPorFormulario(idFormulario);

            //Remove das novas opcoes que seram adicionadas todas aquelas que ja estao na lista de
            //opcoes salvas no banco para este Formulario
            for(int aux = 0; aux < camposSalvos.size(); aux++) {
                if (campos.get(aux).simpleEquals(
                        camposSalvos.get(aux))) {
                    campos.remove(aux);
                }
            }

            //Percorre todos os Campos restantes setando o id do Formulario e salvando-os
            for(int aux = 0; aux < campos.size(); aux++) {
                campos.get(aux).setIdFormulario(idFormulario);

                if(campoDao.adicionar(campos.get(aux)) == null)
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("atualizarFormulario/{idFormulario}/")
    public Response atualizarFormulario(Formulario formulario,
                                        @PathParam("idFormulario") int idFormulario) {

        try {
            //Cria um FormularioDao com base na interface
            FormularioDaoInterface formularioDao = Fabrica.criarFabricaDaoPostgres().criarFormularioDao();

            //Seta o Id do Formulario com o Id passado na Requisiçao Removendo a Necessidade
            //o Formulario ja Esteja preenchido com o Id correto
            formulario.setId(idFormulario);

            //Tenta Atualizar o Formulario, Caso Retorne False Entao houve um problema
            //Retornando BAD_REQUEST
            if (!formularioDao.atualizar(formulario))
                return Response.status(Response.Status.BAD_REQUEST).build();

            //Se tudo certo retorna status 200 de OK
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

    @DELETE
    @Security(NivelAcesso.NIVEL_1)
    @Path("deletarFormulario/{idFormulario}/")
    public Response removerFormulario(@PathParam("idFormulario") int idFormulario) {

        try {
            //Cria um FormularioDao com base na interface
            FormularioDaoInterface formularioDao = Fabrica.criarFabricaDaoPostgres().criarFormularioDao();

            //Tenta Remover o Formulario, se Retornar False Entao ocorreu Algum Erro Entao
            //Retorno BAD_REQUEST
            if(!formularioDao.remover(idFormulario))
                return Response.status(Response.Status.BAD_REQUEST).build();

            //Se tudo certo retorna status 200 de OK
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

    @GET
    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("formularios/")
    public Response listarFormularios(@Context SecurityContext securityContext,
                                      @Context Request request) {

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

            EntityTag etag = new EntityTag(Integer.toString(formularios.hashCode()));
            Response.ResponseBuilder builder = request.evaluatePreconditions(etag);

            if (builder == null) {
                builder = Response.ok(formularios);
                builder.tag(etag);
            }

            builder.cacheControl(CacheController.getCacheControl());
            return builder.build();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger("FormularioController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
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

    @POST
    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("formularios/curso/")
    public Response formulariosByCurso(@FormParam("nomeCurso") String nome,
                                       @Context SecurityContext securityContext) {

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
                    formularios.addAll(formularioDao.formulariosPorCurso(nome, null));
                else {
                    //Por causa da limitacao @Security(Nivel_1, Nivel2)
                    //Se nao e de Nivel_1 entao so pode ser Nivel_2, logo um Aluno
                    formularios.addAll(formularioDao.formulariosPorCurso(nome, token));
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

    @POST
    @Security({NivelAcesso.NIVEL_1, NivelAcesso.NIVEL_2})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("formularios/setor/")
    public Response formulariosBySetor(@FormParam("nomeSetor") String nome,
                                    @Context SecurityContext securityContext) {

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
                formularios.addAll(formularioDao.formulariosPorSetor(nome, null));
            else {
                //Por causa da limitacao @Security(Nivel_1, Nivel2)
                //Se nao e de Nivel_1 entao so pode ser Nivel_2, logo um Aluno
                formularios.addAll(formularioDao.formulariosPorSetor(nome, token));
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

    @PUT
    @Security(NivelAcesso.NIVEL_1)
    @Consumes("image/jpeg")
    @Path("foto/{idFormulario}/")
    public Response setFotoFormulario(File foto,
                                      @PathParam("idFormulario") int idFormulario) {

        //Cria uma StringFoto para receber a Foto em Base64 inicialmente null
        String stringFoto= null;

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
            FormularioDaoInterface formularioDao = Fabrica.criarFabricaDaoPostgres().criarFormularioDao();

            //Se ao atualizar a Foto Retornar True entao Retorna Codigo 200
            if (formularioDao.atualizarFoto(stringFoto, idFormulario)) {
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
    @Path("foto/{idFormulario}/")
    public Response getFotoFormulario(@PathParam("idFormulario") int idFormulario) {

        String foto = null;

        try {
            //Cria objeto FormularioDao
            FormularioDaoInterface formularioDao = Fabrica.criarFabricaDaoPostgres()
                    .criarFormularioDao();

            //Recupera a Foto do Banco de Dados em Base64
            foto = formularioDao.retornarFoto(idFormulario);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger("FormularioController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        //Limpa objeto FormularioDao
        System.gc();

        //Se variavel StringFoto for nula entao este formulario nao possui Foto,
            //logo retorno Codigo 204 de OK mas No Content
        if (foto == null)
            return Response.status(Response.Status.NO_CONTENT).build();

        //Se nao for null entao decodifica a foto e retorna ela com o codigo 200
        try {
            File image = FotoManagement.decodeFoto(foto);
            return Response.ok(image).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger("FormularioController-log").info("Erro:" + ex.getStackTrace());
            System.gc();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
