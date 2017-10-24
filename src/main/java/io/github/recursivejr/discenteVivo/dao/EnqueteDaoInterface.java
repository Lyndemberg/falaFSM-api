package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Enquete;

public interface EnqueteDaoInterface {

    public Integer adicionar(Enquete enquete);
    public boolean remover(int idEnquete);
    public boolean atualizar(Enquete enquete);
    public Enquete buscar(int idEnquete, String matAluno);
    public List<Enquete> listar(String matAluno);
    public List<Enquete> enquetesPorSetor(String nomeSetor, String matAluno);
    public List<Enquete> enquetesPorCurso(String nomeCurso, String matAluno);
    public boolean atualizarFoto(String foto, int idEnquete);
    public String retornarFoto(int idEnquete);
}
