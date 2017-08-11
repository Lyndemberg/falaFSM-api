package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.models.Curso;

import java.util.List;

public interface CursoDaoInterface {

    public boolean adicionar(Curso curso);
    public boolean remover(Curso curso);
    public Curso buscar(String pesquisa);
    public List<Curso> listar();
}
