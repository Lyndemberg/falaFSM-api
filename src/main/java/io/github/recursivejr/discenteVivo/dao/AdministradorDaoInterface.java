package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Administrador;

public interface AdministradorDaoInterface {

//    public boolean cadastrarAluno(Aluno aluno);
//    public boolean cadastrarAdminstrador(Administrador admin);
//    public Enquete cadastrarEnquete(Enquete enquete);
    public boolean adicionar(Administrador administrador);
    public boolean remover(Administrador administrador);
    public Administrador buscar(String pesquisa);
    public List<Administrador>   listar();
}
