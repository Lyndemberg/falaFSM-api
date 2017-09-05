package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Relatorio;

public interface RelatorioDaoInterface {

	public List<Relatorio> gerarRelatorio();
	public List<Relatorio> gerarRelatorio(String nomeEnquete);
	public List<Relatorio> gerarRelatorio(int idEnquete);
}
