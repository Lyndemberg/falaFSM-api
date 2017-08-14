package io.github.recursivejr.discenteVivo.models;

public class Sugestao {

    private String assunto;
    private String texto;

    public Sugestao(String assunto, String texto) {
        this.assunto = assunto;
        this.texto = texto;
    }

    public Sugestao() {

    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sugestao sugestao = (Sugestao) o;

        if (!assunto.equals(sugestao.assunto)) return false;
        return texto.equals(sugestao.texto);
    }

    @Override
    public int hashCode() {
        int result = assunto.hashCode();
        result = 31 * result + texto.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Sugestao{" +
                "assunto='" + assunto + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }
}
