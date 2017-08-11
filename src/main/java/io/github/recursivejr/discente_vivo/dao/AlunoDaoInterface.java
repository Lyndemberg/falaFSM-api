package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.models.Aluno;

import java.util.List;

public interface AlunoDaoInterface {

    public boolean adicionar(Aluno aluno);
    public boolean remover(Aluno aluno);
    public Aluno buscar(String Aluno);
    public List<Aluno> listar();

}
