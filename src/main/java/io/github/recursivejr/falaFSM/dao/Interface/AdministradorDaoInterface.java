package io.github.recursivejr.falaFSM.dao.Interface;

import java.sql.SQLException;
import java.util.List;

import io.github.recursivejr.falaFSM.exceptions.AutenticacaoException;
import io.github.recursivejr.falaFSM.models.Administrador;

public interface AdministradorDaoInterface {

    public boolean adicionar(Administrador administrador);
    public boolean remover(Administrador administrador);
    public Administrador buscar(String pesquisa);
    public List<Administrador>  listar();
    public boolean atualizar(Administrador administrador);
    public Administrador login(String login, String senha) throws AutenticacaoException, SQLException;
}
