package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Administrador;

public interface AdministradorDaoInterface {

    public boolean adicionar(Administrador administrador);
    public boolean remover(Administrador administrador);
    public Administrador buscar(String pesquisa);
    public List<Administrador>  listar();
    public boolean atualizar(Administrador administrador);
    public String login(String login, String senha) throws Exception;
}
