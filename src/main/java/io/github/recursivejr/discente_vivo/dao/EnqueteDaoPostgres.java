package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.factories.Conexao;
import io.github.recursivejr.discente_vivo.models.Enquete;
import io.github.recursivejr.discente_vivo.models.Resposta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnqueteDaoPostgres implements EnqueteDaoInterface{
    private final Connection conn;

    public EnqueteDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    @Override
    public boolean adicionar(Enquete enquete) {
        String sql = "INSERT INTO Enquete (ID, NOME, DESCRICAO, FOTO, EMAILADMIN) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs;
            stmt.setString(2, enquete.getNome());
            stmt.setString(3, enquete.getDescricao());
            stmt.setString(4, enquete.getFoto());
            stmt.setString(5, enquete.getEmailAdmin());

            stmt.executeUpdate();

            int IDENQUETE = buscarId(enquete.getNome());

            List<String> comentarios = new ArrayList();            
            List<String> opcoes = new ArrayList();
            List<Resposta> respostas = new ArrayList();

            comentarios.addAll(enquete.getComentarios());
            if (comentarios.size() > 0) {
                int aux = 0;
                while(aux <= comentarios.size()){

                    sql = "INSERT INTO Comentarios (ID, IDENQUETE, COMENTARIO) VALUES (?,?,?);";
                    stmt = conn.prepareStatement(sql);

                    stmt.setString(1, null);
                    stmt.setInt(2,IDENQUETE);
                    stmt.setString(3, comentarios.get(aux));
                    stmt = conn.prepareStatement(sql);
                    stmt.executeUpdate();

                    ++aux;
                }                
            }

            opcoes.addAll(enquete.getOpcoes());
            if (opcoes.size() > 0) {
                int aux = 0;
                while(aux <= opcoes.size()){

                    sql = "INSERT INTO Opcoes (ID, IDENQUETE, Opcao) VALUES (?,?,?);";
                    stmt = conn.prepareStatement(sql);

                    stmt.setString(1, null);
                    stmt.setInt(2,IDENQUETE);
                    stmt.setString(3, opcoes.get(aux));
                    stmt = conn.prepareStatement(sql);
                    stmt.executeUpdate();

                    ++aux;
                }                
            }

            respostas.addAll(enquete.getRespostas());
            if (respostas.size() > 0) {
            	int aux = 0;
                while(aux <= respostas.size()){

                    sql = "INSERT INTO Respostas (ID, IDENQUETE, IDALUNO, COMENTARIO) VALUES (?,?,?);";
                    stmt = conn.prepareStatement(sql);

                    stmt.setString(1, null);
                    stmt.setInt(2,IDENQUETE);
                    stmt.setInt(3, respostas.get(aux).getAlunoId());
                    stmt.setString(3, respostas.get(aux).getResposta());
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
                + "DELETE FROM Respostas WHERE IDEnquete ILIKE " + enquete.getId() + ";"
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
        List<Enquete> enquetes = null;
        //String sql = "SELECT * FROM Enquete JOIN Respostas NATURAL JOIN Opcoes NATURAL JOIN Comentarios WHERE (ID = IDENQUETE);";
        String sql = "SELECT * FROM Enquete;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                List<String> comentarios = null;
                List<String> opcoes = null;
                List<Resposta> respostas = null;

                Enquete enquete = new Enquete();
                enquete.setId(rs.getInt("id"));
                enquete.setNome(rs.getString("nome"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));

                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete ILIKE " + enquete.getId();
                ResultSet rsListas = stmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rs.getString("comentario"));
                }
                enquete.setComentarios(comentarios);

                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete ILIKE " + enquete.getId();
                rsListas = stmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rs.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                String sqlRespostas = "SELECT * FROM Respostas WHERE IDEnquete ILIKE " + enquete.getId();
                rsListas = stmt.executeQuery(sqlRespostas);
                while (rsListas.next()){
                    Resposta resposta = new Resposta();
                    resposta.setResposta(rs.getString("resposta"));
                    resposta.setAlunoId(rs.getInt("aluno"));
                }
                enquete.setRespostas(respostas);

                
                enquetes.add(enquete);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquetes;
    }

    @Override
    public Enquete buscar(String id) {
        String sql = "SELECT * FROM Enquete WHERE id ILIKE" + id;
        Enquete enquete = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                List<String> comentarios = null;
                List<String> opcoes = null;
                List<Resposta> respostas = null;

                enquete = new Enquete();
                enquete.setId(rs.getInt("id"));
                enquete.setNome(rs.getString("nome"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));

                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete ILIKE " + enquete.getId();
                ResultSet rsListas = stmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rs.getString("comentario"));
                }
                enquete.setOpcoes(comentarios);

                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete ILIKE " + enquete.getId();
                rsListas = stmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rs.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                String sqlRespostas = "SELECT * FROM Respostas WHERE IDEnquete ILIKE " + enquete.getId();
                rsListas = stmt.executeQuery(sqlRespostas);
                while (rsListas.next()){
                    Resposta resp = new Resposta();
                    resp.setResposta(rs.getString("resposta"));
                    resp.setAlunoId(rs.getInt("aluno"));
                }
                enquete.setRespostas(respostas);
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquete;
    }

    public int buscarId(String nome) {
        String sql = "SELECT * FROM Enquete WHERE nome ILIKE" + nome;
        int aux = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                aux = rs.getInt("id");
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aux;
    }

    public void adicionarResposta(int IdEnquete, int IdAluno, String resposta){
        String sql = "INSERT INTO Respostas (ID, IDENQUETE, IDALUNO, COMENTARIO) VALUES (?,?,?);";
                   
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);

            //stmt.setString(1, null);
            stmt.setInt(2,IdEnquete);
            stmt.setInt(3, IdAluno);
            stmt.setString(4, resposta);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
