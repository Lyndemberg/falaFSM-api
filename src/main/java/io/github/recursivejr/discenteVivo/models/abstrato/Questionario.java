package io.github.recursivejr.discenteVivo.models.abstrato;

import io.github.recursivejr.discenteVivo.models.Curso;
import io.github.recursivejr.discenteVivo.models.Setor;

import java.util.List;
import java.util.Objects;

public abstract class Questionario {

    private int id;
    private String emailAdmin;
    private String nome;
    private String descricao;
    private String foto;
    private List<Curso> cursos;
    private List<Setor> setores;

    public Questionario() {

    }

    public Questionario(int id, String emailAdmin, String nome, String descricao,
                        String foto, List<Curso> cursos, List<Setor> setores) {

        this.id = id;
        this.emailAdmin = emailAdmin;
        this.nome = nome;
        this.descricao = descricao;
        this.foto = foto;
        this.cursos = cursos;
        this.setores = setores;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questionario that = (Questionario) o;
        return id == that.id &&
                Objects.equals(emailAdmin, that.emailAdmin) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(descricao, that.descricao) &&
                Objects.equals(foto, that.foto) &&
                Objects.equals(cursos, that.cursos) &&
                Objects.equals(setores, that.setores);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, emailAdmin, nome, descricao, foto, cursos, setores);
    }

    @Override
    public String toString() {

        return "Questionario{" +
                "id=" + id +
                ", emailAdmin='" + emailAdmin + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", foto='" + foto + '\'' +
                ", cursos=" + cursos +
                ", setores=" + setores +
                '}';
    }
}
