package io.github.recursivejr.discenteVivo.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.OpcaoDaoInterface;
import io.github.recursivejr.discenteVivo.models.Opcao;

public class OpcaoDaoPostgres extends ElementoDao implements OpcaoDaoInterface {

    public OpcaoDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Opcao opcao) {
    	String sql = "INSERT INTO Opcoes (Opcao) VALUES (?) WHERE IdEnquete = ?;";
        
        try {
        	PreparedStatement stmt = getConexao().prepareStatement(sql);
        	
            stmt.setString(1, opcao.getOpcao());
            stmt.setInt(2, opcao.getIdFK());
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
    	String sql = "DELETE FROM Opcoes WHERE Opcao ILIKE '" + opcao.getOpcao()
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
        String sql = "SELECT * FROM Opcoes";

        return getOpcoes(sql);
    }

    @Override
    public List<Opcao> listarPorChave(int idEnquete) {
        String sql = "SELECT * FROM Opcoes WHERE idEnquete = '" + idEnquete + "';";

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
