package io.github.recursivejr.falaFSM.dao.postgres;

import io.github.recursivejr.falaFSM.dao.ElementoDao;
import io.github.recursivejr.falaFSM.dao.Interface.ComentarioDaoInterface;
import io.github.recursivejr.falaFSM.models.Comentario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioEnqueteDaoPostgres extends ElementoDao implements ComentarioDaoInterface {

    public ComentarioEnqueteDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Comentario comentario) {
    	String sql = "INSERT INTO ComentaEnquete (MatriculaAluno, idEnquete, Comentario) VALUES (?,?,?);";
        
        try {
        	PreparedStatement stmt = getConexao().prepareStatement(sql);

        	stmt.setString(1, comentario.getMatriculaAluno());
            stmt.setInt(2, comentario.getIdFK());
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
    	String sql = "DELETE FROM ComentaEnquete WHERE MatriculaAluno ILIKE '?' AND idEnquete = ?;";
    	try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setString(1, comentario.getMatriculaAluno());
            stmt.setInt(2, comentario.getIdFK());

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
    public List<Comentario> listarPorChave(int IdEnquete) {
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
                        rs.getInt("idEnquete"),
                        rs.getString("MatriculaAluno"),
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
