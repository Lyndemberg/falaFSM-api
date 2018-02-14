package io.github.recursivejr.discenteVivo.controllers;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.github.recursivejr.discenteVivo.dao.Interface.*;
import io.github.recursivejr.discenteVivo.dao.postgres.RespostaCampoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.postgres.RespostaEnqueteDaoPostgres;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.TokenManagement;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.infraSecurity.model.NivelAcesso;
import io.github.recursivejr.discenteVivo.models.*;

@Path("aluno")
public class AlunoController{

	@POST
	@Security(NivelAcesso.NIVEL_2)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("responder/enquete/")
	public Response responderEnquete(@FormParam("idEnquete") int idEnquete,
									 @FormParam("resposta") String resposta,
									 @Context SecurityContext securityContext) {

		try {
			//Cria um RespostaDao para Enquete
			RespostaDaoInterface respostaDao = Fabrica.criarFabricaDaoPostgres().criarRespostaEnqueteDao();

			//Pega a matricula do aluno que esta respondendo a enquete pelo token de acesso
			String matAluno = TokenManagement.getToken(securityContext);

			//Verifica se o Aluno pode Comentar nesta Enquete
			if(!checkEnquete(matAluno, idEnquete))
				return Response.status(Response.Status.FORBIDDEN).build();

			//Tenta salvar, se retornar false deu Algum problema logo retona BAD_REQUEST
				//se deu true então salvou com sucesso
			Resposta resp = new Resposta(idEnquete, resposta, matAluno);
			if(respostaDao.adicionar(resp) == false)
				return Response.status(Response.Status.BAD_REQUEST).build();

			//Se tudo certo retorna status 200
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Security(NivelAcesso.NIVEL_2)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("responder/campo/")
	public Response responderCampo(@FormParam("idCampo") int idCampo,
								   @FormParam("resposta") String resposta,
								   @Context SecurityContext securityContext) {

		try {
			//Cria um RespostaDao para Campos
			RespostaDaoInterface respostaDao = Fabrica.criarFabricaDaoPostgres().criarRespostaCampoDao();

			//Pega a matricula do aluno que esta respondendo o Campo pelo token de acesso
			String matAluno = TokenManagement.getToken(securityContext);

			//Tenta salvar, se retornar false deu Algum problema logo retona BAD_REQUEST
				//se deu true então salvou com sucesso
			Resposta resp = new Resposta(idCampo, resposta, matAluno);
			if(respostaDao.adicionar(resp) == false)
				return Response.status(Response.Status.BAD_REQUEST).build();

			//Se tudo certo retorna status 200
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@PUT
	@Security(NivelAcesso.NIVEL_2)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("atualizarPerfil/")
	public Response atualizarPerfil(Aluno aluno, @Context SecurityContext securityContext) {

		//verifica se o Aluno foi totalmente Preenchido
			//Se aluno estiver vazio retorna diretamente BAD REQUEST para o cliente
		if(aluno.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();

		//Se o Aluno foi preenchido corretamente entao Tenta Atualizar
		try {
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			//Recebe a matricula pelo token
			String matricula = TokenManagement.getToken(securityContext);

			//Insere no Aluno a Matricula provida pelo Token
			aluno.setMatricula(matricula);

			//Se ao tentar salvar retornar false entao houve erro durante a execucao do SQL
			if(alunoDao.atualizar(aluno) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo foi executado corretamente retorna codigo 200 de OK para o cliente
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@GET
	@Security(NivelAcesso.NIVEL_2)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("perfil/")
	public Response getPerfil(@Context SecurityContext securityContext) {

		//Recupera o perfil com base na matricula do token
		try {
			//Cria Objeto AlunoDao com base na interface
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			//Recupera a matricula do aluno pelo token
			String matricula =  TokenManagement.getToken(securityContext);

			//Retorna Reposta com codigo 200 de OK contendo o Objeto Aluno
			Aluno aluno = alunoDao.buscar(matricula);
			System.gc();
            return Response.ok(aluno).build();

		} catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
	}

	@POST
	@Security(NivelAcesso.NIVEL_2)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("comentar/enquete/")
	public Response comentarEnquete(@FormParam("idEnquete") int idEnquete,
									@FormParam("comentario") String comentario,
									@Context SecurityContext securityContext) {

		try {
			//Cria um comentarioDao com base na Interface
			ComentarioDaoInterface comentarioDao = new FabricaDaoPostgres()
															.criarComentarioEnqueteDao();

			//Recupera a Matricula do Aluno com base no token
			String matAluno = TokenManagement.getToken(securityContext);

			//Verifica se o Aluno pode Comentar nesta Enquete
			if(!checkEnquete(matAluno, idEnquete))
				return Response.status(Response.Status.FORBIDDEN).build();

			//Cria um Objeto Comentario
			Comentario objComentario = new Comentario(
					idEnquete,
					matAluno,
					comentario
			);

			//Tenta Salvar o Comentario
			if (!comentarioDao.adicionar(objComentario))
				//Caso return false entao houve problema de SQL
				throw new Exception("ERRO DE SQL");

			//Se o comentario for enviado com sucesso retorna Codigo 200 de OK
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	//Verifica se o Aluno esta Interagindo com uma Enquete que esta Associada a seu Curso
	private boolean checkEnquete(String matAluno, int idEnquete) {
		//Retorna True se aluno tem permissao para acessar esta enquete
		//Retorna False se aluno nao tem permissao para acessar esta enquete
		try {

			EnqueteDaoInterface enqueteDao = new FabricaDaoPostgres().criarEnqueteDao();
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			Enquete enquete = enqueteDao.buscar(idEnquete, matAluno);
			Aluno aluno = alunoDao.buscar(matAluno);

			List<Curso> cursos = enquete.getCursos();

			//Caso a variavel cursos estaja vazia entao a enquete nao esta associada a nenhum curso
				//logo todos os alunos tem permissao a acessa-la
			if (cursos.isEmpty())
				return true;

			//Retorna true se o curso do aluno estiver dentro da variavel cursos
			for (Curso curso: cursos) {
				if (aluno.getCurso()
						.equals(
								curso.getNome()
						)) {
					System.gc();
					return true;
				}
			}

			//Caso nao esteja retorna false
			System.gc();
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return false;
		}

	}
}

