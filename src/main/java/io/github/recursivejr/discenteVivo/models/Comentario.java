package io.github.recursivejr.discenteVivo.models;

public class Comentario {

    private int idComentario;
    private int idEnquete;
    private String comentario;

    public Comentario(int idComentario, int idEnquete, String comentario) {
        this.idComentario = idComentario;
        this.idEnquete = idEnquete;
        this.comentario = comentario;
    }

    public Comentario() {

    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comentario that = (Comentario) o;

        if (idComentario != that.idComentario) return false;
        if (idEnquete != that.idEnquete) return false;
        return comentario.equals(that.comentario);
    }

    @Override
    public int hashCode() {
        int result = idComentario;
        result = 31 * result + idEnquete;
        result = 31 * result + comentario.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "idComentario=" + idComentario +
                ", idEnquete=" + idEnquete +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
