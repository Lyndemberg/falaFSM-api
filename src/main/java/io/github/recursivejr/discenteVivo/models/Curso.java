package io.github.recursivejr.discenteVivo.models;

import java.util.List;
import java.util.Objects;

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
        return Objects.equals(nome, curso.nome) &&
                Objects.equals(descricao, curso.descricao) &&
                Objects.equals(alunos, curso.alunos) &&
                Objects.equals(enquetes, curso.enquetes);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((enquetes == null) ? 0 : enquetes.hashCode());
        result = prime * result + ((alunos == null) ? 0 : alunos.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());

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