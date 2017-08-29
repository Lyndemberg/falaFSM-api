package io.github.recursivejr.discenteVivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Enquete;
import io.github.recursivejr.discenteVivo.models.Resposta;

public class EnqueteDaoPostgres implements EnqueteDaoInterface {
    private final Connection conn;

    public EnqueteDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    @Override
    public boolean adicionar(Enquete enquete) {
        String sql = "INSERT INTO Enquete (NOME, DESCRICAO, FOTO, EMAILADMIN) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, enquete.getNome());
            stmt.setString(2, enquete.getDescricao());
            stmt.setString(3, enquete.getFoto());
            stmt.setString(4, enquete.getEmailAdmin());

            stmt.executeUpdate();

            int IDENQUETE = buscarId(enquete.getNome());
                      
            List<String> opcoes = new ArrayList<>();

            if (enquete.getOpcoes() != null) {
            	opcoes.addAll(enquete.getOpcoes());
                int aux = 0;
                while(aux <= opcoes.size()){

                    sql = "INSERT INTO Opcoes (IDENQUETE, Opcao) VALUES (?,?);";
                    stmt = conn.prepareStatement(sql);

                    stmt.setInt(1,IDENQUETE);
                    stmt.setString(2, opcoes.get(aux));
                    stmt = conn.prepareStatement(sql);
                    stmt.executeUpdate();

                    ++aux;
                }                
            }
            
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean remover(Enquete enquete) {
        String sql = "DELETE FROM Comentario WHERE IDEnquete ILIKE " + enquete.getId() + ";"
                + "DELETE FROM Opcoes WHERE IDEnquete ILIKE " + enquete.getId() + ";"
                + "DELETE FROM RespondeEnquete WHERE IDEnquete ILIKE " + enquete.getId() + ";"
                + "DELETE FROM Enquete WHERE ID ILIKE " + enquete.getId() + ";";
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
    public List<Enquete> listar() {
        List<Enquete> enquetes = new ArrayList<>();
        String sql = "SELECT * FROM Enquete;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                List<String> comentarios = new ArrayList<>();
                List<String> opcoes = new ArrayList<>();
                List<Resposta> respostas = new ArrayList<>();

                Enquete enquete = new Enquete();
                enquete.setId(rs.getInt("id"));
                enquete.setNome(rs.getString("nome"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));
                
                Statement internalStmt = conn.createStatement();

                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete = " + enquete.getId() + ";";
                ResultSet rsListas = internalStmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rsListas.getString("comentario"));
                }
                enquete.setComentarios(comentarios);

                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = internalStmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rsListas.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = internalStmt.executeQuery(sqlRespostas);
                while (rsListas.next()){
                    Resposta resposta = new Resposta();
                    resposta.setResposta(rsListas.getString("Resposta"));
                    resposta.setMatAluno(rsListas.getString("matriculaAluno"));
                }
                enquete.setRespostas(respostas);
             
                enquetes.add(enquete);
                internalStmt.close();
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquetes;
    }

    @Override
    public Enquete buscar(String id) {
        String sql = "SELECT * FROM Enquete WHERE id = " + id + ";";
        Enquete enquete = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                List<String> comentarios = new ArrayList<>();
                List<String> opcoes = new ArrayList<>();
                List<Resposta> respostas = new ArrayList<>();

                enquete = new Enquete();
                enquete.setId(rs.getInt("id"));
                enquete.setNome(rs.getString("nome"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));

                //Percorre todos os comentarios e adiciona cada um ao Arraylist desta enquete
                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete = " + enquete.getId() + ";";
                ResultSet rsListas = stmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rs.getString("comentario"));
                }
                enquete.setOpcoes(comentarios);

                //Percorre todas as Opcoes e adiciona cada uma ao Arraylist desta enquete
                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = stmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rs.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                //Percorre todas as Respostas e adiciona cada uma ao Arraylist desta enquet
                String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = stmt.executeQuery(sqlRespostas);
                while (rsListas.next()){
                    Resposta resp = new Resposta();
                    resp.setResposta(rs.getString("Resposta"));
                    resp.setMatAluno(rs.getString("matriculaAluno"));
                }
                enquete.setRespostas(respostas);
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquete;
    }

    public int buscarId(String nome) {
        String sql = "SELECT * FROM Enquete WHERE nome ILIKE '" + nome + "';";
        int aux = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                aux = rs.getInt("id");
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aux;
    }
    
    public List<Enquete> enquetesPorSetor(String nomeSetor) {
    	List<Enquete> enquetes = new ArrayList<>();
        String sql = "SELECT E.* FROM Enquete E, EnquetesSetor ES " + 
        		"WHERE E.Id = ES.idEnquete AND ES.nomeSetor ILIKE " + nomeSetor +";";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                List<String> comentarios = new ArrayList<>();
                List<String> opcoes = new ArrayList<>();
                List<Resposta> respostas = new ArrayList<>();

                Enquete enquete = new Enquete();
                enquete.setId(rs.getInt("id"));
                enquete.setNome(rs.getString("nome"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));
                
                Statement internalStmt = conn.createStatement();

                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete = " + enquete.getId() + ";";
                ResultSet rsListas = internalStmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rsListas.getString("comentario"));
                }
                enquete.setComentarios(comentarios);

                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = internalStmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rsListas.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = internalStmt.executeQuery(sqlRespostas);
                while (rsListas.next()){
                    Resposta resposta = new Resposta();
                    resposta.setResposta(rsListas.getString("Resposta"));
                    resposta.setMatAluno(rsListas.getString("matriculaAluno"));
                }
                enquete.setRespostas(respostas);
             
                enquetes.add(enquete);
                internalStmt.close();
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquetes;
    }
    
    public List<Enquete> enquetesPorCurso(String nomeCurso) {
    	List<Enquete> enquetes = new ArrayList<>();
        String sql = "SELECT E.* FROM Enquete E, EnquetesCurso EC " + 
        		"WHERE E.Id = EC.idEnquete AND EC.nomeSetor ILIKE " + nomeCurso +";";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                List<String> comentarios = new ArrayList<>();
                List<String> opcoes = new ArrayList<>();
                List<Resposta> respostas = new ArrayList<>();

                Enquete enquete = new Enquete();
                enquete.setId(rs.getInt("id"));
                enquete.setNome(rs.getString("nome"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));
                
                Statement internalStmt = conn.createStatement();

                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete = " + enquete.getId() + ";";
                ResultSet rsListas = internalStmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rsListas.getString("comentario"));
                }
                enquete.setComentarios(comentarios);

                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = internalStmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rsListas.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = internalStmt.executeQuery(sqlRespostas);
                while (rsListas.next()){
                    Resposta resposta = new Resposta();
                    resposta.setResposta(rsListas.getString("Resposta"));
                    resposta.setMatAluno(rsListas.getString("matriculaAluno"));
                }
                enquete.setRespostas(respostas);
             
                enquetes.add(enquete);
                internalStmt.close();
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquetes;
    }
}
