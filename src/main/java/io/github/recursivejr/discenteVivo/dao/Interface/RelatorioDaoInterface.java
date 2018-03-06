package io.github.recursivejr.discenteVivo.dao.Interface;

import io.github.recursivejr.discenteVivo.models.Relatorio;

public interface RelatorioDaoInterface {

	public Relatorio gerarRelatorioEnquete(int idEnquete);
	public Relatorio gerarRelatorioFormulario(int idFormulario);
}
