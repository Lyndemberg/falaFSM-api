package io.github.recursivejr.discente_vivo.models;

import java.util.List;

public class Aluno extends Usuario {

    private String matricula;
    private List<Sugestao> sugestoes;

    public Aluno(String nome, String email, String login, String senha, Endereco endereco, String matricula, List<Sugestao> sugestoes) {
        super(nome, email, login, senha, endereco);
        this.matricula = matricula;
        this.sugestoes = sugestoes;
    }

    public Aluno() {

    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Sugestao> getSugestoes() {
        return sugestoes;
    }

    public void setSugestoes(List<Sugestao> sugestoes) {
        this.sugestoes = sugestoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Aluno aluno = (Aluno) o;

        if (!matricula.equals(aluno.matricula)) return false;
        return sugestoes.equals(aluno.sugestoes);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + matricula.hashCode();
        result = 31 * result + sugestoes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula='" + matricula + '\'' +
                ", sugestoes=" + sugestoes +
                '}';
    }
}
