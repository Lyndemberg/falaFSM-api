package io.github.recursivejr.discenteVivo.dao.Interface;

import java.sql.SQLException;
import java.util.List;

import io.github.recursivejr.discenteVivo.exceptions.AutenticacaoException;
import io.github.recursivejr.discenteVivo.models.Aluno;

public interface AlunoDaoInterface {

    public boolean adicionar(Aluno aluno);
    public boolean remover(Aluno aluno);
    public Aluno buscar(String matricula);
    public List<Aluno> listar();
    public boolean atualizar(Aluno aluno);
    public Aluno login(String login, String senha) throws AutenticacaoException, SQLException;

}
