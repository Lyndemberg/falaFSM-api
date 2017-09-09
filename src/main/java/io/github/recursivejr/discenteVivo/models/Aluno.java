package io.github.recursivejr.discenteVivo.models;

public class Aluno extends Usuario {

    private String matricula;
    private String curso;

    public Aluno(String nome, String email, String login, String senha, Endereco endereco,
                 String matricula, String curso) {
        super(nome, email, login, senha, endereco);
        this.matricula = matricula;
        this.curso = curso;
    }

    public Aluno() {

    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Aluno aluno = (Aluno) o;

        if (!matricula.equals(aluno.matricula)) return false;
        return curso.equals(aluno.curso);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + matricula.hashCode();
        result = 31 * result + curso.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula='" + matricula + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }

    @Override
    public boolean isEmpty() {
        try {
            //Se a superClasse retornar vazio ou curso for vazio
            if (super.isEmpty() || getCurso().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException nPE) {
            return true;
        }
    }

}
