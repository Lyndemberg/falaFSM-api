package io.github.recursivejr.discente_vivo.models;

import java.util.List;

public class Curso {

    private String nome;
    private Aluno aluno;
    private List<Enquete> enquetes;

    public Curso(String nome, Aluno aluno, List<Enquete> enquetes) {
        this.nome = nome;
        this.aluno = aluno;
        this.enquetes = enquetes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Enquete> getEnquetes() {
        return enquetes;
    }

    public void setEnquetes(List<Enquete> enquetes) {
        this.enquetes = enquetes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Curso curso = (Curso) o;

        if (!nome.equals(curso.nome)) return false;
        if (!aluno.equals(curso.aluno)) return false;
        return enquetes.equals(curso.enquetes);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + aluno.hashCode();
        result = 31 * result + enquetes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nome='" + nome + '\'' +
                ", aluno=" + aluno +
                ", enquetes=" + enquetes +
                '}';
    }
}
