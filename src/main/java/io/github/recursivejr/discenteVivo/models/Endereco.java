package io.github.recursivejr.discenteVivo.models;

public class Endereco {

    private String cidade;
    private String rua;
    private String numero;

    public Endereco(String cidade, String rua, String numero) {
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
    }

    public Endereco() {

    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    //Metodo que verifica se o endereco esta vazio, Baseado no String.isEmpty()
    public boolean isEmpty() {
        try {
            //Se alguma coisa estiver fazio entao retorna true
            if (getCidade().isEmpty() || getNumero().isEmpty() || getRua().isEmpty())
                return true;
            else
                //Se tudo estiver preenchido entao retorna false
                return false;
        //Se algum atributo estiver como null dispara NullPointException logo esta vazio retornando true
        } catch (NullPointerException nPE) {
            return true;
        }
    }
}
