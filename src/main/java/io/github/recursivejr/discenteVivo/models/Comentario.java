package io.github.recursivejr.discenteVivo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Comentario {

    //idFK = id Foreing key, ou seja, o id da Tabela na qual se faz chave estrangeira
    @JsonIgnore
    private int idFK;
    private String matriculaAluno;
    private String comentario;

    public Comentario() {

    }

    public Comentario(int idFK, String matriculaAluno, String comentario) {
        this.idFK = idFK;
        this.matriculaAluno = matriculaAluno;
        this.comentario = comentario;
    }

    public int getIdFK() {
        return idFK;
    }

    public void setIdFK(int idFK) {
        this.idFK = idFK;
    }

    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comentario that = (Comentario) o;
        return getIdFK() == that.getIdFK() &&
                Objects.equals(getMatriculaAluno(), that.getMatriculaAluno()) &&
                Objects.equals(getComentario(), that.getComentario());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdFK(), getMatriculaAluno(), getComentario());
    }

    @Override
    public String toString() {

        return "Comentario{" +
                "idFK=" + idFK +
                ", matriculaAluno='" + matriculaAluno + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}