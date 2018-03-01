package io.github.recursivejr.discenteVivo.dao.Interface;

import io.github.recursivejr.discenteVivo.models.Campo;

import java.util.List;

public interface CampoDaoInterface {

    public Integer adicionar(Campo campo);
    public boolean remover(int idCampo);
    public boolean atualizar(Campo campo);
    public Campo buscar(int idCampo);
    public List<Campo> listar();
    public List<Campo> listarPorFormulario(int idFormulario, String matAluno);
    public boolean atualizarFoto(String foto, int idCampo);
    public String retornarFoto(int idCampo);
}
