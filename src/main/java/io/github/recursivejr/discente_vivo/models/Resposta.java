package io.github.recursivejr.discente_vivo.models;

public class Resposta {

    private String resposta;
    private Aluno aluno;

    public Resposta(String resposta, Aluno aluno) {
        this.resposta = resposta;
        this.aluno = aluno;
    }

    public Resposta() {

    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resposta resposta1 = (Resposta) o;

        if (!resposta.equals(resposta1.resposta)) return false;
        return aluno.equals(resposta1.aluno);
    }

    @Override
    public int hashCode() {
        int result = resposta.hashCode();
        result = 31 * result + aluno.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resposta{" +
                "resposta='" + resposta + '\'' +
                ", aluno=" + aluno +
                '}';
    }
}
