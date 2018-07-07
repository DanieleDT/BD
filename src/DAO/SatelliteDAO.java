package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;

public class SatelliteDAO {
	public static boolean existSatellite(String nome) {
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
			stmt = connection.prepareStatement("SELECT * FROM satellite WHERE nome = ?");
			stmt.setString(1, nome);
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
	
	public static void insertFilamento(String nome, String agenzia, LocalDate dataInizio, LocalDate dataFine) {
		PreparedStatement stmt = null;
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");

			stmt = connection.prepareStatement(
					"INSERT INTO satellite(nome, agenzia, primaosservazione, termineattivita) VALUES (?,?,?,?);");
			stmt.setString(1, nome);
			stmt.setString(2, agenzia);
			stmt.setDate(3, Date.valueOf(dataInizio));
			if(dataFine != null) {
				stmt.setDate(4, Date.valueOf(dataFine));
			}else {
				stmt.setDate(4, null);

			}

			stmt.executeUpdate();
			connection.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
