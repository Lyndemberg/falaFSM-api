package io.github.recursivejr.discenteVivo.models;

public abstract class Usuario {

    private String nome;
    private String email;
    private String login;
    private String senha;
    private Endereco endereco;

    public Usuario(String nome, String email, String login, String senha, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (!nome.equals(usuario.nome)) return false;
        if (!email.equals(usuario.email)) return false;
        if (!login.equals(usuario.login)) return false;
        if (!senha.equals(usuario.senha)) return false;
        return endereco.equals(usuario.endereco);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + senha.hashCode();
        result = 31 * result + endereco.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", endereco=" + endereco +
                '}';
    }
}
