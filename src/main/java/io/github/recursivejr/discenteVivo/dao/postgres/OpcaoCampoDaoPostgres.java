package io.github.recursivejr.discenteVivo.dao.postgres;

import io.github.recursivejr.discenteVivo.dao.ElementoDao;
import io.github.recursivejr.discenteVivo.dao.Interface.OpcaoDaoInterface;
import io.github.recursivejr.discenteVivo.models.Opcao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OpcaoCampoDaoPostgres extends ElementoDao implements OpcaoDaoInterface {

    public OpcaoCampoDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Opcao opcao) {
    	String sql = "INSERT INTO OpcoesCampo (IdCampo, Opcao) VALUES (?,?);";
        
        try {
        	PreparedStatement stmt = getConexao().prepareStatement(sql);

            stmt.setInt(1, opcao.getIdFK());
            stmt.setString(2, opcao.getOpcao());
            stmt.executeUpdate();
            
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Opcao opcao) {
    	String sql = "DELETE FROM OpcoesCampo WHERE Opcao ILIKE '" + opcao.getOpcao()
    	+ "' AND idCampo = '" + opcao.getIdFK() + "';";
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
    public List<Opcao> listar() {
        String sql = "SELECT * FROM OpcoesCampo";

        return getOpcoes(sql);
    }

    @Override
    public List<Opcao> listarPorChave(int idCampo) {
        String sql = "SELECT * FROM OpcoesCampo WHERE idCampo= '" + idCampo + "';";

        return getOpcoes(sql);
    }

    private List<Opcao> getOpcoes(String sql) {
        List<Opcao> opcoes = new ArrayList<>();

        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Opcao opcao = new Opcao();
                opcao.setIdFK(rs.getInt("idCampo"));
                opcao.setOpcao(rs.getString("Opcao"));

                opcoes.add(opcao);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return opcoes;
    }
}
