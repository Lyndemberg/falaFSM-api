package io.github.recursivejr.discenteVivo.dao.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.RelatorioDaoInterface;
import io.github.recursivejr.discenteVivo.models.Comentario;
import io.github.recursivejr.discenteVivo.models.Relatorio;

public class RelatorioDaoPostgres extends ElementoDao implements RelatorioDaoInterface {

    public RelatorioDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public Relatorio gerarRelatorio(int idEnquete) {

		String sql = String.format("SELECT resp.idEnquete AS idEnquete, resp.resposta AS Resposta," +
				" count(*) AS QuantidadeVotos FROM RespondeEnquete resp" +
				" WHERE resp.idEnquete = '%d' GROUP BY resp.idEnquete, resp.resposta;", idEnquete);

		Relatorio relatorio = null;

		List<String> opcoes = new ArrayList<>();
		List<Integer> votos = new ArrayList<>();
		List<Comentario> comentarios = new ArrayList<>();

		try {
			Statement stmt = getConexao().createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				opcoes.add(rs.getString("Resposta"));
				votos.add(rs.getInt("QuantidadeVotos"));
				comentarios = new ComentarioEnqueteDaoPostgres().listarPorChave(rs.getInt("idEnquete"));
			}

			relatorio = new Relatorio(
					opcoes,
					votos,
					comentarios
			);

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(ex.getMessage());
		}
		return relatorio;
    }

}
