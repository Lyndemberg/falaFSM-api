package io.github.recursivejr.discenteVivo.dao.Interface;

import io.github.recursivejr.discenteVivo.models.RelatorioEnquete;
import io.github.recursivejr.discenteVivo.models.RelatorioFormulario;

public interface RelatorioDaoInterface {

	public RelatorioEnquete gerarRelatorioEnquete(int idEnquete);
	public RelatorioFormulario gerarRelatorioFormulario(int idFormulario);
}
