package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StrumentoDAO {
	public static boolean existStrumento(String nome, String satellite, Connection conn) {
		PreparedStatement stmt = null;
		boolean result = false;
		ResultSet resultSet = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM strumento WHERE nome = ? AND satellite = ?");
			stmt.setString(1, nome);
			stmt.setString(2, satellite);
			resultSet = stmt.executeQuery();
			
			result = (resultSet.next());
			
			resultSet.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean existStrumento(String nome, String satellite) {
		PreparedStatement stmt = null;
		boolean result = false;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt = connection.prepareStatement("SELECT * FROM strumento WHERE nome = ? AND satellite = ?");
			stmt.setString(1, nome);
			stmt.setString(2, satellite);
			resultSet = stmt.executeQuery();
			
			result = (resultSet.next());
			
			connection.close();
			resultSet.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
