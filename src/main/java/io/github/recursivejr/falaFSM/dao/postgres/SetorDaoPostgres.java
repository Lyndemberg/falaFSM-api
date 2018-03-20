package io.github.recursivejr.falaFSM.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.github.recursivejr.falaFSM.dao.ElementoDao;
import io.github.recursivejr.falaFSM.dao.Interface.SetorDaoInterface;
import io.github.recursivejr.falaFSM.models.Setor;

public class SetorDaoPostgres extends ElementoDao implements SetorDaoInterface {

    public SetorDaoPostgres() throws SQLException, ClassNotFoundException {
        super();
    }
    
    @Override
    public boolean adicionar(Setor setor) {
        String sql = "INSERT INTO Setor(nome) VALUES (?);";
        try {
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, setor.getNome());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remover(Setor setor) {
        String sql = "DELETE FROM EnquetesSetor WHERE nomeSetor ILIKE " + setor.getNome() + "; " +
        				"DELETE FROM Setor WHERE nome ILIKE " + setor.getNome() + ";";
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
    public List<Setor> listar() {
        List<Setor> setores = new ArrayList<>();
        String sql = "SELECT * FROM Setor;";
        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
            	Setor setor = new Setor();
            	setor.setNome(rs.getString("nome"));

//            	NAO HA NECESSIDADE DE RETORNAR AS ENQUETES POR ENQUANTO
//            	EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
//            	setor.setEnquetes(enqueteDao.enquetesPorSetor(setor.getNome(), null));
                
            	setores.add(setor);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return setores;
    }

    @Override
    public Setor buscar(String nome) {
        String sql = "SELECT * FROM Setor WHERE nome ILIKE " + nome + ";";
        Setor setor = null;
        try {
            Statement stmt = getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                setor = new Setor();
                setor.setNome(rs.getString("nome"));
                
                EnqueteDaoPostgres enqueteDao = new EnqueteDaoPostgres();
            	setor.setEnquetes(enqueteDao.enquetesPorSetor(setor.getNome(), null));
            }
            stmt.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return setor;
    }

}
