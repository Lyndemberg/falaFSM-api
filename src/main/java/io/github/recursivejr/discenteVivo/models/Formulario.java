package io.github.recursivejr.discenteVivo.models;

import io.github.recursivejr.discenteVivo.models.abstrato.Questionario;

import java.util.List;
import java.util.Objects;

public class Formulario extends Questionario {

    private List<Enquete> enquetes;

    public Formulario(int id, String emailAdmin, String nome, String descricao, String foto,
                      List<Curso> cursos, List<Setor> setores, List<Enquete> enquetes) {

        super(id, emailAdmin, nome, descricao, foto, cursos, setores);
        this.enquetes = enquetes;
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
        if (!(o instanceof Formulario)) return false;
        if (!super.equals(o)) return false;
        Formulario that = (Formulario) o;
        return Objects.equals(getEnquetes(), that.getEnquetes());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getEnquetes());
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "enquetes=" + enquetes +
                '}';
    }
}
