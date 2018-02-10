package io.github.recursivejr.discenteVivo.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.AlunoDaoInterface;
import io.github.recursivejr.discenteVivo.models.Aluno;
import io.github.recursivejr.discenteVivo.resources.Encryption;

public class AlunoDaoPostgres extends ElementoDao implements AlunoDaoInterface {

    public AlunoDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Aluno aluno) {
        String sql = "INSERT INTO Aluno(Matricula, Email, Nome, Login, Senha, NomeCurso) " +
                "VALUES ('" + aluno.getMatricula() + "',?,?,?,?,?);";

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
        String sql = "UPDATE Aluno SET Email = ?, Nome = ?, Login = ?, Senha = ?" +
                " WHERE Matricula ILIKE '" + aluno.getMatricula() + "';";

        aluno.setCurso(null);
        return setAluno(sql, aluno);
    }

    @Override
    public Aluno login(String login, String senha) throws Exception {

        Aluno aluno = null;
    	
    	String sql = "SELECT Matricula,Senha FROM Aluno WHERE Login ILIKE ?;";
    	PreparedStatement stmt;

    	stmt = getConexao().prepareStatement(sql);

		stmt.setString(1, login);

		ResultSet rs = stmt.executeQuery();
			
		if(!rs.next())
		    throw new Exception("Credenciais Inválidas");

		if(Encryption.checkPassword(senha,rs.getString("Senha")))
		    aluno = buscar(rs.getString("matricula"));
		else
            throw new Exception("Credenciais Inválidas");

		stmt.close();
		return aluno;
    }

    private boolean setAluno(String sql, Aluno aluno) {
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, aluno.getEmail());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getLogin());
            stmt.setString(4, Encryption.encrypt(aluno.getSenha()));//Senha Criptografada em md5

            //Caso o objeto aluno nao tenha curso entao o mesmo nao sera salvo
                //Pois refere-se ao metodo Atualizar e um aluno nao pode Atualizar seu Curso
            if (aluno.getCurso() != null)
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
