package io.github.recursivejr.discente_vivo.factories;

import java.sql.SQLException;

import io.github.recursivejr.discente_vivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.CursoDaoPostgres;
import io.github.recursivejr.discente_vivo.dao.EnqueteDaoPostgres;

public interface FabricaDaoInterface {
    
    public AlunoDaoPostgres criarAlunoDao() throws SQLException, ClassNotFoundException;
    public AdministradorDaoPostgres criarAdministradorDao() throws SQLException, ClassNotFoundException;
    public CursoDaoPostgres criarCursoDao() throws SQLException, ClassNotFoundException;
    public EnqueteDaoPostgres criarEnqueteDao() throws SQLException, ClassNotFoundException;
    /*public ComentarioDAO criarComentarioDao() throws SQLException, ClassNotFoundException;
    public SetorDAO criarSetorDao() throws SQLException, ClassNotFoundException;
    public SugestaoDAO criarSugestaoDao() throws SQLException, ClassNotFoundException;
    public RespostaDAO criarRespostaDao() throws SQLException, ClassNotFoundException;*/
}
