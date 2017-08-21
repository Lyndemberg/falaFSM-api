package io.github.recursivejr.discenteVivo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
            
            System.out.printf("\n\nPEGUEI O ID : " + IDENQUETE);

            List<String> comentarios = new ArrayList();            
            List<String> opcoes = new ArrayList();
            List<Resposta> respostas = new ArrayList();

            if (enquete.getComentarios() != null) {
            	comentarios.addAll(enquete.getComentarios());
                int aux = 0;
                while(aux <= comentarios.size()){

                    sql = "INSERT INTO Comentarios (IDENQUETE, COMENTARIO) VALUES (?,?);";
                    stmt = conn.prepareStatement(sql);

                    stmt.setInt(1,IDENQUETE);
                    stmt.setString(2, comentarios.get(aux));
                    stmt = conn.prepareStatement(sql);
                    stmt.executeUpdate();

                    ++aux;
                }                
            }

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

            if (enquete.getRespostas() != null) {
                respostas.addAll(enquete.getRespostas());
            	int aux = 0;
                while(aux <= respostas.size()){

                    sql = "INSERT INTO Respostas (IDENQUETE, IDALUNO, COMENTARIO) VALUES (?,?,?);";
                    stmt = conn.prepareStatement(sql);

                    stmt.setInt(1,IDENQUETE);
                    stmt.setInt(2, respostas.get(aux).getAlunoId());
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
        List<Enquete> enquetes = new ArrayList<>();
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

                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete = " + enquete.getId() + ";";
                ResultSet rsListas = stmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rs.getString("comentario"));
                }
                enquete.setComentarios(comentarios);

                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = stmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rs.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                String sqlRespostas = "SELECT * FROM Respostas WHERE IDEnquete = " + enquete.getId() + ";";
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
        String sql = "SELECT * FROM Enquete WHERE id = " + id + ";";
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

                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete = " + enquete.getId() + ";";
                ResultSet rsListas = stmt.executeQuery(sqlComentarios);
                while (rsListas.next()){
                    comentarios.add(rs.getString("comentario"));
                }
                enquete.setOpcoes(comentarios);

                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = " + enquete.getId() + ";";
                rsListas = stmt.executeQuery(sqlOpcoes);
                while (rsListas.next()){
                    opcoes.add(rs.getString("opcao"));
                }
                enquete.setOpcoes(opcoes);

                String sqlRespostas = "SELECT * FROM Respostas WHERE IDEnquete = " + enquete.getId() + ";";
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
        String sql = "SELECT * FROM Enquete WHERE nome ILIKE '" + nome + "';";
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

    public boolean adicionarResposta(int IdEnquete, int IdAluno, String resposta) {
        String sql = "INSERT INTO Respostas (IDENQUETE, IDALUNO, RESPOSTA) VALUES (?,?,?);";
                   
        try {
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	
            stmt.setInt(1,IdEnquete);
            stmt.setInt(2, IdAluno);
            stmt.setString(3, resposta);
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return true;
    }
    
    public String[][] relatorio(String nome) {
    	String sql = "SELECT Id FROM Enquete WHERE Nome ILIKE " + nome + ";";
    	
    	//Matriz de resposta[opçao][numVotos]
    	String respostas[][] = new String[1][1];
    	
    	respostas[0][0] = "Não";
        respostas[0][1] = "0";
        respostas[1][1] = "Sim";
        respostas[1][1] = "0";
    	
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		if(rs.next()) {
    			String idEnquete = rs.getString("id");
    			
    			//Recuperando as Respostas
    			 String sqlRespostas = "SELECT * FROM Respostas WHERE IDEnquete = " + idEnquete + ";";
                 ResultSet rsListas = stmt.executeQuery(sqlRespostas); 
                 
                 while (rsListas.next()){
                	 if (rs.getString("resposta") == "Não") {
                		 respostas[0][1] = String.valueOf((Integer.parseInt(respostas[0][1]) + 1));
                	 } else {
                		 respostas[1][1] = String.valueOf((Integer.parseInt(respostas[1][1]) + 1));
                	 }
                	 
                     Resposta resp = new Resposta();
                     resp.setResposta(rs.getString("resposta"));
                     resp.setAlunoId(rs.getInt("aluno"));
                 }
    		}
    		
		} catch (SQLException ex) {
			Logger.getLogger(ex.getMessage());
		}
    	return respostas;
    }

}
