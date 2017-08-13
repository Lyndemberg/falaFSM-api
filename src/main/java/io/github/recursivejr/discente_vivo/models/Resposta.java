package io.github.recursivejr.discente_vivo.models;

public class Resposta {

    private int idResposta;
    private String resposta;
    private int alunoId;

    public Resposta(int idResposta, String resposta, int alunoId) {
        this.idResposta = idResposta
        this.resposta = resposta;
        this.alunoId = alunoId;
    }

    public Resposta() {

    }

    public int getIdResposta(){
        return idResposta;
    }

    public void setIdResposta(){
        this.idResposta = idResposta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public int setAlunoId() {
        return aluno;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resposta resposta1 = (Resposta) o;

        if (!resposta.equals(resposta1.resposta)) return false;
        return alunoId.equals(resposta1.alunoId);
    }

    @Override
    public int hashCode() {
        int result = resposta.hashCode();
        result = 31 * result + alunoId.hashCode();
        result = 31 * result + idResposta.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resposta{" +
                "resposta='" + resposta + '\'' +
                ", alunoId=" + alunoId +
                ", idResposta=" + idResposta +
                '}';
    }
}
