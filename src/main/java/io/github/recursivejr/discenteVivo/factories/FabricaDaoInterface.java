package io.github.recursivejr.discenteVivo.factories;

import java.sql.SQLException;

import io.github.recursivejr.discenteVivo.dao.postgres.*;

public interface FabricaDaoInterface {
    
    public AlunoDaoPostgres criarAlunoDao() throws SQLException, ClassNotFoundException;
    public AdministradorDaoPostgres criarAdministradorDao() throws SQLException, ClassNotFoundException;
    public CursoDaoPostgres criarCursoDao() throws SQLException, ClassNotFoundException;
    public EnqueteDaoPostgres criarEnqueteDao() throws SQLException, ClassNotFoundException;
    public ComentarioEnqueteDaoPostgres criarComentarioEnqueteDao() throws SQLException, ClassNotFoundException;
    public ComentarioFormularioDaoPostgres criarComentarioFormularioDao() throws SQLException, ClassNotFoundException;
    public SetorDaoPostgres criarSetorDao() throws SQLException, ClassNotFoundException;
    public RespostaEnqueteDaoPostgres criarRespostaEnqueteDao() throws ClassNotFoundException, SQLException;
    public RespostaCampoDaoPostgres criarRespostaCampoDao() throws ClassNotFoundException, SQLException;
    public OpcaoEnqueteDaoPostgres criarOpcaoEnqueteDao() throws ClassNotFoundException, SQLException;
    public OpcaoCampoDaoPostgres criarOpcaoCampoDao() throws ClassNotFoundException, SQLException;
    public RelatorioDaoPostgres criarRelatorioDao() throws ClassNotFoundException, SQLException;
    public FormularioDaoPostgres criarFormularioDao() throws ClassNotFoundException, SQLException;
    public CampoDaoPostgres criarCampoDao() throws ClassNotFoundException, SQLException;
}
