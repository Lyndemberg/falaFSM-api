package io.github.recursivejr.falaFSM.models;

import io.github.recursivejr.falaFSM.models.abstrato.Usuario;

public class Administrador extends Usuario {

    public Administrador(String nome, String email, String login, String senha) {
        super(nome, email, login, senha);
    }

    public Administrador() {
    }
}
