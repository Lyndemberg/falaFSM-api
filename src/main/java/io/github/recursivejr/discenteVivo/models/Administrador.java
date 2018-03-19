package io.github.recursivejr.discenteVivo.models;

import io.github.recursivejr.discenteVivo.models.abstrato.Usuario;

public class Administrador extends Usuario {

    public Administrador(String nome, String email, String login, String senha) {
        super(nome, email, login, senha);
    }

    public Administrador() {
    }
}
