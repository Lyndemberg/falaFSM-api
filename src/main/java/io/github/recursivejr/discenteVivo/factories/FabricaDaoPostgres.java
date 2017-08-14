package io.github.recursivejr.discenteVivo.factories;

import java.sql.SQLException;

import io.github.recursivejr.discenteVivo.dao.AdministradorDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.AlunoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.CursoDaoPostgres;
import io.github.recursivejr.discenteVivo.dao.EnqueteDaoPostgres;

public class FabricaDaoPostgres implements FabricaDaoInterface {

    @Override
    public AlunoDaoPostgres criarAlunoDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public AdministradorDaoPostgres criarAdministradorDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public CursoDaoPostgres criarCursoDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public EnqueteDaoPostgres criarEnqueteDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    /*@Override
    public ComentarioDAO criarComentarioDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SetorDAO criarSetorDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SugestaoDAO criarSugestaoDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public RespostaDAO criarRespostaDao() throws SQLException, ClassNotFoundException {
        return null;
    }*/
}
