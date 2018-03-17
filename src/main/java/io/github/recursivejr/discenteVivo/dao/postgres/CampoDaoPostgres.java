package io.github.recursivejr.discenteVivo.dao.postgres;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.CampoDaoInterface;
import io.github.recursivejr.discenteVivo.factories.Fabrica;
import io.github.recursivejr.discenteVivo.models.Campo;
import io.github.recursivejr.discenteVivo.models.Opcao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampoDaoPostgres extends ElementoDao implements CampoDaoInterface {

    public CampoDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public Integer adicionar(Campo campo) {
        Integer idCampo = null;

        String sql = "INSERT INTO Campo (Nome, Descricao, IdFormulario) VALUES (?,?,?) RETURNING idCampo;";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, campo.getNome());
            stmt.setString(2, campo.getDescricao());
            stmt.setInt(3, campo.getIdFormulario());

            //Recupera o valor do Id deste Campo no BD para ser usado nas proximas Querys
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                idCampo = rs.getInt("IdCampo");

            //Verifica se Ha Opcoes que devem ser Salvas neste Campo
                //Se nao for null entao ha Opcoes que devem ser referenciadas
            if (campo.getOpcoes() != null) {

                //Cria um OpcaoCampoDao para salvar as Opcoes
                OpcaoCampoDaoPostgres opcaoDao = Fabrica.criarFabricaDaoPostgres().criarOpcaoCampoDao();

                //Percorre todos as Opcoes salvando elas no BD
                for (Opcao opcao : campo.getOpcoes()) {
                    opcao.setIdFK(idCampo);
                    opcaoDao.adicionar(opcao);
                }
            }

            stmt.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return idCampo;
    }

    @Override
    public boolean remover(int idCampo) {

        String sql = "DELETE FROM RespondeCampo WHERE IdCampo = ?;"
                + "DELETE FROM OpcoesCampo WHERE IdCampo = ?;"
                + "DELETE FROM Campo WHERE IdCampo = ?;";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, idCampo);
            stmt.setInt(2, idCampo);
            stmt.setInt(3, idCampo);

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Campo campo) {
        //Atualiza Apenas os Dados do Campo
        String sql = "UPDATE Campo SET Nome = ?, Descricao = ? WHERE IdCampo = ?;";

        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setString(1, campo.getNome());
            stmt.setString(2, campo.getDescricao());
            stmt.setInt(3, campo.getId());

            stmt.executeUpdate(sql);

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Campo> listar() {
        //Retorna todas os Campos Salvos

        String sql = "SELECT idCampo, Nome, Descricao FROM Campo;";
        return getAllCampos(sql);
    }

    @Override
    public List<Campo> listarPorFormulario(int idFormulario, String matAluno) {
        //Retorna apenas os Campos que nao tem nenhum curso pois sao para toda a universidade
        //e os Formularios do seu curso especifico

        String sql = String.format("SELECT idCampo, Nome, Descricao From Campo " +
                "WHERE IdFormulario = %d;", idFormulario);

        if (matAluno == null)
            return getAllCampos(sql);
        else
            return getCampos(sql, matAluno);
    }

    @Override
    public Campo buscar(int idCampo) {

        String sql = String.format("SELECT idCampo, Nome, Descricao FROM Campo WHERE IdCampo = '%d';", idCampo);

        return getAllCampos(sql).get(0);
    }

    private List<Campo> getCampos(String sql, String matAluno){
        List<Campo> campos = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Campo campo = new Campo();
                campo.setId(rs.getInt("idCampo"));
                campo.setNome(rs.getString("nome"));
                campo.setDescricao(rs.getString("descricao"));

                try {
                    campo.setOpcoes(
                            new OpcaoCampoDaoPostgres().listarPorChave(campo.getId())
                    );

                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    campo.setOpcoes(new ArrayList<>());
                }

                //Retorna Apenas a Resposta do Aluno
                try {
                    campo.setRespostas(new ArrayList<>());
                    campo.getRespostas().add(
                            new RespostaCampoDaoPostgres().buscar(matAluno, rs.getInt("IdCampo"))
                    );

                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                campos.add(campo);
            }

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return campos;
    }

    private List<Campo> getAllCampos(String sql) {
        List<Campo> campos = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Campo campo = new Campo();
                campo.setId(rs.getInt("idCampo"));
                campo.setNome(rs.getString("nome"));
                campo.setDescricao(rs.getString("descricao"));

                try {
                    campo.setOpcoes(
                            new OpcaoCampoDaoPostgres().listarPorChave(campo.getId())
                    );

                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    campo.setOpcoes(new ArrayList<>());
                }

                //Nao Acho Necessario Listar as Respostas
                campo.setRespostas(new ArrayList<>());

                campos.add(campo);
            }

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return campos;
    }
}
