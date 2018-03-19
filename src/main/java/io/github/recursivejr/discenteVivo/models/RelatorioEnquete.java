package io.github.recursivejr.discenteVivo.models;

import java.util.List;

public class RelatorioEnquete {

	private List<String> opcao;
	private List<Integer> votos;
	private List<Comentario> comentarios;

	public RelatorioEnquete(List<String> opcao, List<Integer> votos, List<Comentario> comentarios) {
		this.opcao = opcao;
		this.votos = votos;
		this.comentarios = comentarios;
	}

	public RelatorioEnquete() {

	}

	public List<String> getOpcao() {
		return opcao;
	}

	public void setOpcao(List<String> opcao) {
		this.opcao = opcao;
	}

	public List<Integer> getVotos() {
		return votos;
	}

	public void setVotos(List<Integer> votos) {
		this.votos = votos;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RelatorioEnquete relatorio = (RelatorioEnquete) o;

		if (!opcao.equals(relatorio.opcao)) return false;
		if (!votos.equals(relatorio.votos)) return false;
		return comentarios.equals(relatorio.comentarios);
	}

	@Override
	public int hashCode() {
		int result = opcao.hashCode();
		result = 31 * result + votos.hashCode();
		result = 31 * result + comentarios.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "RelatorioEnquete{" +
				"opcao=" + opcao +
				", votos=" + votos +
				", comentarios=" + comentarios +
				'}';
	}
}
