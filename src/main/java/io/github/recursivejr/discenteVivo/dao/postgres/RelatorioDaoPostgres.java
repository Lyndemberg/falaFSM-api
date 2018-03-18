package io.github.recursivejr.discenteVivo.dao.postgres;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.RelatorioDaoInterface;
import io.github.recursivejr.discenteVivo.models.Comentario;
import io.github.recursivejr.discenteVivo.models.RelatorioCampo;
import io.github.recursivejr.discenteVivo.models.RelatorioEnquete;
import io.github.recursivejr.discenteVivo.models.RelatorioFormulario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RelatorioDaoPostgres extends ElementoDao implements RelatorioDaoInterface {

    public RelatorioDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public RelatorioEnquete gerarRelatorioEnquete(int idEnquete) {

		String sql = String.format("SELECT resp.idEnquete AS idEnquete, resp.resposta AS Resposta," +
				" count(*) AS QuantidadeVotos FROM RespondeEnquete resp" +
				" WHERE resp.idEnquete = '%d' GROUP BY resp.idEnquete, resp.resposta;", idEnquete);

		RelatorioEnquete relatorio = null;

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

			relatorio = new RelatorioEnquete(
					opcoes,
					votos,
					comentarios
			);

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(ex.getMessage());
			return null;
		}

		return relatorio;
    }

	@Override
	public RelatorioFormulario gerarRelatorioFormulario(int idFormulario) {

		RelatorioFormulario relatorioFormulario = new RelatorioFormulario();

		String sql = String.format("SELECT Nome, IdCampo FROM Campo WHERE IdFormulario = ?;");
    	List<RelatorioCampo> relatorios = new ArrayList<>();

		try {
			PreparedStatement stmt = getConexao().prepareStatement(sql);
			stmt.setInt(1, idFormulario);

			ResultSet rs = stmt.executeQuery();

			relatorioFormulario.setComentarios(
					new ComentarioFormularioDaoPostgres().listarPorChave(rs.getInt("idFormulario"))
			);

			while (rs.next()) {

				List<String> opcoes = new ArrayList<>();
				List<Integer> votos = new ArrayList<>();

				String internalSQL = "SELECT RC.resposta, count(*) as QuantidadeVotos" +
						"FROM OpcoesCampo AS OP JOIN RespondeCampo AS RC ON (OP.idcampo = RC.idcampo " +
						"AND OP.Opcao ILIKE RC.Resposta) WHERE op.idcampo = ? GROUP BY RC.resposta;";

				PreparedStatement internalSTMT = getConexao().prepareStatement(sql);
				internalSTMT.setInt(1, rs.getInt("IdCampo"));

				ResultSet internalRS = internalSTMT.executeQuery();

				while (internalRS.next()) {
					opcoes.add(internalRS.getString("Resposta"));
					votos.add(internalRS.getInt("QuantidadeVotos"));
				}

				RelatorioCampo relatorioCampo = new RelatorioCampo(
						rs.getString("Nome"),
						opcoes,
						votos
				);

				relatorios.add(relatorioCampo);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(ex.getMessage());
			return null;
		}

		relatorioFormulario.setRelatorios(relatorios);
		return relatorioFormulario;
	}
}

