package io.github.recursivejr.falaFSM.dao.postgres;

import io.github.recursivejr.falaFSM.dao.ElementoDao;
import io.github.recursivejr.falaFSM.dao.Interface.RelatorioDaoInterface;
import io.github.recursivejr.falaFSM.models.Comentario;
import io.github.recursivejr.falaFSM.models.RelatorioCampo;
import io.github.recursivejr.falaFSM.models.RelatorioEnquete;
import io.github.recursivejr.falaFSM.models.RelatorioFormulario;

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
			relatorioFormulario.setComentarios(
					new ComentarioFormularioDaoPostgres().listarPorChave(idFormulario)
			);

			PreparedStatement stmt = getConexao().prepareStatement(sql);
			stmt.setInt(1, idFormulario);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				List<String> opcoes = new ArrayList<>();
				List<Integer> votos = new ArrayList<>();

				String internalSQL = "SELECT RC.resposta, count(*) as QuantidadeVotos " +
						"FROM OpcoesCampo AS OP JOIN RespondeCampo AS RC ON (OP.idcampo = RC.idcampo " +
						"AND OP.Opcao ILIKE RC.Resposta) WHERE op.idcampo = ? GROUP BY RC.resposta;";

				PreparedStatement internalSTMT = getConexao().prepareStatement(internalSQL);
				internalSTMT.setInt(1, rs.getInt("IdCampo"));

				ResultSet internalRS = internalSTMT.executeQuery();

				while (internalRS.next()) {
					opcoes.add(internalRS.getString("Resposta"));
					votos.add(internalRS.getInt("QuantidadeVotos"));
				}

				internalSQL = "SELECT OP.Opcao AS Resposta FROM OpcoesCampo AS OP WHERE OP.idcampo = ? " +
						"AND OP.Opcao NOT IN (SELECT RC.Resposta FROM RespondeCampo AS RC " +
						"WHERE RC.idcampo = ? GROUP BY RC.Resposta);";

				internalSTMT = getConexao().prepareStatement(internalSQL);
				internalSTMT.setInt(1, rs.getInt("IdCampo"));
				internalSTMT.setInt(2, rs.getInt("IdCampo"));

				internalRS = internalSTMT.executeQuery();

				while (internalRS.next()) {
					opcoes.add(internalRS.getString("Resposta"));
					votos.add(0);
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

