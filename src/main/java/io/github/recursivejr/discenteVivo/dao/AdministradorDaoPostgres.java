package io.github.recursivejr.discenteVivo.dao;

import java.sql.*;
import java.util.List;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.resources.Encryption;

public class AdministradorDaoPostgres implements AdministradorDaoInterface{
    private final Connection conn;

    public AdministradorDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }
    
    @Override
    public boolean adicionar(Administrador administrador) {
        String sql = "INSERT INTO Administrador(EMAIL,NOME,LOGIN,SENHA,CIDADE,RUA,NUMERO) VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, administrador.getEmail());
            stmt.setString(2, administrador.getNome());
            stmt.setString(3, administrador.getLogin());
            stmt.setString(4, Encryption.encrypt(administrador.getSenha()));//Senha Criptografada em md5
            stmt.setString(5, administrador.getEndereco().getCidade());
            stmt.setString(6, administrador.getEndereco().getRua());
            stmt.setString(7, administrador.getEndereco().getNumero());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Administrador administrador) {
        String sql = "DELETE FROM Administrador WHERE email ILIKE " + administrador.getEmail()+ ";";
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
    public List<Administrador> listar() {
        List<Administrador> administradores = null;
        String sql = "SELECT * FROM Administrador";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Administrador administrador = new Administrador();
                administrador.setEmail(rs.getString("email"));
                administrador.setNome(rs.getString("nome"));
                administrador.setLogin(rs.getString("login"));
                administrador.setSenha(rs.getString("senha"));
                administrador.getEndereco().setRua(rs.getString("rua"));
                administrador.getEndereco().setCidade(rs.getString("cidade"));
                administrador.getEndereco().setNumero(rs.getString("numero"));
                
                administradores.add(administrador);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return administradores;
    }

    @Override
    public Administrador buscar(String email) {
        String sql = "SELECT * FROM Administrador WHERE email ILIKE" + email + ";";
        Administrador administrador = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                administrador = new Administrador();
                administrador.setEmail(rs.getString("email"));
                administrador.setNome(rs.getString("nome"));
                administrador.setLogin(rs.getString("login"));
                administrador.setSenha(rs.getString("senha"));
                administrador.getEndereco().setCidade(rs.getString("cidade"));
                administrador.getEndereco().setRua(rs.getString("rua"));
                administrador.getEndereco().setNumero(rs.getString("numero"));
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return administrador;
    }

}
