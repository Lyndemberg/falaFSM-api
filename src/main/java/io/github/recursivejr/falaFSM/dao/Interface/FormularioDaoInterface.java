package io.github.recursivejr.falaFSM.dao.Interface;

import io.github.recursivejr.falaFSM.models.Formulario;

import java.util.List;

public interface FormularioDaoInterface {

    public Integer adicionar(Formulario formulario);
    public boolean remover(int idFormulario);
    public boolean atualizar(Formulario formulario);
    public Formulario buscar(int idFormulario, String matAluno);
    public List<Formulario> listar();
    public List<Formulario> listarPorAluno(String matAluno);
    public List<Formulario> formulariosPorSetor(String nomeSetor, String matAluno);
    public List<Formulario> formulariosPorCurso(String nomeCurso, String matAluno);
    public boolean atualizarFoto(String foto, int idFormulario);
    public String retornarFoto(int idFormulario);
}
