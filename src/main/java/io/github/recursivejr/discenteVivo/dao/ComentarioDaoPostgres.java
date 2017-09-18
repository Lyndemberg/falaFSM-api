package io.github.recursivejr.discenteVivo.dao;

import io.github.recursivejr.discenteVivo.models.Comentario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDaoPostgres extends ElementoDao implements ComentarioDaoInterface{

    public ComentarioDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Comentario comentario) {
    	String sql = "INSERT INTO ComentaEnquete (MatriculaAluno, idEnquete, Comentario) VALUES (?,?,?);";
        
        try {
        	PreparedStatement stmt = getConexao().prepareStatement(sql);

        	stmt.setString(1, comentario.getMatriculaAluno());
            stmt.setInt(2, comentario.getIdEnquete());
            stmt.setString(3, comentario.getComentario());
            stmt.executeUpdate();
            
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Comentario comentario) {
    	String sql = "DELETE FROM ComentaEnquete WHERE MatriculaAluno ILIKE '" + comentario.getMatriculaAluno()
    	+ "' AND idEnquete = '" + comentario.getIdEnquete() + "';";
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
    public List<Comentario> listar() {
        String sql = "SELECT * FROM ComentaEnquete;";

        return getComentarios(sql);
    }

    @Override
    public List<Comentario> listarPorEnquete(int IdEnquete) {
        String sql = "SELECT * FROM ComentaEnquete WHERE idEnquete = '" + IdEnquete + "';";

        return getComentarios(sql);
    }

    private List<Comentario> getComentarios(String sql) {
        List<Comentario> comentarios = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Comentario comentario = new Comentario(
                        rs.getString("MatriculaAluno"),
                        rs.getInt("idEnquete"),
                        rs.getString("Comentario")
                );

                comentarios.add(comentario);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return comentarios;
    }
}
