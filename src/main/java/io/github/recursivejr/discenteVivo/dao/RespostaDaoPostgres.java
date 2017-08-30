package io.github.recursivejr.discenteVivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Resposta;

public class RespostaDaoPostgres implements RespostaDaoInterface{
    private final Connection conn;

    public RespostaDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }
    
    @Override
    public boolean adicionar(Resposta resposta) {
    	String sql = "INSERT INTO RespondeEnquete (idEnquete, matriculaAluno, Resposta) VALUES (?,?,?);";
        
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	
            stmt.setInt(1, resposta.getIdEnquete());
            stmt.setString(2, resposta.getMatAluno());
            stmt.setString(3, resposta.getResposta());
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return true;
    }

    @Override
    public boolean remover(Resposta resposta) {
    	String sql = "DELETE FROM RespondeEnquete WHERE matriculaAluno ILIKE " + resposta.getMatAluno() 
    	+ " AND idEnquete = " + resposta.getIdEnquete() + ";";
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
    public List<Resposta> listar() {
        List<Resposta> respostas = new ArrayList<>();
        String sql = "SELECT * FROM RespondeEnquete";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Resposta resp = new Resposta();
                resp.setIdEnquete(rs.getInt("idEnquete"));
                resp.setMatAluno(rs.getString("matriculaAluno"));
                resp.setResposta(rs.getString("Resposta"));
                
                respostas.add(resp);
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return respostas;
    }

    @Override
    public Resposta buscar(String matAluno, int IdEnquete) {
        String sql = "SELECT * FROM RespondeEnquete WHERE matriculaAluno ILIKE " + matAluno 
        				+ " AND IdEnquete = " + IdEnquete + ";";
        Resposta resp = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                resp = new Resposta();
                resp.setIdEnquete(rs.getInt("IdEnquete"));
                resp.setMatAluno(rs.getString("matriculaAluno"));
                resp.setResposta(rs.getString("Resposta"));
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resp;
    }

}
