package io.github.recursivejr.discenteVivo.models;

import io.github.recursivejr.discenteVivo.models.abstrato.Questionario;

import java.util.List;
import java.util.Objects;

public class Formulario extends Questionario {

    private List<Campo> campos;

    public Formulario() {

    }

    public Formulario(int id, String emailAdmin, String nome, String descricao, String foto,
                      List<Curso> cursos, List<Setor> setores, List<Comentario> comentarios,
                      List<Campo> campos) {

        super(id, emailAdmin, nome, descricao, foto, cursos, setores, comentarios);
        this.campos = campos;
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Formulario)) return false;
        if (!super.equals(o)) return false;
        Formulario that = (Formulario) o;
        return Objects.equals(getCampos(), that.getCampos());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((campos == null) ? 0 : campos.hashCode());

        return result;
    }

    @Override
    public String toString() {

        return "Formulario{" +
                "campos=" + campos +
                '}';
    }
}
