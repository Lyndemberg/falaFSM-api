package io.github.recursivejr.discenteVivo.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.print.attribute.standard.Media;
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
import io.github.recursivejr.discenteVivo.models.*;
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

		//Caso disparado uma Exception entao Mostro a Exception ao Terminal
		//Cria-se um Log
		//Limpa a Memoria
		//Retorna Erro do Servidor ao Cliente
		} catch (Exception ex) {
			ex.printStackTrace();
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

		//Caso disparado uma Exception entao Mostro a Exception ao Terminal
		//Cria-se um Log
		//Limpa a Memoria
		//Retorna Erro do Servidor ao Cliente
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

		//Caso disparado uma Exception entao Mostro a Exception ao Terminal
		//Cria-se um Log
		//Limpa a Memoria
		//Retorna Erro do Servidor ao Cliente
		} catch (Exception ex) {
			ex.printStackTrace();
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

			//Caso disparado uma Exception entao Mostro a Exception ao Terminal
			//Cria-se um Log
			//Limpa a Memoria
			//Retorna Erro do Servidor ao Cliente
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
	@Produces(MediaType.TEXT_PLAIN)
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
			int idEnquete = enqueteDao.adicionar(enquete);
			if(idEnquete == 0)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200 de OK com a Id da Enquete Salva
			System.gc();
			return Response.ok(idEnquete).build();

		//Caso disparado uma Exception entao Mostro a Exception ao Terminal
		//Cria-se um Log
		//Limpa a Memoria
		//Retorna Erro do Servidor ao Cliente
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

			//Caso disparado uma Exception entao Mostro a Exception ao Terminal
			//Cria-se um Log
			//Limpa a Memoria
			//Retorna Erro do Servidor ao Cliente
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

        //Caso disparado uma Exception entao Mostro a Exception ao Terminal
		// Cria-se um Log
		//Limpa a Memoria
		//Retorna Erro do Servidor ao Cliente
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
	@Security
	@Consumes("image/jpeg")
	@Path("enquete/enviarFoto/{idEnquete}")
	public Response setFotoEnquete(File foto, @PathParam("idEnquete") int idEnquete,
								   @Context ContainerRequestContext requestContext) {

		String stringFoto = null;

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso token seja válido tenta Converter a imagem em Base64
		try {
			stringFoto = FotoManagement.encodeFoto(foto);

			//Caso de IOExcetion ao tentar Converter a Foto entao Retorna Bad Request
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		//Tenta Salvar a Imagem em Base64 no Banco de Dados
		try {
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			//Se ao atualizar a Foto Retornar True entao Retorna Codigo 200
			if (enqueteDao.atualizarFoto(stringFoto, idEnquete)) {
				System.gc();

				//Caso ocorra tudo corretamente returna Codigo 200 de OK
				return Response.ok().build();
			} else
				throw new SQLException("Erro ao Atualizar Foto");

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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("enquete/atualizar/{idEnquete}")
	public Response atualizarEnquete(Enquete enquete, @PathParam("idEnquete") int idEnquete,
								 @Context ContainerRequestContext requestContext) {

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

			//Seta o Id da Enquete com o Id passado na Requisiçao Removendo a Necessidade
				//da Enquete ja Estar preenchida com o Id
			enquete.setId(idEnquete);

			//Tenta Atualizara a Enquete, Caso Retorne False Entao Dispara uma Exception de Erro de SQL
			if (!enqueteDao.atualizar(enquete))
				throw new Exception("Erro de SQL");

			//Se tudo certo retorna status 200 de OK com a Id da Enquete Salva
			System.gc();
			return Response.ok(idEnquete).build();

			//Caso disparado uma Exception entao Mostro a Exception ao Terminal
			//Cria-se um Log
			//Limpa a Memoria
			//Retorna Erro do Servidor ao Cliente
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@DELETE
	@Security
	@Path("enquete/deletar/{idEnquete}")
	public Response removerEnquete(@PathParam("idEnquete") int idEnquete,
								   @Context ContainerRequestContext requestContext) {

		//Verifica se e admin, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();


		//Caso token seja válido tenta salvar a enquete no BD
		try {
			//Cria um EnqueteDao com base na interface
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			//Tenta Remover a Enquete, se o EnqueteDao Retornar False Entao ocorreu Algum Erro Entao
				//Dispara uma SQLException
			if(!enqueteDao.remover(idEnquete))
				throw new SQLException("ERRO DE SQL");

			//Se tudo certo retorna status 200 de OK com a Id da Enquete Salva
			System.gc();
			return Response.ok(idEnquete).build();

			//Caso disparado uma Exception entao Mostro a Exception ao Terminal
			//Cria-se um Log
			//Limpa a Memoria
			//Retorna Erro do Servidor ao Cliente
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
	@Path("curso/cadastrarCurso/")
	public Response cadastrarCurso(Curso curso, @Context ContainerRequestContext requestContext) {

		//Checa o Token para verificar se ele e valido e se pertence a um admin
			//caso retorne false entao Retorna ao Cliente 401 de Nao Autorizado
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Verifica se o Objeto Curso foi devidamente Preenchido pelo Cliente
			//Caso nao Retorna Bad_Request para o Cliente
		if(curso.getNome().isEmpty() || curso.getDescricao().isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();

		//Caso Tudo Esteja Valido Tenta Salvar O Curso
		try {
			//Cria um cursoDao com base na Interface
			CursoDaoInterface cursoDao = new FabricaDaoPostgres().criarCursoDao();

			//Se ao adicionar o Curso retornar True entao Retorna Codigo 200 de Ok para o Cliente
			//Caso Retorne False entao Ocorreu erro de SQL logo disparo uma Exception
			if (cursoDao.adicionar(curso)) {
				System.gc();
				return Response.status(Response.Status.OK).build();
			} else
				throw new SQLException("Erro de SQL ao Adicionar o Curso : " + curso.toString());

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
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("setor/cadastrarSetor/")
	public Response cadastrarSetor(Setor setor, @Context ContainerRequestContext requestContext) {

		//Verifica se o Token e Valido e Se o Token Pertence a um Admministrador
			//Caso Nao Entao Retorna ao Cliente Nao_Autorizado
		if (!FilterDetect.checkAdmin(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Verifica se o Objeto Setor foi Devidamente Preenchido
			//Caso nao entao Retorna Bad_Request para o Cliente
		if(setor.getNome().isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();

		//Caso tudo Seja Validado entao Tenta Salvar o Setor
		try {
			//Cria um Objeto setorDao com base na Interface
			SetorDaoInterface setorDao = new FabricaDaoPostgres().criarSetorDao();

			//Se ao adicionar o Setor retornar True entao Retorna Codigo 200 de Ok para o Cliente
			//Caso Retorne False entao Ocorreu erro de SQL logo disparo uma Exception
			if (setorDao.adicionar(setor)) {
				System.gc();
				return Response.status(Response.Status.OK).build();
			} else
				throw new SQLException("Erro de SQL ao Adicionar o Setor : " + setor.toString());

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

}