package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.models.Setor;

import java.util.List;

public interface SetorDaoInterface {

    public boolean adicionar(Setor setor);
    public boolean remover(Setor setor);
    public Setor buscar(String pesquisa);
    public List<Setor> listar();
}
