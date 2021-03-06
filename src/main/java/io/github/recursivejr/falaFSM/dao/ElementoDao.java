package io.github.recursivejr.falaFSM.dao;

import io.github.recursivejr.falaFSM.factories.Conexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ElementoDao {

    private final Connection conn;

    public ElementoDao() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    public Connection getConexao() {
        return conn;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ElementoDao.class.getName()).log(Level.SEVERE, "ERRO AO FECHAR CONEXAO.", ex);
            finalize();
        }
    }
}
