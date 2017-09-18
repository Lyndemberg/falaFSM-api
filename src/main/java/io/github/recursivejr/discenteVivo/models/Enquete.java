package io.github.recursivejr.discenteVivo.models;

import java.util.List;

public class Enquete {

	private int id;
	private String emailAdmin;
    private String nome;
    private String descricao;
    private String foto;
    private List<Comentario> comentarios;
    private List<Opcao> opcoes;
    private List<Resposta> respostas;
    private List<Curso> cursos;
    private List<Setor> setores;
    private boolean respondida;

    public Enquete(int id, String emailAdmin, String nome, String descricao, String foto, List<Comentario> comentarios,
                   List<Opcao> opcoes, List<Resposta> respostas, List<Curso> cursos, List<Setor> setores, boolean respondida) {
        this.id = id;
        this.emailAdmin = emailAdmin;
        this.nome = nome;
        this.descricao = descricao;
        this.foto = foto;
        this.comentarios = comentarios;
        this.opcoes = opcoes;
        this.respostas = respostas;
        this.cursos = cursos;
        this.setores = setores;
        this.respondida = respondida;
    }

    public Enquete() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
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

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

    public boolean isRespondida() {
        return respondida;
    }

    public void setRespondida(boolean respondida) {
        this.respondida = respondida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enquete enquete = (Enquete) o;

        if (id != enquete.id) return false;
        if (respondida != enquete.respondida) return false;
        if (!emailAdmin.equals(enquete.emailAdmin)) return false;
        if (!nome.equals(enquete.nome)) return false;
        if (!descricao.equals(enquete.descricao)) return false;
        if (!foto.equals(enquete.foto)) return false;
        if (!comentarios.equals(enquete.comentarios)) return false;
        if (!opcoes.equals(enquete.opcoes)) return false;
        if (!respostas.equals(enquete.respostas)) return false;
        if (!cursos.equals(enquete.cursos)) return false;
        return setores.equals(enquete.setores);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + emailAdmin.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + descricao.hashCode();
        result = 31 * result + foto.hashCode();
        result = 31 * result + comentarios.hashCode();
        result = 31 * result + opcoes.hashCode();
        result = 31 * result + respostas.hashCode();
        result = 31 * result + cursos.hashCode();
        result = 31 * result + setores.hashCode();
        result = 31 * result + (respondida ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Enquete{" +
                "id=" + id +
                ", emailAdmin='" + emailAdmin + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", foto='" + foto + '\'' +
                ", comentarios=" + comentarios +
                ", opcoes=" + opcoes +
                ", respostas=" + respostas +
                ", cursos=" + cursos +
                ", setores=" + setores +
                ", respondida=" + respondida +
                '}';
    }
}

