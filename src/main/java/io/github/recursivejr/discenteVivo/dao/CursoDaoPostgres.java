package io.github.recursivejr.discenteVivo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.models.Curso;
import io.github.recursivejr.discenteVivo.models.Endereco;
import io.github.recursivejr.discenteVivo.models.Enquete;

public class CursoDaoPostgres extends ElementoDao implements CursoDaoInterface{

    public CursoDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Curso curso) {
        String sql = "INSERT INTO Curso(Nome, Descricao) VALUES (?, ?);";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Curso curso) {
        String sql = "DELETE FROM EnquetesCurso WHERE nomeCurso ILIKE '" + curso.getNome() + "'; " +
        				"DELETE FROM Curso WHERE nome ILIKE '" + curso.getNome() + "';";
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
    public List<Curso> listar() {
        String sql = "SELECT * FROM Curso;";

        return getCursos(sql, null);
    }

    @Override
    public Curso buscar(String nome) {
        String sql = "SELECT * FROM Curso WHERE nome ILIKE '" + nome + "';";

        List<Curso> cursos = getCursos(sql, nome);

        if(cursos.isEmpty())
            return null;
        else
            return cursos.get(0);
    }

    private List<Curso> getCursos(String sql, String nomeCurso) {
        List<Curso> cursos = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Curso curso = new Curso();
                curso.setNome(rs.getString("Nome"));
                curso.setDescricao(rs.getString("Descricao"));

                //Recuperando Lista de Aluno
                List<Aluno> alunos = new ArrayList<>();
                String recuperaAlunos = null;

                //Se nomeCurso for null entao procura todos os aluno
                //Se nome curso diferente de null entao filtra pelo nome
                if(nomeCurso != null)
                    recuperaAlunos = "SELECT * FROM Aluno AS A JOIN Curso AS C ON A.NomeCurso = C.Nome;";
                else
                    recuperaAlunos = "SELECT * FROM Aluno AS A JOIN Curso AS C ON A.NomeCurso = C.Nome "+
                            "WHERE C.Nome ILIKE '"+ nomeCurso + "';";

                Statement internalstmt = getConexao().createStatement();
                ResultSet rsListas = internalstmt.executeQuery(sql);

                while(rsListas.next()) {
                    alunos.add(new Aluno(
                            rsListas.getString("Nome"),
                            rsListas.getString("Email"),
                            rsListas.getString("Login"),
                            rsListas.getString("Senha"),
                            new Endereco(
                                    rsListas.getString("Cidade"),
                                    rsListas.getString("Rua"),
                                    rsListas.getString("Numero")
                            ),
                            rsListas.getString("Matricula"),
                            rsListas.getString("NomeCurso")
                    ));
                }
                curso.setAlunos(alunos);

                //Recupera Lista de Enquetes
                List<Enquete> enquetes = new EnqueteDaoPostgres()
                                                .enquetesPorCurso(
                                                        rs.getString("Nome")
                                                );
                curso.setEnquetes(enquetes);

                cursos.add(curso);
            }
            stmt.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return cursos;

    }

}
