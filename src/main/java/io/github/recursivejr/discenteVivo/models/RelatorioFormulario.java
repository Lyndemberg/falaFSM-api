package io.github.recursivejr.discenteVivo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelatorioFormulario {

	private List<RelatorioCampo> relatorios;
	private List<Comentario> comentarios;

	{
		relatorios = new ArrayList<>();
		comentarios = new ArrayList<>();
	}

	public RelatorioFormulario() {

	}

	public RelatorioFormulario(List<RelatorioCampo> relatorios, List<Comentario> comentarios) {
		this.relatorios = relatorios;
		this.comentarios = comentarios;
	}

	public List<RelatorioCampo> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<RelatorioCampo> relatorios) {
		this.relatorios = relatorios;
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
		RelatorioFormulario that = (RelatorioFormulario) o;
		return Objects.equals(getRelatorios(), that.getRelatorios()) &&
				Objects.equals(getComentarios(), that.getComentarios());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getRelatorios(), getComentarios());
	}

	@Override
	public String toString() {

		return "RelatorioFormulario{" +
				"relatorios=" + relatorios +
				", comentarios=" + comentarios +
				'}';
	}
}
