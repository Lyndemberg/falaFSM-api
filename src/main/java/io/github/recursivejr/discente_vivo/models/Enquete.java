package io.github.recursivejr.discente_vivo.models;

import java.util.List;

public class Enquete {

    private String nome;
    private String descricao;
    private String foto;
    private int id;
    private List<String> comentarios;
    private List<String> opcoes;
    private List<Resposta> respostas;

    public Enquete(String nome, String descricao, String foto, int id, List<String> comentarios, List<String> opcoes, List<Resposta> respostas) {
        this.nome = nome;
        this.descricao = descricao;
        this.foto = foto;
        this.id = id;
        this.comentarios = comentarios;
        this.opcoes = opcoes;
        this.respostas = respostas;
    }

    public Enquete() {

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<String> opcoes) {
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
        if (o == null || getClass() != o.getClass()) return false;

        Enquete enquete = (Enquete) o;

        if (id != enquete.id) return false;
        if (!nome.equals(enquete.nome)) return false;
        if (!descricao.equals(enquete.descricao)) return false;
        if (!foto.equals(enquete.foto)) return false;
        if (!comentarios.equals(enquete.comentarios)) return false;
        if (!opcoes.equals(enquete.opcoes)) return false;
        return respostas.equals(enquete.respostas);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + descricao.hashCode();
        result = 31 * result + foto.hashCode();
        result = 31 * result + id;
        result = 31 * result + comentarios.hashCode();
        result = 31 * result + opcoes.hashCode();
        result = 31 * result + respostas.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Enquete{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", foto='" + foto + '\'' +
                ", id=" + id +
                ", comentarios=" + comentarios +
                ", opcoes=" + opcoes +
                ", respostas=" + respostas +
                '}';
    }
}
