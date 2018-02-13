package io.github.recursivejr.discenteVivo.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.EnqueteDaoInterface;
import io.github.recursivejr.discenteVivo.models.*;

public class EnqueteDaoPostgres extends ElementoDao implements EnqueteDaoInterface {

    public EnqueteDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public Integer  adicionar(Enquete enquete) {
        Integer idEnquete = null;

        String sql = "INSERT INTO Enquete (NOME, DESCRICAO, EMAILADMIN, FOTO) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, enquete.getNome());
            stmt.setString(2, enquete.getDescricao());
            stmt.setString(3, enquete.getEmailAdmin());
            stmt.setString(4, enquete.getFoto());

            stmt.executeUpdate();

            //Recupera o valor do Id desta Enquete no BD para ser usada nas proximas Querys
            idEnquete = buscarId(enquete.getNome());

            //Cria um ArrayList de Opçoes e Verifica se Ha Opçoes que devem ser Salvas nesta Enquete
            List<Opcao> opcoes = new ArrayList<>();

            //Se nao for null entao ha Opcoes que devem ser referenciadas
            if (enquete.getOpcoes() != null) {
                //Recupera todas as Opçoes que serao salvas
            	opcoes.addAll(enquete.getOpcoes());

                sql = "INSERT INTO Opcoes (IDENQUETE, Opcao) VALUES (?,?);";
                stmt = getConexao().prepareStatement(sql);

                //Perccore todas as opcoes salvando eles no BD
                for (int i = 0; i < opcoes.size(); i++) {
                    stmt.setInt(1, idEnquete);
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
                stmt = getConexao().prepareStatement(sql);

                //Perccore todas os Cursos salvando eles no BD
                for (int i = 0; i < cursos.size(); i++) {
                    stmt.setInt(1, idEnquete);
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
                stmt = getConexao().prepareStatement(sql);

                //Perccore todas os Setores salvando eles no BD
                for (int i = 0; i < setores.size(); i++) {
                    stmt.setInt(1, idEnquete);
                    stmt.setString(2, setores.get(i).getNome());
                    stmt.executeUpdate();
                }
            }
            
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idEnquete;
    }

    @Override
    public boolean remover(int idEnquete) {
        String sql = "DELETE FROM EnquetesSetor WHERE IdEnquete = ?;"
                + "DELETE FROM EnquetesCurso WHERE IdEnquete = ?;"
                + "DELETE FROM ComentaEnquete WHERE IdEnquete = ?;"
                + "DELETE FROM Opcoes WHERE IdEnquete = ?;"
                + "DELETE FROM RespondeEnquete WHERE IdEnquete = ?;"
                + "DELETE FROM Enquete WHERE IdEnquete = ?;";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, idEnquete);
            stmt.setInt(2, idEnquete);
            stmt.setInt(3, idEnquete);
            stmt.setInt(4, idEnquete);
            stmt.setInt(5, idEnquete);
            stmt.setInt(6, idEnquete);

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Enquete enquete) {
        String sql = "DELETE FROM Opcoes WHERE IdEnquete = ?"
                + "DELETE FROM EnquetesSetor WHERE IdEnquete = ?;"
                + "DELETE FROM EnquetesCurso WHERE IdEnquete = ?;"
                + "UPDATE Enquete SET Nome = ?, Descricao = ?, emailAdmin = ? WHERE IdEnquete = ?;";


        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setInt(1, enquete.getId());
            stmt.setInt(2, enquete.getId());
            stmt.setInt(3, enquete.getId());
            stmt.setString(4, enquete.getNome());
            stmt.setString(5, enquete.getDescricao());
            stmt.setString(6, enquete.getEmailAdmin());
            stmt.setInt(7, enquete.getId());


            stmt.executeUpdate(sql);

            sql = "INSERT INTO EnquetesCurso(NomeCurso, IdEnquete) VALUES (?,?);";
            PreparedStatement stmtCurso = getConexao().prepareStatement(sql);
            for (Curso curso : enquete.getCursos()) {
                stmtCurso.setString(1, curso.getNome());
                stmtCurso.setInt(2, enquete.getId());
                stmtCurso.executeUpdate(sql);
            }

            sql = "INSERT INTO EnquetesSetor(NomeSetor, IdEnquete) VALUES (?,?);";
            PreparedStatement stmtSetor = getConexao().prepareStatement(sql);
            for (Setor setor : enquete.getSetores()) {
                stmtSetor.setString(1, setor.getNome());
                stmtSetor.setInt(2, enquete.getId());
                stmtSetor.executeUpdate(sql);
            }

            sql = "INSERT INTO Opcoes(Opcao, IdEnquete) VALUES (?,?)";
            PreparedStatement stmtOpcoes = getConexao().prepareStatement(sql);
            for (Opcao opcao : enquete.getOpcoes()) {
                stmtOpcoes.setString(1, opcao.getOpcao());
                stmtOpcoes.setInt(2, enquete.getId());
                stmtOpcoes.executeUpdate(sql);
            }

            stmt.close();
            stmtCurso.close();
            stmtSetor.close();
            stmtOpcoes.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Enquete> listar() {
        //Retorna todas as Enquetes Salvas

        String sql = "SELECT * FROM Enquete;";
        return getAllEnquetes(sql);
    }

    @Override
    public List<Enquete> listarPorAluno(String matAluno) {
        //caso matAluno nao seja null entao filtra por aluno
        //recebendo apenas as enquetes que nao tem nenhum curso pois sao para toda a universidade
        //e as enquetes do seu curso especifico

        String sql = String.format("SELECT E.* FROM Enquete E NATURAL LEFT JOIN EnquetesCurso EC" +
                " WHERE EC.NomeCurso IS NULL OR EC.NomeCurso ILIKE" +
                " (SELECT NomeCurso FROM Aluno WHERE Matricula ILIKE '%s');", matAluno);
        return getEnquetes(sql, matAluno);
    }


    @Override
    public Enquete buscar(int idEnquete, String matAluno) {

        //Testar se n da erro ao tentar buscar uma enquete q nao existe

        String sql = String.format("SELECT * FROM Enquete WHERE idEnquete = '%d';", idEnquete);

        List<Enquete> enquetes = null;

        //Se nao tiver matAluno entao nao e necessario filtrar por aluno
        if (matAluno == null)
            enquetes = getAllEnquetes(sql);
        else
            //caso matAluno nao seja null entao filtra por aluno
            enquetes = getEnquetes(sql, matAluno);

        if (enquetes.isEmpty()) {
            return null;
        } else {
            return enquetes.get(0);
        }
        
    }

    @Override
    public List<Enquete> enquetesPorSetor(String nomeSetor, String matAluno) {

        String sql = String.format("SELECT E.* FROM Enquete E NATURAL JOIN EnquetesSetor ES " +
                "WHERE ES.nomeSetor ILIKE '%s';", nomeSetor);

        //Se nao tiver matAluno entao nao e necessario filtrar por aluno
        if (matAluno == null)
            return getAllEnquetes(sql);
        else
            //caso matAluno nao seja null entao filtra por aluno
            return getEnquetes(sql, matAluno);
    }

    @Override
    public List<Enquete> enquetesPorCurso(String nomeCurso, String matAluno) {

        String sql = String.format("SELECT E.* FROM Enquete E NATURAL JOIN EnquetesCurso EC " +
                "WHERE EC.nomeCurso ILIKE '%s';", nomeCurso);

        //Se nao tiver matAluno entao nao e necessario filtrar por aluno
        if (matAluno == null)
            return getAllEnquetes(sql);
        else
            //caso matAluno nao seja null entao filtra por aluno
            return getEnquetes(sql, matAluno);
    }

    private int buscarId(String nome) {
        String sql = String.format("SELECT idEnquete FROM Enquete WHERE nome ILIKE '%s';", nome);
        int aux = -1;
        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                aux = rs.getInt("idEnquete");
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aux;
    }

    @Override
    public boolean atualizarFoto(String foto, int idEnquete) {
        String sql = "UPDATE Enquete SET Foto = ? WHERE idEnquete = ?";

        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, foto);
            stmt.setInt(2, idEnquete);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public String retornarFoto(int idEnquete) {
        String foto = null;
        String sql = String.format("SELECT Foto FROM Enquete WHERE idEnquete = %d", idEnquete);

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next())
                foto = rs.getString("Foto");

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foto;
    }

    private List<Enquete> getEnquetes(String sql, String matAluno){
        List<Enquete> enquetes = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                List<Comentario> comentarios = new ArrayList<>();
                List<Opcao> opcoes = new ArrayList<>();
                List<Resposta> respostas = new ArrayList<>();
                List<Curso> cursos = new ArrayList<>();
                List<Setor> setores = new ArrayList<>();

                Enquete enquete = new Enquete();
                enquete.setId(rs.getInt("idEnquete"));
                enquete.setNome(rs.getString("nome"));
                enquete.setFoto(rs.getString("foto"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));

                Statement internalStmt = getConexao().createStatement();

                //Percorre todos os comentarios e recupera apenas o comentario deste aluno
                String sqlComentarios = String.format("SELECT * FROM ComentaEnquete WHERE IDEnquete = '%d'" +
                        " and MatriculaAluno ILIKE '%s';", enquete.getId(), matAluno);
                ResultSet rsLista = internalStmt.executeQuery(sqlComentarios);
                while (rsLista.next()) {

                    Comentario comentario = new Comentario(
                            rsLista.getInt("IdEnquete"),
                            rsLista.getString("MatriculaAluno"),
                            rsLista.getString("Comentario")
                    );

                    comentarios.add(comentario);
                }
                enquete.setComentarios(comentarios);

                //Percorre todas as Opcoes e adiciona cada uma ao Arraylist desta enquete
                String sqlOpcoes = String.format("SELECT * FROM Opcoes WHERE IDEnquete = '%d';", enquete.getId());
                rsLista = internalStmt.executeQuery(sqlOpcoes);
                while (rsLista.next()) {

                    Opcao opcao = new Opcao(
                            rsLista.getInt("IdEnquete"),
                            rsLista.getString("Opcao")
                    );

                    opcoes.add(opcao);
                }
                enquete.setOpcoes(opcoes);

                //Percorre todas as Respostas e retorna a(s) resposta(s) deste aluno
                String sqlRespostas = String.format("SELECT * FROM RespondeEnquete WHERE IDEnquete = '%d'" +
                        " and MatriculaAluno ILIKE '%s';", enquete.getId(), matAluno);
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
                String sqlCursos = String.format("SELECT NomeCurso FROM EnquetesCurso" +
                        " WHERE idEnquete = '%d';", enquete.getId());
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
                String sqlSetores = String.format("SELECT NomeSetor FROM EnquetesSetor" +
                        " WHERE idEnquete = '%d';", enquete.getId());
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquetes;
    }

    private List<Enquete> getAllEnquetes(String sql){
        List<Enquete> enquetes = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                List<Comentario> comentarios = new ArrayList<>();
                List<Opcao> opcoes = new ArrayList<>();
                List<Resposta> respostas = null;
                List<Curso> cursos = new ArrayList<>();
                List<Setor> setores = new ArrayList<>();

                Enquete enquete = new Enquete();
                enquete.setId(rs.getInt("idEnquete"));
                enquete.setNome(rs.getString("nome"));
                enquete.setFoto(rs.getString("Foto"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setEmailAdmin(rs.getString("emailAdmin"));

                Statement internalStmt = getConexao().createStatement();

                //Percorre todos os comentarios e adiciona cada um ao Arraylist desta enquete
                String sqlComentarios = "SELECT * FROM ComentaEnquete WHERE IDEnquete = '" + enquete.getId() + "';";
                ResultSet rsLista = internalStmt.executeQuery(sqlComentarios);
                while (rsLista.next()) {

                    Comentario comentario = new Comentario(
                            rsLista.getInt("IdEnquete"),
                            rsLista.getString("MatriculaAluno"),
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
                            rsLista.getInt("IdEnquete"),
                            rsLista.getString("Opcao")
                    );

                    opcoes.add(opcao);
                }
                enquete.setOpcoes(opcoes);

                //Nao e necessario recuperar as Respostas das Enquetes para este Metodo

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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enquetes;
    }
}
