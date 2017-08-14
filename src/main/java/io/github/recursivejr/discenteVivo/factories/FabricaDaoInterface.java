package io.github.recursivejr.discenteVivo.factories;

import java.sql.SQLException;

import io.github.recursivejr.discenteVivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.CursoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;

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
