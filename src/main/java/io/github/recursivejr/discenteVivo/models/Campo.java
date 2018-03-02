package io.github.recursivejr.discenteVivo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.recursivejr.discenteVivo.models.abstrato.Questionario;

import java.util.List;
import java.util.Objects;

public class Campo {

    private int id;
    private String nome;
    private String descricao;
    @JsonIgnore
    private String foto;
    @JsonIgnore
    private int idFormulario;
    private List<Opcao> opcoes;
    private List<Resposta> respostas;

    public Campo() {

    }

    public Campo(int id, String nome, String descricao, String foto, int idFormulario,
                 List<Opcao> opcoes, List<Resposta> respostas) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.foto = foto;
        this.idFormulario = idFormulario;
        this.opcoes = opcoes;
        this.respostas = respostas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(int idFormulario) {
        this.idFormulario = idFormulario;
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
        if (o == null || getClass() != o.getClass()) return false;
        Campo campo = (Campo) o;
        return getId() == campo.getId() &&
                getIdFormulario() == campo.getIdFormulario() &&
                Objects.equals(getNome(), campo.getNome()) &&
                Objects.equals(getDescricao(), campo.getDescricao()) &&
                Objects.equals(getFoto(), campo.getFoto()) &&
                Objects.equals(getOpcoes(), campo.getOpcoes()) &&
                Objects.equals(getRespostas(), campo.getRespostas());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getNome(), getDescricao(), getFoto(), getIdFormulario(), getOpcoes(), getRespostas());
    }

    @Override
    public String toString() {

        return "Campo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", foto='" + foto + '\'' +
                ", idFormulario=" + idFormulario +
                ", opcoes=" + opcoes +
                ", respostas=" + respostas +
                '}';
    }

    //Este metodo funciona de forma Similar ao equal Comum porem desconsidera os atributos :
        //* Id
        //* IdFormulario
        //* Foto
        //* Repostas
    //Sao desconsiderados estes atributos pois estao presentes apenas nos objetos ja salvos no BD,
    //Caso queira comprar um elemento salvo no Banco de Dados com um Elemento Enviado pelo Cliente
    //ou criado em tempo de Execuçao Utilize este metodo
    public boolean simpleEquals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campo campo = (Campo) o;
        return Objects.equals(getNome(), campo.getNome()) &&
                Objects.equals(getDescricao(), campo.getDescricao()) &&
                Objects.equals(getOpcoes(), campo.getOpcoes());
    }
}

