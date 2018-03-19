package io.github.recursivejr.discenteVivo.models;

import io.github.recursivejr.discenteVivo.models.abstrato.Questionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Enquete extends Questionario {

    private List<Opcao> opcoes;
    private List<Resposta> respostas;

    public Enquete() {

    }

    public Enquete(int id, String emailAdmin, String nome, String descricao, String foto,
                   List<Curso> cursos, List<Setor> setores, List<Comentario> comentarios,
                   List<Opcao> opcoes, List<Resposta> respostas) {

        super(id, emailAdmin, nome, descricao, foto, cursos, setores, comentarios);
        this.opcoes = opcoes;
        this.respostas = respostas;
    }

    public List<Opcao> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<Opcao> opcoes) {
        this.opcoes = opcoes;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enquete)) return false;
        if (!super.equals(o)) return false;
        Enquete enquete = (Enquete) o;
        return Objects.equals(getOpcoes(), enquete.getOpcoes()) &&
                Objects.equals(getRespostas(), enquete.getRespostas());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getOpcoes(), getRespostas());
    }

    @Override
    public String toString() {
        return "Enquete{" +
                "opcoes=" + opcoes +
                ", respostas=" + respostas +
                '}';
    }
}

