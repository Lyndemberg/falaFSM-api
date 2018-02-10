package io.github.recursivejr.discenteVivo.dao.Interface;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Resposta;

public interface RespostaDaoInterface {

    public boolean adicionar(Resposta resposta);
    public boolean remover(Resposta resposta);
    public Resposta buscar(String matAluno, int IdEnquete);
    public List<Resposta> listar();
}
