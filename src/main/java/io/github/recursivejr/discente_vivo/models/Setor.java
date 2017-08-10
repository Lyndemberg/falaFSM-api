package io.github.recursivejr.discente_vivo.models;

import java.util.List;

public class Setor {

    private String nome;
    private List<Sugestao> sugestoes;
    private List<Enquete> enquetes;

    public Setor(String nome, List<Sugestao> sugestoes, List<Enquete> enquetes) {
        this.nome = nome;
        this.sugestoes = sugestoes;
        this.enquetes = enquetes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Sugestao> getSugestoes() {
        return sugestoes;
    }

    public void setSugestoes(List<Sugestao> sugestoes) {
        this.sugestoes = sugestoes;
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
        if (o == null || getClass() != o.getClass()) return false;

        Setor setor = (Setor) o;

        if (!nome.equals(setor.nome)) return false;
        if (!sugestoes.equals(setor.sugestoes)) return false;
        return enquetes.equals(setor.enquetes);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + sugestoes.hashCode();
        result = 31 * result + enquetes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Setor{" +
                "nome='" + nome + '\'' +
                ", sugestoes=" + sugestoes +
                ", enquetes=" + enquetes +
                '}';
    }
}
