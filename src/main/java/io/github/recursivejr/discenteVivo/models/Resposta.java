package io.github.recursivejr.discenteVivo.models;

import java.util.Objects;

public class Resposta {

	//idFK = id Foreing key, ou seja, o id da Tabela na qual se faz chave estrangeira
    private int idFK;
    private String resposta;
    private String matAluno;

	public Resposta() {
		
	}

	public Resposta(int idFK, String resposta, String matAluno) {
		this.idFK = idFK;
		this.resposta = resposta;
		this.matAluno = matAluno;
	}

	public int getIdFK() {
		return idFK;
	}

	public void setIdFK(int idFK) {
		this.idFK = idFK;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getMatAluno() {
		return matAluno;
	}

	public void setMatAluno(String matAluno) {
		this.matAluno = matAluno;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Resposta resposta1 = (Resposta) o;
		return getIdFK() == resposta1.getIdFK() &&
				Objects.equals(getResposta(), resposta1.getResposta()) &&
				Objects.equals(getMatAluno(), resposta1.getMatAluno());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getIdFK(), getResposta(), getMatAluno());
	}

	@Override
	public String toString() {
		return "Resposta{" +
				"idFK=" + idFK +
				", resposta='" + resposta + '\'' +
				", matAluno='" + matAluno + '\'' +
				'}';
	}
}
