package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.models.Sugestao;

import java.util.List;

public interface SugestaoDaoInterface {

    public boolean adicionar(Sugestao sugestao);
    public boolean remover(Sugestao sugestao);
    public Sugestao buscar(String pesquisa);
    public List<Sugestao> listar();
}
