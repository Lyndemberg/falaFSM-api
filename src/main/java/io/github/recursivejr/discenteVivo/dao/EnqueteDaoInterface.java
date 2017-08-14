package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Enquete;

public interface EnqueteDaoInterface {

    public boolean adicionar(Enquete enquete);
    public boolean remover(Enquete enquete);
    public Enquete buscar(String pesquisa);
    public List<Enquete> listar();
}
