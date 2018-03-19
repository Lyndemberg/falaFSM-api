package io.github.recursivejr.discenteVivo.models;

import java.util.List;
import java.util.Objects;

public class RelatorioCampo {

	private String nomeCampo;
	private List<String> opcoes;
	private List<Integer> votos;

	public RelatorioCampo() {

	}

	public RelatorioCampo(String nomeCampo, List<String> opcoes, List<Integer> votos) {

		this.nomeCampo = nomeCampo;
		this.opcoes = opcoes;
		this.votos = votos;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public List<String> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<String> opcoes) {
		this.opcoes = opcoes;
	}

	public List<Integer> getVotos() {
		return votos;
	}

	public void setVotos(List<Integer> votos) {
		this.votos = votos;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RelatorioCampo that = (RelatorioCampo) o;
		return Objects.equals(getNomeCampo(), that.getNomeCampo()) &&
				Objects.equals(getOpcoes(), that.getOpcoes()) &&
				Objects.equals(getVotos(), that.getVotos());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getNomeCampo(), getOpcoes(), getVotos());
	}

	@Override
	public String toString() {

		return "RelatorioCampo{" +
				"nomeCampo='" + nomeCampo + '\'' +
				", opcoes=" + opcoes +
				", votos=" + votos +
				'}';
	}
}
