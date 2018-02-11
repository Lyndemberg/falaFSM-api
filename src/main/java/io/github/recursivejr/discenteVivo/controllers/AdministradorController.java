package io.github.recursivejr.discenteVivo.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.github.recursivejr.discenteVivo.dao.Interface.*;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.*;
import io.github.recursivejr.discenteVivo.resources.FotoManagement;

@Path("administrador")
public class AdministradorController {

	@POST
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("cadastrarAluno/")
	public Response cadastrarAluno(Aluno aluno) {

		try {
			//Verifica se o aluno foi corretamente preenchido, caso nao retorna BAD_REQUEST
			if (aluno.isEmpty())
				return Response.status(Response.Status.BAD_REQUEST).build();

			//Como o aluno esta corretamente preenchido entao
				//Cria um AlunoDaoPostgres para salvar no BD
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
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("atualizarAluno/")
	public Response atualizarAluno(Aluno aluno) {

		//Tenta atualizar o Aluno
		try {
			//Verifica se o Aluno foi corretamente Preenchido, caso nao retorna BAD_REQUEST
			if (aluno.isEmpty())
				return Response.status(Response.Status.BAD_REQUEST).build();

			//Caso o aluno tenha sido corretamente preenchido entao
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
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("cadastrarAdmin/")
	public Response cadastrarAdministrador(Administrador admin) {

		try {
			//Verifica se o Administrador foi corretamente preechido caso nao retorna BAD_REQUEST
			if (admin.isEmpty())
				return Response.status(Response.Status.BAD_REQUEST).build();

			//Caso o Administrador tenha sido preenchido corretamente entrao
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
    @Security(NivelAcesso.NIVEL_1)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("atualizarAdmin/")
    public Response atualizarAdmin(Administrador admin) {

		//Verifica se o Adminsitrador foi corretamente Preenchido, caso nao retorna
			//BAD_REQUEST
		if (admin.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();

		//Caso tenha sido entrao
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
    }

	@POST
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("enquete/cadastrarEnquete/")
	public Response cadastrarEnquete(Enquete enquete,
									 @Context SecurityContext securityContext) {

		try {
			//Cria um EnqueteDao com base na interface
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			//Seta o email do admin que está criando a Enquete com base no token
			enquete.setEmailAdmin(
					TokenManagement.getToken(securityContext));

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
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("enquete/adicionarOpcao/{idEnquete}/")
	public Response adicionarOpcao(@PathParam("idEnquete") Integer idEnquete,
								   List<Opcao> opcoes) {

		//Caso nao tenha sido Enviada as Opçoes entao retorna BAD_REQUEST
		if (opcoes.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();

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

	}

    @GET
    @Security(NivelAcesso.NIVEL_1)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("perfil/")
    public Response getPerfil(@Context SecurityContext securityContext) {

        try {
            //Cria um AdminDao com base na interface
            AdministradorDaoInterface adminDao = new FabricaDaoPostgres().criarAdministradorDao();

            //Recupera o email do token
            String email = TokenManagement.getToken(securityContext);

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
	@Security(NivelAcesso.NIVEL_1)
	@Consumes("image/jpeg")
	@Path("enquete/enviarFoto/{idEnquete}")
	public Response setFotoEnquete(File foto,
								   @PathParam("idEnquete") int idEnquete) {

		String stringFoto = null;

		//Tenta Converter a imagem em Base64
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
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("enquete/atualizar/{idEnquete}")
	public Response atualizarEnquete(Enquete enquete,
									 @PathParam("idEnquete") int idEnquete,
									 @Context SecurityContext securityContext) {

		try {
			//Cria um EnqueteDao com base na interface
			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();

			//Seta o email do admin que está criando a Enquete com base no token
			enquete.setEmailAdmin(
					TokenManagement.getToken(securityContext));

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
	@Security(NivelAcesso.NIVEL_1)
	@Path("enquete/deletar/{idEnquete}")
	public Response removerEnquete(@PathParam("idEnquete") int idEnquete) {

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
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("curso/cadastrarCurso/")
	public Response cadastrarCurso(Curso curso) {

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
	@Security(NivelAcesso.NIVEL_1)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("setor/cadastrarSetor/")
	public Response cadastrarSetor(Setor setor) {

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