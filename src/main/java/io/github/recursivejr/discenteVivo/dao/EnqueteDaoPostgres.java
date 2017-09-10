package io.github.recursivejr.discenteVivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.*;

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

            //Recupera o valor do Id desta Enquete no BD para ser usada nas proximas Querys
            final int IDENQUETE = buscarId(enquete.getNome());

            //Cria um ArrayList de Opçoes e Verifica se Ha Opçoes que devem ser Salvas nesta Enquete
            List<Opcao> opcoes = new ArrayList<>();

            //Se nao for null entao ha Opcoes que devem ser referenciadas
            if (enquete.getOpcoes() != null) {
                //Recupera todas as Opçoes que serao salvas
            	opcoes.addAll(enquete.getOpcoes());

                sql = "INSERT INTO Opcoes (IDENQUETE, Opcao) VALUES (?,?);";
                stmt = conn.prepareStatement(sql);

                //Perccore todas as opcoes salvando eles no BD
                for (int i = 0; i < opcoes.size(); i++) {
                    stmt.setInt(1,IDENQUETE);
                    stmt.setString(2, opcoes.get(i).getOpcao());
                    stmt.executeUpdate();
                }
            }

            //Cria um ArrayList de Cursos e Verifica se Ha Cursos que devem ser Referenciados
                // por esta Enquete
            List<Curso> cursos = new ArrayList<>();

            //Se nao for null entao ha Cursos que devem ser Referenciados
            if (enquete.getCursos() != null) {
                //Recupera todas os Cursos que serao Referenciados
                cursos.addAll(enquete.getCursos());

                sql = "INSERT INTO EnquetesCurso (IDENQUETE, NomeCurso) VALUES (?,?);";
                stmt = conn.prepareStatement(sql);

                //Perccore todas os Cursos salvando eles no BD
                for (int i = 0; i < cursos.size(); i++) {
                    stmt.setInt(1,IDENQUETE);
                    stmt.setString(2, cursos.get(i).getNome());
                    stmt.executeUpdate();
                }
            }

            //Cria um ArrayList de Setores e Verifica se Ha Setores que devem ser
                // Referenciados nesta Enquete
            List<Setor> setores = new ArrayList<>();

            //Se nao for null entao ha Setores que devem ser Referenciados
            if (enquete.getSetores() != null) {
                //Recupera todas os Setores que serao Referenciados
                setores.addAll(enquete.getSetores());

                sql = "INSERT INTO EnquetesSetor (IDENQUETE, NomeSetor) VALUES (?,?);";
                stmt = conn.prepareStatement(sql);

                //Perccore todas os Setores salvando eles no BD
                for (int i = 0; i < setores.size(); i++) {
                    stmt.setInt(1,IDENQUETE);
                    stmt.setString(2, setores.get(i).getNome());
                    stmt.executeUpdate();
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
        String sql = "DELETE FROM Comentario WHERE IDEnquete ILIKE '" + enquete.getId() + "';"
                + "DELETE FROM Opcoes WHERE IDEnquete ILIKE '" + enquete.getId() + "'';"
                + "DELETE FROM RespondeEnquete WHERE IDEnquete ILIKE '" + enquete.getId() + "';"
                + "DELETE FROM Enquete WHERE ID ILIKE '" + enquete.getId() + "';";
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

        String sql = "SELECT * FROM Enquete WHERE id = '" + id + "';";

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
                "WHERE E.Id = ES.idEnquete AND ES.nomeSetor ILIKE '" + nomeSetor +"';";

        return getEnquetes(sql);
    }

    @Override
    public List<Enquete> enquetesPorCurso(String nomeCurso) {

        String sql = "SELECT E.* FROM Enquete E, EnquetesCurso EC " +
                "WHERE E.Id = EC.idEnquete AND EC.nomeSetor ILIKE '" + nomeCurso +"';";

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
//            stmt.close();
//            conn.close();
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
                List<Curso> cursos = new ArrayList<>();
                List<Setor> setores = new ArrayList<>();

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
                String sqlOpcoes = "SELECT * FROM Opcoes WHERE IDEnquete = '" + enquete.getId() + "';";
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

                //Percorre todas as Respostas e adiciona cada uma ao Arraylist desta enquete
                String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = '" + enquete.getId() + "';";
                rsLista = internalStmt.executeQuery(sqlRespostas);
                while (rsLista.next()) {

                    Resposta resposta = new Resposta(
                            rsLista.getInt("IdEnquete"),
                            rsLista.getString("Resposta"),
                            rsLista.getString("matriculaAluno")
                    );

                    respostas.add(resposta);
                }
                enquete.setRespostas(respostas);

                //Percorre todos os Cursos Associados a esta Enquete e adiciona-os ao ArrayList de Retorno
                String sqlCursos = "SELECT NomeCurso FROM EnquetesCurso WHERE idEnquete = '"+ enquete.getId() +"';";
                rsLista = internalStmt.executeQuery(sqlCursos);
                while (rsLista.next()) {

                    //Retorno apenas o nome pois e o unico dado realmente necessario
                        //para se identificar a qual Curso pertence a Enquete
                    Curso curso = new Curso();
                    curso.setNome(rsLista.getString("NomeCurso"));

                    cursos.add(curso);
                }
                enquete.setCursos(cursos);

                //Percorre todos os Setores Associados a esta Enquete e adiciona-os ao ArrayList de Retorno
                String sqlSetores = "SELECT NomeSetor FROM EnquetesSetor WHERE idEnquete = '" + enquete.getId() + "';";
                rsLista = internalStmt.executeQuery(sqlSetores);
                while (rsLista.next()) {

                    //Retorno apenas o nome pois e o unico dado realmente necessario
                        //para se identificar a qual Setor pertence a Enquete
                    Setor setor = new Setor();
                    setor.setNome(rsLista.getString("NomeSetor"));

                    setores.add(setor);
                }
                enquete.setSetores(setores);

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
