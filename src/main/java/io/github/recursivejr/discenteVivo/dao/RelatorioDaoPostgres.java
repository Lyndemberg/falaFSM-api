package io.github.recursivejr.discenteVivo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.github.recursivejr.discenteVivo.factories.Conexao;
import io.github.recursivejr.discenteVivo.models.Relatorio;

public class RelatorioDaoPostgres implements RelatorioDaoInterface {
    private final Connection conn;

    public RelatorioDaoPostgres() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    @Override    
    public List<Relatorio> gerarRelatorio(String nome) {
    	List<Relatorio> relatorio = new ArrayList<>();
    	
    	String sql = "SELECT Id FROM Enquete WHERE Nome ILIKE " + nome + ";";    	
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		if(rs.next()) {
    			String idEnquete = rs.getString("id");
    			Statement internalStmt = conn.createStatement();
    			
    			//Recuperando as Respostas
    			 String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = " + idEnquete + ";";
                 ResultSet rsRespostas = internalStmt.executeQuery(sqlRespostas); 
                 
                 while(rsRespostas.next()) {
                	 //Se não houver nada no relatorio adiciona uma nova opção com 1 voto
                	 if(relatorio.isEmpty()) {
                    	 relatorio.add(
                    			 new Relatorio(
                    					 rsRespostas.getString("Resposta"), 1));
                     } else {
                    	 //Se já há alguma opção verifica se a nova resposta bate com essa opção
                    	 //cria variavel opção 
                    	 String opcao = rsRespostas.getString("Resposta");
                    	 boolean aux = false;
                    	 //Percorre todo o relatorio atras desta opção, se achar transforma variavel aux em true
                    	 for(Relatorio r: relatorio) {
                    		 if(r.getOpcao().equals(opcao)) {
                    			 r.setVotos(r.getVotos() + 1);
                    			 aux = true;
                    		 }
                    	 }
                    	 //Se perroceu todo o relatorio e nao achou a opção, ou seja, variavel aux == false 
                    	 //entao cria-se uma nova opção no relatorio com 1 voto
                    	 if(aux == false) 
                    		relatorio.add(new Relatorio(opcao, 1));
                     }
                 }
                 internalStmt.close();                 
    		}
    		stmt.close();
    		conn.close();
    		
    		return relatorio;
		} catch (SQLException ex) {
			Logger.getLogger(ex.getMessage());
		}
    	return null;
    }
    
    @Override    
    public List<Relatorio> gerarRelatorio(int id) {
    	List<Relatorio> relatorio = new ArrayList<>();
    	
    	String sql = "SELECT Id FROM Enquete WHERE idEnquete = " + id + ";";    	
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		if(rs.next()) {
    			Statement internalStmt = conn.createStatement();
    			
    			//Recuperando as Respostas
    			 String sqlRespostas = "SELECT * FROM RespondeEnquete WHERE IDEnquete = " + id + ";";
                 ResultSet rsRespostas = internalStmt.executeQuery(sqlRespostas); 
                 
                 while(rsRespostas.next()) {
                	 //Se não houver nada no relatorio adiciona uma nova opção com 1 voto
                	 if(relatorio.isEmpty()) {
                    	 relatorio.add(
                    			 new Relatorio(
                    					 rsRespostas.getString("Resposta"), 1));
                     } else {
                    	 //Se já há alguma opção verifica se a nova resposta bate com essa opção
                    	 //cria variavel opção 
                    	 String opcao = rsRespostas.getString("Resposta");
                    	 boolean aux = false;
                    	 //Percorre todo o relatorio atras desta opção, se achar transforma variavel aux em true
                    	 for(Relatorio r: relatorio) {
                    		 if(r.getOpcao().equals(opcao)) {
                    			 r.setVotos(r.getVotos() + 1);
                    			 aux = true;
                    		 }
                    	 }
                    	 //Se perroceu todo o relatorio e nao achou a opção, ou seja, variavel aux == false 
                    	 //entao cria-se uma nova opção no relatorio com 1 voto
                    	 if(aux == false) 
                    		relatorio.add(new Relatorio(opcao, 1));
                     }
                 }
                 internalStmt.close();                 
    		}
    		stmt.close();
    		conn.close();
    		
    		return relatorio;
		} catch (SQLException ex) {
			Logger.getLogger(ex.getMessage());
		}
    	return null;
    	
    }

}
