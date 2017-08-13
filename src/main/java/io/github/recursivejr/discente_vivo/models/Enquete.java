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
    private String emailAdmin;

    public Enquete(String nome, String descricao, String foto, int id, List<String> comentarios, List<String> opcoes, List<Resposta> respostas, String emailAdmin) {
        this.nome = nome;
        this.descricao = descricao;
        this.foto = foto;
        this.id = id;
        this.comentarios = comentarios;
        this.opcoes = opcoes;
        this.respostas = respostas;
        this.emailAdmin = emailAdmin;
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

    public void setComentarios(String comentario) {
        this.comentarios.add(comentario);
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(String opcao) {
        this.opcoes.add(opcao);
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(Resposta resposta) {
        this.respostas.add(resposta);
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enquete enquete = (Enquete) o;

        if (id != enquete.id) return false;
        if (!nome.equals(enquete.nome)) return false;
        if (!descricao.equals(enquete.descricao)) return false;
        if (foto != null ? !foto.equals(enquete.foto) : enquete.foto != null) return false;
        if (comentarios != null ? !comentarios.equals(enquete.comentarios) : enquete.comentarios != null) return false;
        if (opcoes != null ? !opcoes.equals(enquete.opcoes) : enquete.opcoes != null) return false;
        if (respostas != null ? !respostas.equals(enquete.respostas) : enquete.respostas != null) return false;
        return emailAdmin.equals(enquete.emailAdmin);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + descricao.hashCode();
        result = 31 * result + (foto != null ? foto.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (comentarios != null ? comentarios.hashCode() : 0);
        result = 31 * result + (opcoes != null ? opcoes.hashCode() : 0);
        result = 31 * result + (respostas != null ? respostas.hashCode() : 0);
        result = 31 * result + emailAdmin.hashCode();
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
                ", emailAdmin='" + emailAdmin + '\'' +
                '}';
    }
}
