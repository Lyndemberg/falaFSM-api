package io.github.recursivejr.falaFSM.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Opcao {

	//idFK = id Foreing key, ou seja, o id da Tabela na qual se faz chave estrangeira
	@JsonIgnore
	private int idFK;
	private String opcao;

	public Opcao() {

	}

	public Opcao(int idFK, String opcao) {
		this.idFK = idFK;
		this.opcao = opcao;
	}

	public int getIdFK() {
		return idFK;
	}

	public void setIdFK(int idFK) {
		this.idFK = idFK;
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Opcao opcao1 = (Opcao) o;
		return getIdFK() == opcao1.getIdFK() &&
				Objects.equals(getOpcao(), opcao1.getOpcao());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getIdFK(), getOpcao());
	}

	@Override
	public String toString() {
		return "Opcao{" +
				"idFK=" + idFK +
				", opcao='" + opcao + '\'' +
				'}';
	}
}
