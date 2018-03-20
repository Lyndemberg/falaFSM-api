package io.github.recursivejr.falaFSM.dao.Interface;

import java.util.List;

import io.github.recursivejr.falaFSM.models.Resposta;

public interface RespostaDaoInterface {

    public boolean adicionar(Resposta resposta);
    public boolean remover(Resposta resposta);
    public Resposta buscar(String matAluno, int idFK);
    public List<Resposta> listar();
}
