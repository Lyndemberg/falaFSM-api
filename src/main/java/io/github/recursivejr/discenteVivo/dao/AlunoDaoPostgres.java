package io.github.recursivejr.discenteVivo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.resources.Encryption;

public class AlunoDaoPostgres implements AlunoDaoInterface{
    private final Connection conn;

    public AlunoDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }
    
    @Override
    public boolean adicionar(Aluno aluno) {
        String sql = "INSERT INTO Aluno(Matricula, Email, Nome, Login, Senha, Cidade, Rua, Numero) VALUES (?,?,?,?,?,?,?,?);";

        return setAluno(sql, aluno);
    }

    @Override
    public boolean remover(Aluno aluno) {
    	//Remove Aluno da tabela AlunoCurso = aluno frequenta curso
    	String sql = "DELETE FROM AlunoCurso WHERE matriculaAluno ILIKE " + aluno.getMatricula() + ";";
    	try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            
        } catch (SQLException ex) {
                ex.printStackTrace(); 
        }
    	
    	//Remove o aluno da tabela aluno
        sql = "DELETE FROM Aluno WHERE matricula ILIKE " + aluno.getMatricula()+ ";";
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
    public List<Aluno> listar() {
        String sql = "SELECT * FROM Aluno";

        return getAlunos(sql);
    }

    @Override
    public Aluno buscar(String email) {

        //Testar se n da erro ao tentar buscar um aluno q nao existe

        String sql = "SELECT * FROM Aluno WHERE email ILIKE " + email + ";";

        List<Aluno> alunos = getAlunos(sql);

        if(alunos.isEmpty())
            return null;
        else
            return alunos.get(0);
    }

    @Override
    public boolean atualizar(Aluno aluno) {
        String sql = "Update Aluno SET Email = ?, Nome = ?, Login = ?, Senha = ?, Cidade = ?, Rua = ?, Numero = ? "
                + "WHERE Matricula ILIKE " + aluno.getMatricula() + ";"
                + "DELETE FROM AlunoCurso WHERE Matricula ILIKE " + aluno.getMatricula() + ";";

        return setAluno(sql, aluno);
    }
    
    public String login(String login, String senha) throws Exception {
    	
    	senha = Encryption.encrypt(senha);
    	
    	String sql = "SELECT Matricula FROM Aluno WHERE login ILIKE '" + login + "' AND SENHA ILIKE '" + senha + "';";
    	Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(!rs.next()) {
				throw new Exception("Credenciais Inválidas");
			}
			
			String matricula = rs.getString("matricula");
			
			stmt.close();
            conn.close();
			return matricula;
			
		} catch (SQLException ex) {
			Logger.getLogger(ex.getMessage());
			throw ex;
		}
    }

    private boolean setAluno(String sql, Aluno aluno) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getNome());
            stmt.setString(4, aluno.getLogin());
            stmt.setString(5, Encryption.encrypt(aluno.getSenha()));//Senha Criptografada em md5
            stmt.setString(6, aluno.getEndereco().getCidade());
            stmt.setString(7, aluno.getEndereco().getRua());
            stmt.setString(8, aluno.getEndereco().getNumero());
            stmt.executeUpdate();

            //Seta os cursos do aluno na tabela AlunoCurso
            sql = "INSERT INTO AlunoCurso(matriculaAluno, nomeCurso) VALUES (?,?);";
            for(String curso: aluno.getCursos()) {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, aluno.getMatricula());
                stmt.setString(2, curso);
                stmt.executeUpdate();
            }

            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private List<Aluno> getAlunos(String sql) {
        List<Aluno> alunos = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setEmail(rs.getString("email"));
                aluno.setNome(rs.getString("nome"));
                aluno.setLogin(rs.getString("login"));
                aluno.setSenha(rs.getString("senha"));
                aluno.getEndereco().setRua(rs.getString("rua"));
                aluno.getEndereco().setCidade(rs.getString("cidade"));
                aluno.getEndereco().setNumero(rs.getString("numero"));

                //Procura e Adiciona os cursos que este aluno frequenta
                List<String> cursos = new ArrayList<>();

                String recuperaCursos = "SELECT * FROM AlunoCurso WHERE matriculaAluno ILIKE " +
                        aluno.getMatricula() + ";";

                Statement internalStmt = conn.createStatement();
                ResultSet rsCursos = internalStmt.executeQuery(recuperaCursos);

                while(rsCursos.next()) {
                    cursos.add(rsCursos.getString("nome"));
                }
                internalStmt.close();

                aluno.setCursos(cursos);

                alunos.add(aluno);
            }
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return alunos;
    }

}
