package io.github.recursivejr.discenteVivo.dao.Interface;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Opcao;

public interface OpcaoDaoInterface {

    public boolean adicionar(int idEnquete, Opcao opcao);
    public boolean remover(Opcao opcao);
    public List<Opcao> listar();
    public List<Opcao> listarPorEnquete(int idEnquete);
}
