package io.github.recursivejr.discenteVivo.models;

import java.util.List;

public class Curso {

    private String nome;
    private String descricao;
    private List<Aluno> alunos;
    private List<Enquete> enquetes;

    public Curso(String nome, String descricao, List<Aluno> alunos, List<Enquete> enquetes) {
        this.nome = nome;
        this.descricao = descricao;
        this.alunos = alunos;
        this.enquetes = enquetes;
    }

    public Curso() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
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
        if (!descricao.equals(curso.descricao)) return false;
        if (!alunos.equals(curso.alunos)) return false;
        return enquetes.equals(curso.enquetes);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + descricao.hashCode();
        result = 31 * result + alunos.hashCode();
        result = 31 * result + enquetes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", alunos=" + alunos +
                ", enquetes=" + enquetes +
                '}';
    }
}