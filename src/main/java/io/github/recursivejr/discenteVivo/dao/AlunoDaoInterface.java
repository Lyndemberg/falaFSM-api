package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Aluno;

public interface AlunoDaoInterface {

    public boolean adicionar(Aluno aluno);
    public boolean remover(Aluno aluno);
    public Aluno buscar(String Aluno);
    public List<Aluno> listar();
    public String login(String login, String senha) throws Exception;

}
