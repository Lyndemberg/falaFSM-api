package io.github.recursivejr.falaFSM.dao.Interface;

import io.github.recursivejr.falaFSM.models.RelatorioEnquete;
import io.github.recursivejr.falaFSM.models.RelatorioFormulario;

public interface RelatorioDaoInterface {

	public RelatorioEnquete gerarRelatorioEnquete(int idEnquete);
	public RelatorioFormulario gerarRelatorioFormulario(int idFormulario);
}
