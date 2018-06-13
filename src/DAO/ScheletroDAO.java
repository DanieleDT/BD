package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entity.posContorno;
import entity.posScheletro;

public class ScheletroDAO {
	// RF 11
	public ArrayList<Double> distanzaSegmentoContorno(int idSeg) {
		// nel primo elemento si trova la distanza del punto minimo, nel secondo quella
		// del massimo
		ArrayList<Double> distanze = new ArrayList<Double>();
		ArrayList<posContorno> contorno;
		double distanza = 0;
		double distMin = Integer.MAX_VALUE;
		double distMax = Integer.MAX_VALUE;
		posScheletro min = null;
		posScheletro max = null;
		int idFil = -1;
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		double latitudine;
		double longitudine;
		double flusso;
		int numeroProgressivo;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// trovo l'ID del filamento a cui appartiene il segmento
			String sql = "SELECT idFilamento FROM Scheletro WHERE ID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idSeg);
			resultSet = stmt.executeQuery();
			resultSet.next();
			idFil = resultSet.getInt("idFilamento");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// carico le posizioni del contorno
		contorno = StelleDAO.posContornoById(idFil);
		try {
			// ottiene il punto del segmento massimo
			String sql = "SELECT * FROM PosScheletro WHERE idScheletro = ? AND numeroProgressivo = (SELECT max(numeroProgressivo) FROM PosScheletro WHERE idScheletro = ?) ";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idSeg);
			stmt.setInt(2, idSeg);
			resultSet = stmt.executeQuery();
			resultSet.next();
			latitudine = resultSet.getDouble("Latitudine");
			longitudine = resultSet.getDouble("Longitudine");
			flusso = resultSet.getDouble("flusso");
			numeroProgressivo = resultSet.getInt("numeroProgressivo");
			max = new posScheletro(latitudine, longitudine, flusso, numeroProgressivo, idSeg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// ottiene il punto del segmento minimo
			String sql = "SELECT * FROM PosScheletro WHERE idScheletro = ? AND numeroProgressivo = (SELECT min(numeroProgressivo) FROM PosScheletro WHERE idScheletro = ?) ";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idSeg);
			stmt.setInt(2, idSeg);
			resultSet = stmt.executeQuery();
			resultSet.next();
			latitudine = resultSet.getDouble("Latitudine");
			longitudine = resultSet.getDouble("Longitudine");
			flusso = resultSet.getDouble("flusso");
			numeroProgressivo = resultSet.getInt("numeroProgressivo");
			min = new posScheletro(latitudine, longitudine, flusso, numeroProgressivo, idSeg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// distanze euclidee per il massimo
		for (int i = 0; i <= contorno.size() - 1; i++) {
			distanza = Math.sqrt(Math.pow((max.getLatitudine() - contorno.get(i).getLatitudine()), 2.0)
					+ Math.pow((max.getLongitudine() - contorno.get(i).getLongitudine()), 2.0));
			if (distanza < distMax) {
				distMax = distanza;
			}
		}
		// distanze euclidee per il minimo
		for (int i = 0; i <= contorno.size() - 1; i++) {
			distanza = Math.sqrt(Math.pow((min.getLatitudine() - contorno.get(i).getLatitudine()), 2.0)
					+ Math.pow((min.getLongitudine() - contorno.get(i).getLongitudine()), 2.0));
			if (distanza < distMin) {
				distMin = distanza;
			}
		}
		distanze.add(distMin);
		distanze.add(distMax);
		return distanze;
	}

	public static boolean existScheletro(int id, Connection conn) {
		PreparedStatement stmt = null;
		boolean result = false;
		ResultSet resultSet = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM scheletro WHERE id = ?");
			stmt.setInt(1, id);
			resultSet = stmt.executeQuery();

			result = (resultSet.next());

			resultSet.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void insertScheletro(int id, int idFilamento, Boolean tipo, Connection conn) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO scheletro(id, idfilamento, tipo) VALUES (?,?,?);");
			stmt.setInt(1, id);
			stmt.setInt(2, idFilamento);
			stmt.setBoolean(3, tipo);

			stmt.executeUpdate();

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateScheletro(int id, int idFilamento, Boolean tipo, Connection conn) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE scheletro SET idfilamento = ?, tipo = ? WHERE id = ?");
			stmt.setInt(3, id);
			stmt.setInt(1, idFilamento);
			stmt.setBoolean(2, tipo);

			stmt.executeUpdate();

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean existPuntoScheletro(double lat, double lon, Connection conn) {
		PreparedStatement stmt = null;
		boolean result = false;
		ResultSet resultSet = null;
		// double latitudine, longitudine;
		try {
			stmt = conn.prepareStatement("SELECT * FROM posscheletro WHERE latitudine = ? AND longitudine = ?");
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			resultSet = stmt.executeQuery();

			result = (resultSet.next());

			resultSet.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void insertPuntoScheletro(double lon, double lat, double flusso, int numProg, int idScheletro,
			Connection conn) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(
					"INSERT INTO posscheletro(latitudine, longitudine, flusso, numeroprogressivo, idscheletro) VALUES (?,?,?,?,?);");
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setDouble(3, flusso);
			stmt.setInt(4, numProg);
			stmt.setInt(5, idScheletro);

			stmt.executeUpdate();

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ArrayList<Double> dist;
		ScheletroDAO dao = new ScheletroDAO();
		System.out.println("start");
		dist = dao.distanzaSegmentoContorno(3);
		System.out.println("finish");
		System.out.println(dist.get(0));
		System.out.println(dist.get(1));
	}

}
