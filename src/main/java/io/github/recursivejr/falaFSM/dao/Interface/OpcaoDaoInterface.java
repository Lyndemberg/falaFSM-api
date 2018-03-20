package io.github.recursivejr.falaFSM.dao.Interface;

import java.util.List;

import io.github.recursivejr.falaFSM.models.Opcao;

public interface OpcaoDaoInterface {

    public boolean adicionar(Opcao opcao);
    public boolean remover(Opcao opcao);
    public List<Opcao> listar();
    public List<Opcao> listarPorChave(int idFK);
}
