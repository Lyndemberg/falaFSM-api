package io.github.recursivejr.discente_vivo.factories;

import java.sql.SQLException;

public interface FabricaDaoInterface {
    
    public AlunoDAO criarAlunoDao() throws SQLException, ClassNotFoundException;
    public AdministradorDAO criarAdministradorDao() throws SQLException, ClassNotFoundException;
    public CursoDAO criarCursoDao() throws SQLException, ClassNotFoundException;
    public EnqueteDAO criarEnqueteDao() throws SQLException, ClassNotFoundException;
    public ComentarioDAO criarComentarioDao() throws SQLException, ClassNotFoundException;
    public SetorDAO criarSetorDao() throws SQLException, ClassNotFoundException;
    public SugestaoDAO criarSugestaoDao() throws SQLException, ClassNotFoundException;
    public RespostaDAO criarRespostaDao() throws SQLException, ClassNotFoundException;
}
