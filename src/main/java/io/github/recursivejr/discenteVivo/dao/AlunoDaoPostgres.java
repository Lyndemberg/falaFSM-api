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
import io.github.recursivejr.discenteVivo.models.Endereco;
import io.github.recursivejr.discenteVivo.resources.Encryption;

public class AlunoDaoPostgres implements AlunoDaoInterface{
    private final Connection conn;

    public AlunoDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }
    
    @Override
    public boolean adicionar(Aluno aluno) {
        String sql = "INSERT INTO Aluno(Matricula, Email, Nome, Login, Senha, Cidade, Rua, Numero, NomeCurso) " +
                "VALUES ('" + aluno.getMatricula() + "',?,?,?,?,?,?,?,?);";

        return setAluno(sql, aluno);
    }

    @Override
    public boolean remover(Aluno aluno) {
    	//Remove o aluno da tabela aluno
        String sql = "DELETE FROM Aluno WHERE Matricula ILIKE '" + aluno.getMatricula()+ "';";
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

        return getAlunos(sql, null);
    }

    @Override
    public Aluno buscar(String matricula) {

        //Testar se n da erro ao tentar buscar um aluno q nao existe

        String sql = "SELECT * FROM Aluno WHERE Matricula ILIKE ?;";

        List<Aluno> alunos = getAlunos(sql, matricula);

        if(alunos.isEmpty())
            return null;
        else
            return alunos.get(0);
    }

    @Override
    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE Aluno SET Email = ?, Nome = ?, Login = ?, Senha = ?, Cidade = ?, Rua = ?, Numero = ?, " +
                "NomeCurso = ? WHERE Matricula ILIKE '" + aluno.getMatricula() + "';";

        return setAluno(sql, aluno);
    }

    @Override
    public String login(String login, String senha) throws Exception {
    	
    	senha = Encryption.encrypt(senha);
    	
    	String sql = "SELECT Matricula FROM Aluno WHERE Login ILIKE ? AND SENHA ILIKE ?;";
    	PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, login);
			stmt.setString(2, senha);

			ResultSet rs = stmt.executeQuery();
			
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
            stmt.setString(1, aluno.getEmail());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getLogin());
            stmt.setString(4, Encryption.encrypt(aluno.getSenha()));//Senha Criptografada em md5
            stmt.setString(5, aluno.getEndereco().getCidade());
            stmt.setString(6, aluno.getEndereco().getRua());
            stmt.setString(7, aluno.getEndereco().getNumero());
            stmt.setString(8, aluno.getCurso());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private List<Aluno> getAlunos(String sql, String param) {
        List<Aluno> alunos = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            if(param != null)
                stmt.setString(1, param);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setEmail(rs.getString("email"));
                aluno.setNome(rs.getString("nome"));
                aluno.setLogin(rs.getString("login"));
                aluno.setSenha(rs.getString("senha"));

                //Cria objeto Endereço e o atribui ao aluno
                aluno.setEndereco(
                        new Endereco(
                            rs.getString("cidade"),
                            rs.getString("numero"),
                            rs.getString("rua")
                        )
                );

               aluno.setCurso(rs.getString("NomeCurso"));

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
