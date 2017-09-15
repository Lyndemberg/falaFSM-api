package io.github.recursivejr.discenteVivo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.models.Endereco;
import io.github.recursivejr.discenteVivo.resources.Encryption;

public class AlunoDaoPostgres extends ElementoDao implements AlunoDaoInterface{

    public AlunoDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
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
    public Aluno login(String login, String senha) throws Exception {

        Aluno aluno = null;
        senha = Encryption.encrypt(senha);
    	
    	String sql = "SELECT Matricula FROM Aluno WHERE Login ILIKE ? AND SENHA ILIKE ?;";
    	PreparedStatement stmt;
		try {
			stmt = getConexao().prepareStatement(sql);

			stmt.setString(1, login);
			stmt.setString(2, senha);

			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()) {
				throw new Exception("Credenciais Inválidas");
			}

			aluno = buscar(rs.getString("matricula"));
			
			stmt.close();
			
		} catch (SQLException ex) {
			Logger.getLogger(ex.getMessage());
			throw ex;
		}
		return aluno;
    }

    private boolean setAluno(String sql, Aluno aluno) {
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
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
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private List<Aluno> getAlunos(String sql, String param) {
        List<Aluno> alunos = new ArrayList<>();

        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

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
                            rs.getString("rua"),
                            rs.getString("numero")
                        )
                );

               aluno.setCurso(rs.getString("NomeCurso"));

                alunos.add(aluno);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return alunos;
    }

}
