package io.github.recursivejr.falaFSM.dao.Interface;

import java.util.List;

import io.github.recursivejr.falaFSM.models.Curso;

public interface CursoDaoInterface {

    public boolean adicionar(Curso curso);
    public boolean remover(Curso curso);
    public Curso buscar(String pesquisa);
    public List<Curso> listar();
}
