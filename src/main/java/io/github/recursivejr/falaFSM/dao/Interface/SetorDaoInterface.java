package io.github.recursivejr.falaFSM.dao.Interface;

import java.util.List;

import io.github.recursivejr.falaFSM.models.Setor;

public interface SetorDaoInterface {

    public boolean adicionar(Setor setor);
    public boolean remover(Setor setor);
    public Setor buscar(String pesquisa);
    public List<Setor> listar();
}
