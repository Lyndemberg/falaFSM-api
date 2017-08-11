package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.models.Administrador;

import java.util.List;

public interface AdministradorDaoInterface {

//    public boolean cadastrarAluno(Aluno aluno);
//    public boolean cadastrarAdminstrador(Administrador admin);
//    public Enquete cadastrarEnquete(Enquete enquete);
    public boolean adicionar(Administrador administrador);
    public boolean remover(Administrador administrador);
    public Administrador buscar(String pesquisa);
    public List<Administrador>   listar();
}
