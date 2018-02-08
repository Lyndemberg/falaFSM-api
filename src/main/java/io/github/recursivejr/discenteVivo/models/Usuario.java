package io.github.recursivejr.discenteVivo.models;

import java.util.Objects;

public abstract class Usuario {

    private String nome;
    private String email;
    private String login;
    private String senha;

    public Usuario(String nome, String email, String login, String senha) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
    }

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nome, usuario.nome) &&
                Objects.equals(email, usuario.email) &&
                Objects.equals(login, usuario.login) &&
                Objects.equals(senha, usuario.senha);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + senha.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    //Metodo que verifica se o Usuario esta vazio, baseado no String.isEmpty()
    public boolean isEmpty() {
        try {
            //Se alguma coisa estiver vazia retorna true
            if (getNome().isEmpty() || getEmail().isEmpty() || getLogin().isEmpty()
                    || getSenha().isEmpty()) {
                return true;
            } else {
                //Se tudo estiver preenchido entao retorna false
                return false;
            }
            //Se algum atributo estiver como null dispara NullPointException logo esta vazio retornando true
        } catch (NullPointerException nPE) {
            return true;
        }
    }
}
