package io.github.recursivejr.discenteVivo.factories;

import java.sql.SQLException;

import io.github.recursivejr.discenteVivo.dao.postgres.*;

public class FabricaDaoPostgres implements FabricaDaoInterface {

	@Override
	public AlunoDaoPostgres criarAlunoDao() throws SQLException, ClassNotFoundException {
		AlunoDaoPostgres alunoDao = new AlunoDaoPostgres();
		return alunoDao;
	}

	@Override
	public AdministradorDaoPostgres criarAdministradorDao() throws SQLException, ClassNotFoundException {
		AdministradorDaoPostgres adminDao = new AdministradorDaoPostgres();
		return adminDao;
	}

	@Override
	public CursoDaoPostgres criarCursoDao() throws SQLException, ClassNotFoundException {
		CursoDaoPostgres cursoDao = new CursoDaoPostgres();
		return cursoDao;
	}

	@Override
	public EnqueteDaoPostgres criarEnqueteDao() throws SQLException, ClassNotFoundException {
		EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
		return enqueteDao;
	}

	 @Override
    public ComentarioEnqueteDaoPostgres criarComentarioEnqueteDao() throws SQLException, ClassNotFoundException {
        ComentarioEnqueteDaoPostgres comentarioDao = new ComentarioEnqueteDaoPostgres();
        return comentarioDao;
    }

	@Override
	public ComentarioFormularioDaoPostgres criarComentarioFormularioDao() throws SQLException, ClassNotFoundException {
		ComentarioFormularioDaoPostgres comentarioDao = new ComentarioFormularioDaoPostgres();
		return comentarioDao;
	}

	@Override
	public SetorDaoPostgres criarSetorDao() throws SQLException, ClassNotFoundException {
		SetorDaoPostgres setorDao = new SetorDaoPostgres();
		return setorDao;
	}

	@Override
	public RespostaEnqueteDaoPostgres criarRespostaEnqueteDao() throws ClassNotFoundException, SQLException {
		RespostaEnqueteDaoPostgres respDao = new RespostaEnqueteDaoPostgres();
		return respDao;
	}

	@Override
	public RespostaCampoDaoPostgres criarRespostaCampoDao() throws ClassNotFoundException, SQLException {
		RespostaCampoDaoPostgres respDao = new RespostaCampoDaoPostgres();
		return respDao;
	}
	
	@Override
	public OpcaoEnqueteDaoPostgres criarOpcaoEnqueteDao() throws ClassNotFoundException, SQLException {
		OpcaoEnqueteDaoPostgres opcaoDao = new OpcaoEnqueteDaoPostgres();
		return opcaoDao;
	}

	@Override
	public OpcaoCampoDaoPostgres criarOpcaoCampoDao() throws ClassNotFoundException, SQLException {
		OpcaoCampoDaoPostgres opcaoDao = new OpcaoCampoDaoPostgres();
		return opcaoDao;
	}

	@Override
	public RelatorioDaoPostgres criarRelatorioDao() throws ClassNotFoundException, SQLException{
		RelatorioDaoPostgres relatorioDao = new RelatorioDaoPostgres();
		return relatorioDao;
	}
	
}
