package io.github.recursivejr.discenteVivo.models;

import io.github.recursivejr.discenteVivo.models.abstrato.Questionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Enquete extends Questionario {

    private List<Comentario> comentarios;
    private List<Opcao> opcoes;
    private List<Resposta> respostas;
    private int idFormulario;

    public Enquete() {
        //Como idFormulario = -1 entao a Enquet nao pertence a nenhum Formulario
        idFormulario = -1;
    }

    public Enquete(int id, String emailAdmin, String nome, String descricao, String foto,
                   List<Curso> cursos, List<Setor> setores, List<Comentario> comentarios,
                   List<Opcao> opcoes, List<Resposta> respostas, int idFormulario) {

        super(id, emailAdmin, nome, descricao, foto, cursos, setores);
        this.comentarios = comentarios;
        this.opcoes = opcoes;
        this.respostas = respostas;
        this.idFormulario = idFormulario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
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

    public int getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(int idFormulario) {
        this.idFormulario = idFormulario;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Enquete)) return false;
        if (!super.equals(o)) return false;
        Enquete enquete = (Enquete) o;
        return getIdFormulario() == enquete.getIdFormulario() &&
                Objects.equals(getComentarios(), enquete.getComentarios()) &&
                Objects.equals(getOpcoes(), enquete.getOpcoes()) &&
                Objects.equals(getRespostas(), enquete.getRespostas());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getComentarios(), getOpcoes(), getRespostas(), getIdFormulario());
    }

    @Override
    public String toString() {

        return "Enquete{" +
                "comentarios=" + comentarios +
                ", opcoes=" + opcoes +
                ", respostas=" + respostas +
                ", idFormulario=" + idFormulario +
                '}';
    }
}

