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
}
