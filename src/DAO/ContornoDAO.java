package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContornoDAO {
	public static boolean existContorno(int id, double lon, double lat, Connection conn) {
		PreparedStatement stmt = null;
		boolean result = false;
		ResultSet resultSet = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM poscontorno WHERE idfil = ? AND latitudine = ? AND longitudine = ?");
			stmt.setInt(1, id);
			stmt.setDouble(2, lat);
			stmt.setDouble(3, lon);
			resultSet = stmt.executeQuery();
			
			result = (resultSet.next());
			
			resultSet.close();
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void insertPuntoContorno(int id, double lon, double lat, Connection conn) {
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement("INSERT INTO poscontorno(latitudine, longitudine, idfil) VALUES (?,?,?);");
				stmt.setDouble(1, lat);
				stmt.setDouble(2, lon);
				stmt.setInt(3, id);
				
				stmt.executeUpdate();
				
				stmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
