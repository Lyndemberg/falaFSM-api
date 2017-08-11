package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.models.Enquete;

import java.util.List;

public interface EnqueteDaoInterface {

    public boolean adicionar(Enquete enquete);
    public boolean remover(Enquete enquete);
    public Enquete buscar(String pesquisa);
    public List<Enquete> listar();
}
