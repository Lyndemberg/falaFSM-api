package io.github.recursivejr.discenteVivo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.github.recursivejr.discenteVivo.models.Administrador;
import io.github.recursivejr.discenteVivo.models.Endereco;
import io.github.recursivejr.discenteVivo.resources.Encryption;

public class AdministradorDaoPostgres extends ElementoDao implements AdministradorDaoInterface{

    public AdministradorDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Administrador administrador) {
        String sql = "INSERT INTO Administrador(EMAIL,NOME,LOGIN,SENHA,CIDADE,RUA,NUMERO) VALUES (?,?,?,?,?,?,?);";
        
        return setAdmin(sql, administrador);
    }

    @Override
    public boolean remover(Administrador administrador) {
        String sql = "DELETE FROM Administrador WHERE email ILIKE '" + administrador.getEmail()+ "';";
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
    public List<Administrador> listar() {
        String sql = "SELECT * FROM Administrador";

        return getAdmins(sql, null);
    }

    @Override
    public Administrador buscar(String email) {
        String sql = "SELECT * FROM Administrador WHERE email ILIKE ?;";

        List<Administrador> administradores = getAdmins(sql, email);

        if (administradores.isEmpty())
            return null;
        else
            return administradores.get(0);
    }

    @Override
    public boolean atualizar(Administrador administrador) {
        String sql = "UPDATE Administrador SET Email = ?, Nome = ?,Login = ?,Senha = ?,Cidade = ?,Rua= ?,Numero = ? "
        + "WHERE Email ILIKE ?;";

        return setAdmin(sql, administrador);
    }

    @Override
    public Administrador login(String login, String senha) throws Exception {

        Administrador admin = null;
    	
    	String sql = "SELECT Email,Senha FROM Administrador WHERE Login ILIKE ?;";
    	PreparedStatement stmt;
		stmt = getConexao().prepareStatement(sql);

		stmt.setString(1, login);

		ResultSet rs = stmt.executeQuery();
			
		if(!rs.next())
		    throw new Exception("Credenciais Inválidas");


		if(Encryption.checkPassword(senha, rs.getString("Senha")))
		    admin = buscar(rs.getString("Email"));
        else
            throw new Exception("Credenciais Inválidas");

        stmt.close();
		return admin;
    }

    private boolean setAdmin(String sql, Administrador administrador) {
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setString(1, administrador.getEmail());
            stmt.setString(2, administrador.getNome());
            stmt.setString(3, administrador.getLogin());
            stmt.setString(4, Encryption.encrypt(administrador.getSenha()));//Senha Criptografada pelo BCrypt
            stmt.setString(5, administrador.getEndereco().getCidade());
            stmt.setString(6, administrador.getEndereco().getRua());
            stmt.setString(7, administrador.getEndereco().getNumero());

            if (sql.contains("ILIKE"))
                stmt.setString(8, administrador.getEmail());

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private List<Administrador> getAdmins(String sql, String param) {
        List<Administrador> administradores = new ArrayList<>();

        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);

            if(param != null)
                stmt.setString(1, param);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Administrador administrador = new Administrador();
                administrador.setEmail(rs.getString("email"));
                administrador.setNome(rs.getString("nome"));
                administrador.setLogin(rs.getString("login"));
                administrador.setSenha(rs.getString("senha"));

                //Cria objeto Endereço e o atribui ao Administrador
                administrador.setEndereco(
                        new Endereco(
                                rs.getString("cidade"),
                                rs.getString("numero"),
                                rs.getString("rua")
                        )
                );

                administradores.add(administrador);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return administradores;
    }

}
