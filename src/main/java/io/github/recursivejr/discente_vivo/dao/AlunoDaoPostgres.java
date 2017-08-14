package io.github.recursivejr.discente_vivo.dao;

import io.github.recursivejr.discente_vivo.factories.Conexao;
import io.github.recursivejr.discente_vivo.models.Aluno;
import io.github.recursivejr.discente_vivo.resources.Encryption;

import java.sql.*;
import java.util.List;

public class AlunoDaoPostgres implements AlunoDaoInterface{
    private final Connection conn;

    public AlunoDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }
    
    @Override
    public boolean adicionar(Aluno aluno) {
        String sql = "INSERT INTO Aluno(MATRICULA, EMAIL,NOME,LOGIN,SENHA,CIDADE,RUA,NUMERO) VALUES (?,?,?,?,?,?,?,?)";
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
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Aluno aluno) {
        String sql = "DELETE FROM Aluno WHERE matricula ILIKE " + aluno.getMatricula()+ ";";
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
        List<Aluno> alunos = null;
        String sql = "SELECT * FROM Aluno";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getNString("matricula"));
                aluno.setEmail(rs.getString("email"));
                aluno.setNome(rs.getString("nome"));
                aluno.setLogin(rs.getString("login"));
                aluno.setSenha(rs.getString("senha"));
                aluno.getEndereco().setRua(rs.getString("rua"));
                aluno.getEndereco().setCidade(rs.getString("cidade"));
                aluno.getEndereco().setNumero(rs.getString("numero"));
                
                alunos.add(aluno);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return alunos;
    }

    @Override
    public Aluno buscar(String email) {
        String sql = "SELECT * FROM Aluno WHERE email ILIKE" + email;
        Aluno aluno = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setEmail(rs.getString("email"));
                aluno.setNome(rs.getString("nome"));
                aluno.setLogin(rs.getString("login"));
                aluno.setSenha(rs.getString("senha"));
                aluno.getEndereco().setCidade(rs.getString("cidade"));
                aluno.getEndereco().setRua(rs.getString("rua"));
                aluno.getEndereco().setNumero(rs.getString("numero"));
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aluno;
    }

}
