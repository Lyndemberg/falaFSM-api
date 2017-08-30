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
    public boolean adicionar(Opcao opcao) {
    	String sql = "INSERT INTO Opcao (idEnquete, Opcao) VALUES (?,?);";
        
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	
            stmt.setInt(1, opcao.getIdEnquete());
            stmt.setString(2, opcao.getOpcao());
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return true;
    }

    @Override
    public boolean remover(Opcao opcao) {
    	String sql = "DELETE FROM Opcao WHERE Opcao ILIKE " + opcao.getOpcao() 
    	+ " AND idEnquete = " + opcao.getIdEnquete() + ";";
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
        List<Opcao> opcoes = new ArrayList<>();
        String sql = "SELECT * FROM Opcao";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
            	Opcao opcao = new Opcao();
            	opcao.setIdEnquete(rs.getInt("idEnquete"));
            	opcao.setOpcao(rs.getString("Opcao"));
                
                opcoes.add(opcao);
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return opcoes;
    }
}
