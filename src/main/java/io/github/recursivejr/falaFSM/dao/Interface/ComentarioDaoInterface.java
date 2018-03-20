package io.github.recursivejr.falaFSM.dao.Interface;

import io.github.recursivejr.falaFSM.models.Comentario;

import java.util.List;

public interface ComentarioDaoInterface {

    public boolean adicionar(Comentario comentario);
    public boolean remover(Comentario comentario);
    public List<Comentario> listar();
    public List<Comentario> listarPorChave(int idFK);
}
