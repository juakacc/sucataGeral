package br.com.geral.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnection {

	public Connection getConnection() {
		
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost/sucata_bd", "root", "Aqu1Est0u+1D1a");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}

}
