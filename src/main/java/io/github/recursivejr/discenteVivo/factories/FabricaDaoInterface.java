package io.github.recursivejr.discenteVivo.factories;

import java.sql.SQLException;

import io.github.recursivejr.discenteVivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.CursoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.OpcaoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.RespostaDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.SetorDaoPostgres;

public interface FabricaDaoInterface {
    
    public AlunoDaoPostgres criarAlunoDao() throws SQLException, ClassNotFoundException;
    public AdministradorDaoPostgres criarAdministradorDao() throws SQLException, ClassNotFoundException;
    public CursoDaoPostgres criarCursoDao() throws SQLException, ClassNotFoundException;
    public EnqueteDaoPostgres criarEnqueteDao() throws SQLException, ClassNotFoundException;
  //Public ComentarioDAO criarComentarioDao() throws SQLException, ClassNotFoundException;
    public SetorDaoPostgres criarSetorDao() throws SQLException, ClassNotFoundException;
    public RespostaDaoPostgres criarRespostaDao() throws ClassNotFoundException, SQLException;    
    public OpcaoDaoPostgres criarOpcaoDao() throws ClassNotFoundException, SQLException;
}
