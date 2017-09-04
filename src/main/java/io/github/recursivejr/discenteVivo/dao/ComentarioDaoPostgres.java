package io.github.recursivejr.discenteVivo.dao;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Comentario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDaoPostgres implements ComentarioDaoInterface{
    private final Connection conn;

    public ComentarioDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }
    
    @Override
    public boolean adicionar(Comentario comentario) {
    	String sql = "INSERT INTO Comentario (idEnquete, Comentario) VALUES (?,?);";
        
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	
            stmt.setInt(1, comentario.getIdEnquete());
            stmt.setString(2, comentario.getComentario());
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return true;
    }

    @Override
    public boolean remover(Comentario comentario) {
    	String sql = "DELETE FROM Comentario WHERE Comentario ILIKE " + comentario.getComentario()
    	+ " AND idEnquete = " + comentario.getIdEnquete() + ";";
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
    public List<Comentario> listar() {
        List<Comentario> comentarios = new ArrayList<>();
        String sql = "SELECT * FROM Comentario";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Comentario comentario = new Comentario(
                        rs.getInt("ID"),
                        rs.getInt("idEnquete"),
                        rs.getString("Comentario")
                );

                comentarios.add(comentario);
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return comentarios;
    }

    @Override
    public List<Comentario> listarPorEnquete(int IdEnquete) {
        List<Comentario> comentarios = new ArrayList<>();
        String sql = "SELECT * FROM Comentario WHERE idEnquete = " + IdEnquete;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Comentario comentario = new Comentario(
                        rs.getInt("ID"),
                        rs.getInt("idEnquete"),
                        rs.getString("Comentario")
                );

                comentarios.add(comentario);
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return comentarios;
    }
}
