package io.github.recursivejr.discenteVivo.controllers;

import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.recursivejr.discenteVivo.dao.AlunoDaoInterface;
import io.github.recursivejr.discenteVivo.dao.RespostaDaoPostgres;
import io.github.recursivejr.discenteVivo.factories.FabricaDaoPostgres;
import io.github.recursivejr.discenteVivo.infraSecurity.FilterDetect;
import io.github.recursivejr.discenteVivo.infraSecurity.Security;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.models.Resposta;

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

			//Tenta salvar, se retornar false deu SQL exeption, se deu true então salvou com sucesso
			Resposta resp = new Resposta(idEnquete, resposta, matAluno);
			if(respostaDao.adicionar(resp) == false)
				throw new Exception("ERRO DE SQL");

			//Se tudo certo retorna status 200
			return Response.status(Response.Status.OK).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
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

		//Caso seja token seja valido verifica se o Aluno foi totalmente Preenchido
		if(!aluno.isEmpty()) {
			try {
				AlunoDaoInterface alunoDao = new FabricaDaoPostgres().criarAlunoDao();

				//Recebe a matricula pelo token
				String matricula = requestContext
						.getSecurityContext()
							.getUserPrincipal()
								.getName();

				//Caso a matricula do token seja diferente do aluno entao este usuario esta tentando alterar perfil
					//de outro aluno
				if (!matricula.equals(aluno.getMatricula()))
					return Response.status(Response.Status.UNAUTHORIZED).build();

				//Se ao tentar salvar retornar false entao houve erro durante a execucao do SQL
				if(alunoDao.atualizar(aluno) == false)
					throw new Exception("ERRO DE SQL");

				//Se tudo foi executado corretamente retorna codigo 200 de OK para o cliente
				return Response.status(Response.Status.OK).build();
			} catch (Exception ex) {
				ex.printStackTrace();
				Logger.getLogger("AlunoController-log").info("Erro:" + ex.getStackTrace());
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
		} else {
			//Se aluno estiver vazio retorna diretamente BAD REQUEST para o cliente
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

}
