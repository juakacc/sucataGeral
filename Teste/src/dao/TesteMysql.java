package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class TesteMysql {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
		
		// Fecha automaticamente a conexão
		try (Connection conexao = new FactoryConnection().getConnection()) { 
			System.out.println("A conexão foi aberta...");
			
			String sql = "insert into contatos " +
						"(nome, email, endereco, dataNascimento) " +
						"values (?, ?, ?, ?)";
			
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			stmt.setString(1, "Joaquim");
			stmt.setString(2, "detonadorjuaca...");
			stmt.setString(3, "Rua de cima");
			stmt.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
			
			stmt.execute();
			System.out.println("Foi gravado ...");
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}
