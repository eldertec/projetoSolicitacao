package br.edu.faculdadedelta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
	public static Connection getConnection() {
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			return DriverManager.getConnection("jdbc:postgresql://localhost/db_projetoN2", "postgres", "toor");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) throws SQLException {
		if(rs != null)
			rs.close();
		if(stmt != null)
			stmt.close();
		if(con != null)
			con.close();
	}
}
