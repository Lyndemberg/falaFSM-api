package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Opcao;

public interface OpcaoDaoInterface {

    public boolean adicionar(Opcao opcao);
    public boolean remover(Opcao opcao);
    //public Opcao buscar(int IdEnquete); Não Faz Sentido este metodo
    public List<Opcao> listar();
}
