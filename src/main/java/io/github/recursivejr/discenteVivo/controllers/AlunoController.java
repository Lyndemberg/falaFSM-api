package io.github.recursivejr.discenteVivo.controllers;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.AlunoDaoInterface;
import io.github.recursivejr.discenteVivo.dao.ComentarioDaoInterface;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoInterface;
import io.github.recursivejr.discenteVivo.dao.RespostaDaoPostgres;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.*;

@Path("aluno")
public class AlunoController{

	@POST
	@Security
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("responder/{idEnquete}/{resposta}/")
	public Response responderEnquete(@PathParam("idEnquete") int idEnquete, @PathParam("resposta") String resposta,
			@Context ContainerRequestContext requestContext) {

		//Verifica se e um Aluno, caso nao seja entao retorna nao autorizado para o Cliente
		if (!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		//Caso token seja válido tenta salvar a nova resposta no BD
		try {
			//Cria um EnqueteDaoPostgres
			RespostaDaoPostgres respostaDao = new RespostaDaoPostgres();

			//Pega a matricula do aluno que esta respondendo a enquete pelo token de acesso
			String matAluno = requestContext
					.getSecurityContext()
						.getUserPrincipal()
							.getName();

			//Verifica se o Aluno pode Comentar nesta Enquete
			if(!checkEnquete(matAluno, idEnquete))
				return Response.status(Response.Status.UNAUTHORIZED).build();

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			Resposta resp = new Resposta(idEnquete, resposta, matAluno);
			if(respostaDao.adicionar(resp) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			System.gc();
			return Response.status(Response.Status.OK).build();

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
			System.gc();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("atualizarPerfil/")
	public Response atualizarPerfil(Aluno aluno, @Context ContainerRequestContext requestContext) {

		//Verifica se o token e valido para um aluno
		if (!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		System.out.printf("\n\nALUNO VAZIO? " + aluno.isEmpty());
		System.out.printf("\n\n\nALUNO POR ESCRITO? " + aluno.toString() + "\n\n");

		//Caso seja token seja valido verifica se o Aluno foi totalmente Preenchido
		if(!aluno.isEmpty()) {
			try {
				AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

				//Recebe a matricula pelo token
				String matricula = requestContext
						.getSecurityContext()
							.getUserPrincipal()
								.getName();

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
		} else {
			//Se aluno estiver vazio retorna diretamente BAD REQUEST para o cliente
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Security
	@Produces(MediaType.APPLICATION_JSON)
	@Path("perfil/")
	public Response getPerfil(@Context ContainerRequestContext requestContext) {

		//Verifica se o token e valido para um aluno
		if(!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso seja entao recupera o perfil com base na matricula do token
		try {
			//Cria Objeto AlunoDao com base na interface
			AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

			//Recupera a matricula do aluno pelo token
			String matricula =  requestContext
					.getSecurityContext()
						.getUserPrincipal()
							.getName();

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
	@Security
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("comentar/{idEnquete}/{comentario}/")
	public Response enviarComentario(@PathParam("idEnquete") int idEnquete, @PathParam("comentario") String comentario,
									 @Context ContainerRequestContext requestContext) {

		//Verifica se e um Aluno tentando enviar um comentario
		if(!FilterDetect.checkAluno(requestContext))
			return Response.status(Response.Status.UNAUTHORIZED).build();

		//Caso seja tenta enviar o Comentario
		try {
			//Cria um comentarioDao com base na Interface
			ComentarioDaoInterface comentarioDao = new FabricaDaoPostgres().criarComentarioDao();

			//Recupera a Matricula do Aluno com base no token
			String matAluno =  FilterDetect.getToken(requestContext);

			//Verifica se o Aluno pode Comentar nesta Enquete
			if(!checkEnquete(matAluno, idEnquete))
				return Response.status(Response.Status.UNAUTHORIZED).build();

			//Tenta Salvar o Comentario
			if (!comentarioDao.adicionar(new Comentario(
					matAluno,
					idEnquete,
					comentario
				))) {
				//Caso return false entao houve problema de SQL
				throw new Exception("ERRO DE SQL");
			}

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

			Enquete enquete = enqueteDao.buscar(idEnquete);
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

