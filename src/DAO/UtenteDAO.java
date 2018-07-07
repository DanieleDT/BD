package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtenteDAO {
	public static boolean login(String userid, String password) {
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
			stmt = connection.prepareStatement("SELECT * FROM utente WHERE userid = ? AND password = ?");
			stmt.setString(1, userid);
			stmt.setString(2, password);
			resultSet = stmt.executeQuery();

			result = (resultSet.next());
			
			connection.close();
			resultSet.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean getType(String userid) {
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
			stmt = connection.prepareStatement("SELECT tipo FROM utente WHERE userid = ?");
			stmt.setString(1, userid);
			resultSet = stmt.executeQuery();

			resultSet.next();
			result = resultSet.getBoolean(1);
			
			connection.close();
			resultSet.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean existUtente(String id) {
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
			stmt = connection.prepareStatement("SELECT * FROM utente WHERE userid = ?");
			stmt.setString(1, id);
			resultSet = stmt.executeQuery();

			result = (resultSet.next());

			resultSet.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public static void insertUtente(String username, String nome, String cognome, String password, String email, Boolean tipo) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.prepareStatement(
					"INSERT INTO utente(nome, cognome, userid, password, tipo, email) VALUES (?,?,?,?,?,?);");
			stmt.setString(1, nome);
			stmt.setString(2, cognome);
			stmt.setString(3, username);
			stmt.setString(4, password);
			stmt.setBoolean(5, tipo);
			stmt.setString(6, email);
			
			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
