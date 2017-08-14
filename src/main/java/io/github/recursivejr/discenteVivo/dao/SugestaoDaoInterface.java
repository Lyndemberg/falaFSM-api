package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Sugestao;

public interface SugestaoDaoInterface {

    public boolean adicionar(Sugestao sugestao);
    public boolean remover(Sugestao sugestao);
    public Sugestao buscar(String pesquisa);
    public List<Sugestao> listar();
}
