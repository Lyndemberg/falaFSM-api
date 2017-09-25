package io.github.recursivejr.discenteVivo.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.org.apache.regexp.internal.RE;
import io.github.recursivejr.discenteVivo.dao.*;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.models.Enquete;
import io.github.recursivejr.discenteVivo.models.Opcao;
import io.github.recursivejr.discenteVivo.resources.FotoManagement;

@Path("administrador")
public class AdministradorController {

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("cadastrarAluno/")
	public Response cadastrarAluno(Aluno aluno, @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso token seja válido tenta salvar o aluno no BD
		try {
			//Cria um ALunoDaoPostgres
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(alunoDao.adicionar(aluno) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		} 
		
	}

	@PUT
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("atualizarAluno/")
	public Response atualizarAluno(Aluno aluno, @Context ContainerRequestContext requestContext) {

		//Checa se e administrador
		if(!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Tenta atualizar o Aluno
		try {
			//Cria um alunoDao com base na Interface
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			//Tenta atualizar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(alunoDao.atualizar(aluno) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo ocorreu corretamente retorna codigo 200 de OK para o Cliente
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarAdmin/")
	public Response cadastrarAdministrador(Administrador admin,  @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		//Caso token seja válido tenta salvar o administrador no BD
		try {
			//Cria um AdministradorDaoPostgres
			AdministradorDaoInterface adminDao = new FabricaDaoPostgres().criarAdministradorDao();

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(adminDao.adicionar(admin) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@PUT
    @Security
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("atualizarAdmin/")
    public Response atualizarAdmin(Administrador admin, @Context ContainerRequestContext requestContext) {

	    //Verifica pelo token se e um Admin
        if (!FilterDetect.checkAdmin(requestContext))
            return Response.status(Response.Status.UNAUTHORIZED).build();

        //Caso o token seja valido entao verifica se o objeto passado e valido
        if (!admin.isEmpty()) {
            //Caso seja valido tenta salvar

            try {
                //Cria objeto AdminDao com base na interface
                AdministradorDaoInterface adminDao = new FabricaDaoPostgres().criarAdministradorDao();

                //Tenta atualizar, caso retorne false deu SQL Exception, caso retorne true entao atualizou com sucesso
                if (!adminDao.atualizar(admin))
                    throw new Exception("ERRO DE SQL");

                //Se der tudo certo entao retorna codigo 200 de OK
				System.gc();
                return Response.status(Response.Status.OK).build();

            }catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
				System.gc();
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } else {
            //Se nao for valido entao retorna codigo 400 de BAD REQUEST
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("enquete/cadastrarEnquete/")
	public Response cadastrarEnquete(Enquete enquete, @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso token seja válido tenta salvar a enquete no BD
		try {
			//Cria um EnqueteDao com base na interface
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			//Seta o email do admin que está criando a Enquete com base no token
			enquete.setEmailAdmin(
					FilterDetect.getToken(requestContext));

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			if(enqueteDao.adicionar(enquete) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("enquete/adicionarOpcao/{idEnquete}/")
	public Response adicionarOpcao(@PathParam("idEnquete") Integer idEnquete, List<Opcao> opcoes, @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso token seja válido tenta adicionar as novas opçoes para enquete no BD
		if (!opcoes.isEmpty()) {
			try {
				//Cria uma opcaoDao
				OpcaoDaoInterface opcaoDao = new FabricaDaoPostgres().criarOpcaoDao();

				//Recebe todas as opcoes ja salvas para esta enquete
				List<Opcao> opcoesSalvas = opcaoDao.listarPorEnquete(idEnquete);

				//Remove das novas opcoes que seram adicionadas todas aquelas que ja estao na lista de
					//opcoes ja salvas no banco para esta enquete
				for(int aux = 0; aux < opcoesSalvas.size(); aux++) {
					if (opcoes.get(aux).getOpcao().equals(
									opcoesSalvas.get(aux).getOpcao())) {
						opcoes.remove(aux);
					}
				}

				//Percorre todas as novas opcoes salvando-as
				for(int aux = 0; aux < opcoes.size(); aux++) {
					if(!opcaoDao.adicionar(idEnquete, opcoes.get(aux)))
						throw new Exception("ERRO DE SQL");
				}

				//Se tudo der certo retorna ao Cliente o Codigo 200 de OK
				System.gc();
				return Response.status(Response.Status.OK).build();

			} catch (Exception ex) {
				ex.printStackTrace();
				Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
				System.gc();
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

    @GET
    @Security
    @Produces(MediaType.APPLICATION_JSON)
    @Path("perfil/")
    public Response getPerfil(@Context ContainerRequestContext requestContext) {

        //Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
        if (!FilterDetect.checkAdmin(requestContext))
            return Response.status(Response.Status.UNAUTHORIZED).build();

        //Caso token seja válido tenta recuperar o Perfil
        try {
            //Cria um AdminDao com base na interface
            AdministradorDaoInterface adminDao = new FabricaDaoPostgres().criarAdministradorDao();

            //Recupera o email do token
            String email = FilterDetect.getToken(requestContext);

            //Retorna uma resposta com codigo 200 de OK e o Objeto Administrador com o Email do Token
			Administrador admin = adminDao.buscar(email);
			System.gc();
            return Response.ok(admin).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
	@Security
	@Consumes("image/*")
	@Path("enquete/enviarFoto/{nomeEnquete}")
	public Response setFotoEnquete(File foto, @PathParam("nomeEnquete") String nomeEnquete,
								   @Context ContainerRequestContext requestContext) {

		String stringFoto = null;

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso token seja válido tenta Setar a imagem
		try {
			stringFoto = FotoManagement.encodeFoto(foto);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		try {
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			if (enqueteDao.atualizarFoto(stringFoto, nomeEnquete))
				return Response.ok().build();
			else
				throw new SQLException("Erro ao Atualizar Foto");

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}