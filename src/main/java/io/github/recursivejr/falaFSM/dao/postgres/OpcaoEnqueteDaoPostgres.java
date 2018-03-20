package io.github.recursivejr.falaFSM.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.falaFSM.dao.ElementoDao;
import io.github.recursivejr.falaFSM.dao.Interface.OpcaoDaoInterface;
import io.github.recursivejr.falaFSM.models.Opcao;

public class OpcaoEnqueteDaoPostgres extends ElementoDao implements OpcaoDaoInterface {

    public OpcaoEnqueteDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Opcao opcao) {
    	String sql = "INSERT INTO OpcoesEnquete (idEnquete, Opcao) VALUES (?,?);";
        
        try {
        	PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setInt(1, opcao.getIdFK());
            stmt.setString(2, opcao.getOpcao());
            stmt.executeUpdate();
            
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Opcao opcao) {
    	String sql = "DELETE FROM OpcoesEnquete WHERE Opcao ILIKE '" + opcao.getOpcao()
    	+ "' AND idEnquete = '" + opcao.getIdFK() + "';";
    	try {
            Statement stmt = getConexao().createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
        } catch (SQLException ex) {
    	    ex.printStackTrace();
    	    return false;
        }
        return true;
    }

    @Override
    public List<Opcao> listar() {
        String sql = "SELECT * FROM OpcoesEnquete";

        return getOpcoes(sql);
    }

    @Override
    public List<Opcao> listarPorChave(int idEnquete) {
        String sql = "SELECT * FROM OpcoesEnquete WHERE idEnquete = '" + idEnquete + "';";

        return getOpcoes(sql);
    }

    private List<Opcao> getOpcoes(String sql) {
        List<Opcao> opcoes = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Opcao opcao = new Opcao();
                opcao.setIdFK(rs.getInt("idEnquete"));
                opcao.setOpcao(rs.getString("Opcao"));

                opcoes.add(opcao);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return opcoes;
    }
}
