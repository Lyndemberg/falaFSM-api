package io.github.recursivejr.discenteVivo.dao;

import java.util.List;

import io.github.recursivejr.discenteVivo.models.Enquete;
import io.github.recursivejr.discenteVivo.models.Relatorio;

public interface RelatorioDaoInterface {
	
	public List<Relatorio> gerarRelatorio(String nome);
	public List<Relatorio> gerarRelatorio(int id);
}
