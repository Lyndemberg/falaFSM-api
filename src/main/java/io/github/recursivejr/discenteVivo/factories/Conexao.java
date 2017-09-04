package io.github.recursivejr.discenteVivo.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:postgresql://ec2-54-163-233-201.compute-1.amazonaws.com:5432/dc556k0psngs08";
    private static final String usuario = "lxujnjxkxuivbs";
    private static final String senha = "3f6ea33fb42d35767312fccacc67d211070a7dbcb0b9a698bd0885e8cedbdd2f";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, usuario, senha);
    }

}
