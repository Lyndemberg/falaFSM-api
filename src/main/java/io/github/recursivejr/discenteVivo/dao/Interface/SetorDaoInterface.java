package io.github.recursivejr.discenteVivo.dao.Interface;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Setor;

public interface SetorDaoInterface {

    public boolean adicionar(Setor setor);
    public boolean remover(Setor setor);
    public Setor buscar(String pesquisa);
    public List<Setor> listar();
}
