package io.github.recursivejr.falaFSM.dao.postgres;

import io.github.recursivejr.falaFSM.dao.ElementoDao;
import io.github.recursivejr.falaFSM.dao.Interface.RespostaDaoInterface;
import io.github.recursivejr.falaFSM.models.Resposta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RespostaCampoDaoPostgres extends ElementoDao implements RespostaDaoInterface {

    public RespostaCampoDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Resposta resposta) {
    	String sql = "INSERT INTO RespondeCampo (idCampo, matriculaAluno, Resposta) VALUES (?,?,?);";
        
        try {
        	PreparedStatement stmt = getConexao().prepareStatement(sql);
        	
            stmt.setInt(1, resposta.getIdFK());
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
    	String sql = "DELETE FROM RespondeCampo WHERE matriculaAluno ILIKE ? AND idCampo = ?;";
    	try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setString(1, resposta.getMatAluno());
            stmt.setInt(2, resposta.getIdFK());

            stmt.executeUpdate();

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
        String sql = "SELECT * FROM RespondeCampo";
        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Resposta resp = new Resposta();

                resp.setIdFK(rs.getInt("idCampo"));
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
    public Resposta buscar(String matAluno, int idFK) {
        String sql = "SELECT * FROM RespondeCampo WHERE matriculaAluno ILIKE ? AND IdCampo = ?;";
        Resposta resp = null;

        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setString(1, matAluno);
            stmt.setInt(2, idFK);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                resp = new Resposta();

                resp.setIdFK(rs.getInt("idCampo"));
                resp.setMatAluno(rs.getString("matriculaAluno"));
                resp.setResposta(rs.getString("Resposta"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resp;
    }

}
