package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.models.Resposta;

import java.util.List;

public interface RespostaDaoInterface {

    public boolean adicionar(Resposta resposta);
    public boolean remover(Resposta resposta);
    public Resposta buscar(String pesquisa);
    public List<Resposta> listar();
}
