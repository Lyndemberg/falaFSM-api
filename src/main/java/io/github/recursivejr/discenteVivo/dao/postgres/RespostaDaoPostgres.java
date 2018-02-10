package io.github.recursivejr.discenteVivo.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.RespostaDaoInterface;
import io.github.recursivejr.discenteVivo.models.Resposta;

public class RespostaDaoPostgres extends ElementoDao implements RespostaDaoInterface {

    public RespostaDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Resposta resposta) {
    	String sql = "INSERT INTO RespondeEnquete (idEnquete, matriculaAluno, Resposta) VALUES (?,?,?);";
        
        try {
        	PreparedStatement stmt = getConexao().prepareStatement(sql);
        	
            stmt.setInt(1, resposta.getIdEnquete());
            stmt.setString(2, resposta.getMatAluno());
            stmt.setString(3, resposta.getResposta());
            stmt.executeUpdate();
            
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Resposta resposta) {
    	String sql = "DELETE FROM RespondeEnquete WHERE matriculaAluno ILIKE " + resposta.getMatAluno() 
    	+ " AND idEnquete = " + resposta.getIdEnquete() + ";";
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
    public List<Resposta> listar() {
        List<Resposta> respostas = new ArrayList<>();
        String sql = "SELECT * FROM RespondeEnquete";
        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Resposta resp = new Resposta();
                resp.setIdEnquete(rs.getInt("idEnquete"));
                resp.setMatAluno(rs.getString("matriculaAluno"));
                resp.setResposta(rs.getString("Resposta"));
                
                respostas.add(resp);
            }
            stmt.close();
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
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                resp = new Resposta();
                resp.setIdEnquete(rs.getInt("IdEnquete"));
                resp.setMatAluno(rs.getString("matriculaAluno"));
                resp.setResposta(rs.getString("Resposta"));
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resp;
    }

}
