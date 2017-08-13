package io.github.recursivejr.discente_vivo.factories;

import io.github.miolivc.dao.*;

import java.sql.SQLException;

public class FabricaDaoPostgres implements FabricaDaoInterface {

    @Override
    public AlunoDAO criarAlunoDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public AdministradorDAO criarAdministradorDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public CursoDAO criarCursoDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public EnqueteDAO criarEnqueteDao() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
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
    }
}
