package io.github.recursivejr.discenteVivo.models;

public class Comentario {

    private String matriculaAluno;
    private int idEnquete;
    private String comentario;

    public Comentario(String matriculaAluno, int idEnquete, String comentario) {
        this.matriculaAluno = matriculaAluno;
        this.idEnquete = idEnquete;
        this.comentario = comentario;
    }

    public Comentario() {

    }

    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public int getIdEnquete() {
        return idEnquete;
    }

    public void setIdEnquete(int idEnquete) {
        this.idEnquete = idEnquete;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "matriculaAluno='" + matriculaAluno + '\'' +
                ", idEnquete=" + idEnquete +
                ", comentario='" + comentario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comentario that = (Comentario) o;

        if (idEnquete != that.idEnquete) return false;
        if (!matriculaAluno.equals(that.matriculaAluno)) return false;
        return comentario.equals(that.comentario);
    }

    @Override
    public int hashCode() {
        int result = matriculaAluno.hashCode();
        result = 31 * result + idEnquete;
        result = 31 * result + comentario.hashCode();
        return result;
    }
}