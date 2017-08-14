package io.github.recursivejr.discenteVivo.models;

public class Administrador extends Usuario {

    public Administrador(String nome, String email, String login, String senha, Endereco endereco) {
        super(nome, email, login, senha, endereco);
    }

    public Administrador() {
    }
}
