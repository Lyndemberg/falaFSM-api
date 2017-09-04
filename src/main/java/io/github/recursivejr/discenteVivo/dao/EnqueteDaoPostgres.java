package io.github.recursivejr.discenteVivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Comentario;
import io.github.recursivejr.discenteVivo.models.Enquete;
import io.github.recursivejr.discenteVivo.models.Opcao;
import io.github.recursivejr.discenteVivo.models.Resposta;

public class EnqueteDaoPostgres implements EnqueteDaoInterface {
    private final Connection conn;

    public EnqueteDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    @Override
    public boolean  adicionar(Enquete enquete) {
        String sql = "INSERT INTO Enquete (NOME, DESCRICAO, FOTO, EMAILADMIN) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, enquete.getNome());
            stmt.setString(2, enquete.getDescricao());
            stmt.setString(3, enquete.getFoto());
            stmt.setString(4, enquete.getEmailAdmin());

            stmt.executeUpdate();

            int IDENQUETE = buscarId(enquete.getNome());
                      
            List<Opcao> opcoes = new ArrayList<>();

            if (enquete.getOpcoes() != null) {
            	opcoes.addAll(enquete.getOpcoes());
                int aux = 0;
                while(aux <= opcoes.size()){

                    sql = "INSERT INTO Opcoes (IDENQUETE, Opcao) VALUES (?,?);";
                    stmt = conn.prepareStatement(sql);

                    stmt.setInt(1,IDENQUETE);
                    stmt.setString(2, opcoes.get(aux).getOpcao());
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

        String sql = "SELECT * FROM Enquete;";

        return getEnquetes(sql);
    }

    @Override
    public Enquete buscar(String id) {

        //Testar se n da erro ao tentar buscar uma enquete q nao existe

        String sql = "SELECT * FROM Enquete WHERE id = " + id + ";";

        List<Enquete> enquetes = getEnquetes(sql);

        if (enquetes.isEmpty()) {
            return null;
        } else {
            return enquetes.get(0);
        }
        
    }

    @Override
    public List<Enquete> enquetesPorSetor(String nomeSetor) {

        String sql = "SELECT E.* FROM Enquete E, EnquetesSetor ES " +
                "WHERE E.Id = ES.idEnquete AND ES.nomeSetor ILIKE " + nomeSetor +";";

        return getEnquetes(sql);
    }

    @Override
    public List<Enquete> enquetesPorCurso(String nomeCurso) {

        String sql = "SELECT E.* FROM Enquete E, EnquetesCurso EC " +
                "WHERE E.Id = EC.idEnquete AND EC.nomeSetor ILIKE " + nomeCurso +";";

        return getEnquetes(sql);
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

    private List<Enquete> getEnquetes(String sql){
        List<Enquete> enquetes = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                List<Comentario> comentarios = new ArrayList<>();
                List<Opcao> opcoes = new ArrayList<>();
                List<Resposta> respostas = new ArrayList<>();

                Enquete enquete = new Enquete();
                enquete.setId(rs.getInt("id"));
                enquete.setNome(rs.getString("nome"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));

                Statement internalStmt = conn.createStatement();

                //Percorre todos os comentarios e adiciona cada um ao Arraylist desta enquete
                String sqlComentarios = "SELECT * FROM Comentarios WHERE IDEnquete = " + enquete.getId() + ";";
                ResultSet rsLista = internalStmt.executeQuery(sqlComentarios);
                while (rsLista.next()) {

                    Comentario comentario = new Comentario(
                            rsLista.getInt("ID"),
                            rsLista.getInt("IdEnquete"),
                            rsLista.getString("Comentario")
                    );

                    comentarios.add(comentario);
                }
                enquete.setComentarios(comentarios);

                //Percorre todas as Opcoes e adiciona cada uma ao Arraylist desta enquete
                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = " + enquete.getId() + ";";
                rsLista = internalStmt.executeQuery(sqlOpcoes);
                while (rsLista.next()) {

                    Opcao opcao = new Opcao(
                            rsLista.getInt("ID"),
                            rsLista.getInt("IdEnquete"),
                            rsLista.getString("Opcao")
                    );

                    opcoes.add(opcao);
                }
                enquete.setOpcoes(opcoes);

                //Percorre todas as Respostas e adiciona cada uma ao Arraylist desta enquet
                String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = " + enquete.getId() + ";";
                rsLista = internalStmt.executeQuery(sqlRespostas);
                while (rsLista.next()) {

                    Resposta resposta = new Resposta();

                    resposta.setResposta(rsLista.getString("Resposta"));
                    resposta.setMatAluno(rsLista.getString("matriculaAluno"));
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
