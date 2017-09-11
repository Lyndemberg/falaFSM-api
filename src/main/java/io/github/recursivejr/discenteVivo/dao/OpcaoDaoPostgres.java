package io.github.recursivejr.discenteVivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Opcao;

public class OpcaoDaoPostgres implements OpcaoDaoInterface{
    private final Connection conn;

    public OpcaoDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }
    
    @Override
    public boolean adicionar(int idEnquete, Opcao opcao) {
    	String sql = "INSERT INTO Opcoes (Opcao) VALUES (?) WHERE IdEnquete = ?;";
        
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	
            stmt.setString(1, opcao.getOpcao());
            stmt.setInt(2, idEnquete);
            stmt.executeUpdate();
            
//            stmt.close();
//            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return true;
    }

    @Override
    public boolean remover(Opcao opcao) {
    	String sql = "DELETE FROM Opcoes WHERE Opcao ILIKE '" + opcao.getOpcao()
    	+ "' AND idEnquete = '" + opcao.getIdEnquete() + "';";
    	try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
                ex.printStackTrace(); 
        }
        return true;
    }

    @Override
    public List<Opcao> listar() {
        String sql = "SELECT * FROM Opcoes";

        return getOpcoes(sql);
    }

    @Override
    public List<Opcao> listarPorEnquete(int idEnquete) {
        String sql = "SELECT * FROM Opcoes WHERE idEnquete = '" + idEnquete + "';";

        return getOpcoes(sql);
    }

    private List<Opcao> getOpcoes(String sql) {
        List<Opcao> opcoes = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Opcao opcao = new Opcao();
                opcao.setIdEnquete(rs.getInt("idEnquete"));
                opcao.setOpcao(rs.getString("Opcao"));

                opcoes.add(opcao);
            }
//            stmt.close();
//            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return opcoes;
    }
}
