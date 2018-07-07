package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.BeanFilNumSeg;
import bean.BeanFilamentiConEll;
import bean.BeanFilamento;
import bean.Centroide;
import bean.Estensione;
import controller.ControllerFilamenti;
import controller.ControllerStelle;
import entity.Filamento;

public class FilamentiDAO {

	// RF 5 Designazione
	public BeanFilamento InformazioniFilamentoDesignazione(String nome) {
		Centroide centroide;
		Estensione estensione;
		int numSeg;
		BeanFilamento beanFilamento;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		centroide = getCentroideDesignazione(nome, connection);
		estensione = getEstensioneDesignazione(nome, connection);
		numSeg = getNumSegDesignazione(nome, connection);
		beanFilamento = new BeanFilamento(numSeg, centroide.getLat(), centroide.getLon(), estensione.getLatitudine(),
				estensione.getLongitudine());
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beanFilamento;
	}

	// RF 5 ID
	public BeanFilamento InformazioniFilamentoId(int id) {
		Centroide centroide;
		Estensione estensione;
		int numSeg;
		BeanFilamento beanFilamento;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		centroide = getCentroideId(id, connection);
		estensione = getEstensioneId(id, connection);
		numSeg = getNumSegId(id, connection);
		beanFilamento = new BeanFilamento(numSeg, centroide.getLat(), centroide.getLon(), estensione.getLatitudine(),
				estensione.getLongitudine());
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beanFilamento;
	}

	// RF 6
	public BeanFilamentiConEll SearchFilConEll(int brillanza, double Emax, double Emin) {
		BeanFilamentiConEll bean;
		ArrayList<Filamento> filamenti;
		int totale;
		int parziale;
		filamenti = ricercaContEll(brillanza, Emin, Emax);
		totale = countFilamenti();
		parziale = countFilamentiContEll(brillanza, Emin, Emax);
		bean = new BeanFilamentiConEll(filamenti, totale, parziale);
		return bean;
	}

	// RF 7
	public ArrayList<Integer> FilamentiNumSeg(int min, int max) {
		// ArrayList[0] == #filamenti trovati
		// ArrayList[1...N] == ID filamenti trovati
		// -1 per vincolo non necessario
		ArrayList<Integer> idFil = new ArrayList<Integer>();
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		int lastID = -1;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (min == -1) {
			try {
				String sql = "SELECT idFilamento FROM Scheletro GROUP BY idFilamento HAVING count(ID) <= ?";
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, max);
				resultSet = stmt.executeQuery();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (max == -1) {
			try {
				String sql = "SELECT idFIlamento FROM Scheletro GROUP BY idFilamento HAVING count(ID) >= ?";
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, min);
				resultSet = stmt.executeQuery();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				String sql = "SELECT idFilamento FROM Scheletro GROUP BY idFilamento HAVING count(ID)>= ? AND count(ID) <= ?";
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, min);
				stmt.setInt(2, max);
				resultSet = stmt.executeQuery();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			while (resultSet.next()) {
				if (lastID == -1) {
					lastID = resultSet.getInt("idfilamento");
					idFil.add(lastID);
				} else {
					if (lastID != resultSet.getInt("idFilamento")) {
						lastID = resultSet.getInt("idFilamento");
						idFil.add(lastID);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idFil;
	}

	// RF 8 quadrato
	public ArrayList<Integer> FilamentoRegioneQuadrato(double latCentro, double lonCentro, double lato) {
		ArrayList<Integer> filamenti = new ArrayList<Integer>();
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		double semiLato = lato / 2;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT distinct idFil FROM poscontorno WHERE Latitudine >= (? -? ) AND Latitudine <= (?+?) AND Longitudine >= (?-?) AND Longitudine <= (?+?) GROUP BY idFil";
			stmt = connection.prepareStatement(sql);
			stmt.setDouble(2, semiLato);
			stmt.setDouble(4, semiLato);
			stmt.setDouble(6, semiLato);
			stmt.setDouble(8, semiLato);
			stmt.setDouble(1, latCentro);
			stmt.setDouble(3, latCentro);
			stmt.setDouble(5, lonCentro);
			stmt.setDouble(7, lonCentro);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				filamenti.add(resultSet.getInt("idFil"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filamenti;
	}

	// RF 8 cerchio
	public ArrayList<Integer> FilamentoRegioneCerchio(double latCentro, double lonCentro, double raggio) {
		ArrayList<Integer> filamenti = new ArrayList<Integer>();
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT distinct idFil FROM poscontorno WHERE SQRT(ABS(POWER((Latitudine-?),2) + POWER((Longitudine - ?), 2))) <= ? GROUP BY idfil";
			stmt = connection.prepareStatement(sql);
			stmt.setDouble(1, latCentro);
			stmt.setDouble(2, lonCentro);
			stmt.setDouble(3, raggio);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				filamenti.add(resultSet.getInt("idFil"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filamenti;
	}

	public static boolean existFilamento(int id, Connection conn) {
		PreparedStatement stmt = null;
		boolean result = false;
		ResultSet resultSet = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM filamento WHERE id = ?");
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

	public static boolean existFilamentoNoConn(String nome) {
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
			stmt = connection.prepareStatement("SELECT * FROM filamento WHERE nome = ?");
			stmt.setString(1, nome);
			resultSet = stmt.executeQuery();

			result = (resultSet.next());

			resultSet.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean existFilamentoNoConn(int id) {
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
			stmt = connection.prepareStatement("SELECT * FROM filamento WHERE id = ?");
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

	public static void insertFilamento(int id, String nome, double flusso, double contrasto, double densita,
			double temperatura, double ellitticita, String strumento, String satellite, Connection conn) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(
					"INSERT INTO filamento(id, nome, flussototale, contrasto, densitamedia, tempmedia, ellitticita, nomstrumento, nomsatellite) VALUES (?,?,?,?,?,?,?,?,?);");
			stmt.setInt(1, id);
			stmt.setString(2, nome);
			stmt.setDouble(3, flusso);
			stmt.setDouble(4, contrasto);
			stmt.setDouble(5, densita);
			stmt.setDouble(6, temperatura);
			stmt.setDouble(7, ellitticita);
			stmt.setString(8, strumento);
			stmt.setString(9, satellite);

			stmt.executeUpdate();

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateFilamento(int id, String nome, double flusso, double contrasto, double densita,
			double temperatura, double ellitticita, String strumento, String satellite, Connection conn) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(
					"UPDATE filamento SET nome = ?, flussototale = ?, contrasto = ?, densitamedia = ?, tempmedia = ?, ellitticita = ?, nomstrumento = ?, nomsatellite = ? WHERE id = ?");
			stmt.setInt(9, id);
			stmt.setString(1, nome);
			stmt.setDouble(2, flusso);
			stmt.setDouble(3, contrasto);
			stmt.setDouble(4, densita);
			stmt.setDouble(5, temperatura);
			stmt.setDouble(6, ellitticita);
			stmt.setString(7, strumento);
			stmt.setString(8, satellite);

			stmt.executeUpdate();

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Centroide getCentroideId(int id, Connection connection) {
		double longitudine;
		double latitudine;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Centroide centroide = null;
		try {
			String sql = "SELECT avg(latitudine) as lat, avg(longitudine) as lon FROM poscontorno WHERE idfil = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			resultSet = stmt.executeQuery();
			resultSet.next();

			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			centroide = new Centroide(latitudine, longitudine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return centroide;
	}

	public Centroide getCentroideDesignazione(String nome, Connection connection) {
		double longitudine;
		double latitudine;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Centroide centroide = null;
		try {
			String sql = "SELECT avg(latitudine) as lat, avg(longitudine) as lon FROM poscontorno WHERE idfil = (SELECT id FROM Filamento WHERE nome = ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);

			resultSet = stmt.executeQuery();
			resultSet.next();

			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			centroide = new Centroide(latitudine, longitudine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return centroide;
	}

	public Estensione getEstensioneId(int id, Connection connection) {
		double latitudine;
		double longitudine;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Estensione estensione = null;
		try {
			String sql = "SELECT max(longitudine) - min(longitudine) as lon, max(latitudine) - min(latitudine) as lat FROM poscontorno WHERE idfil = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			resultSet = stmt.executeQuery();

			resultSet.next();

			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			estensione = new Estensione(latitudine, longitudine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return estensione;
	}

	public Estensione getEstensioneDesignazione(String nome, Connection connection) {
		double latitudine;
		double longitudine;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Estensione estensione = null;
		try {
			String sql = "SELECT max(longitudine) - min(longitudine) as lon, max(latitudine) - min(latitudine) as lat FROM poscontorno WHERE idfil = (SELECT id FROM Filamento WHERE nome = ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);

			resultSet = stmt.executeQuery();
			resultSet.next();

			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			estensione = new Estensione(latitudine, longitudine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return estensione;
	}

	public int getNumSegId(int id, Connection connection) {
		int numSeg = -1;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT count(id) AS num FROM scheletro WHERE idfilamento = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			resultSet = stmt.executeQuery();
			resultSet.next();
			numSeg = resultSet.getInt("num");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numSeg;
	}

	public int getNumSegDesignazione(String nome, Connection connection) {
		int numSeg = -1;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT count(id) AS num FROM Scheletro WHERE idfilamento =(SELECT id FROM Filamento WHERE nome = ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			resultSet = stmt.executeQuery();
			resultSet.next();

			numSeg = resultSet.getInt("num");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numSeg;
	}

	public ArrayList<Filamento> ricercaContEll(int brillanza, double Emin, double Emax) {
		ArrayList<Filamento> filamenti = new ArrayList<Filamento>();
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int ID;
		String nome;
		double flusso;
		double contrasto;
		double densita;
		double temperatura;
		double ellitticita;
		String nomeStrumento;
		String nomeSatellite;
		Filamento fil;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT * FROM filamento WHERE contrasto >= (1+(?/100)) AND ? < ellitticita AND ellitticita < ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, brillanza);
			stmt.setDouble(3, Emin);
			stmt.setDouble(2, Emax);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				ID = resultSet.getInt("ID");
				nome = resultSet.getString("nome");
				flusso = resultSet.getDouble("flussototale");
				densita = resultSet.getDouble("densitamedia");
				temperatura = resultSet.getDouble("tempMedia");
				contrasto = resultSet.getDouble("contrasto");
				ellitticita = resultSet.getDouble("ellitticita");
				nomeStrumento = resultSet.getString("nomstrumento");
				nomeSatellite = resultSet.getString("nomsatellite");
				fil = new Filamento(ID, densita, temperatura, contrasto, nome, flusso, ellitticita, nomeStrumento,
						nomeSatellite);
				filamenti.add(fil);
			}
			resultSet.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filamenti;
	}

	public int countFilamenti() {
		int count = -1;
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT count(id) AS num FROM Filamento";
			stmt = connection.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			resultSet.next();
			count = resultSet.getInt("num");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int countFilamentiContEll(int brillanza, double Emin, double Emax) {
		int count = -1;
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT count(ID) as num FROM Filamento WHERE contrasto >= (1+(?/100)) AND ? < ellitticita AND ellitticita > ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, brillanza);
			stmt.setDouble(2, Emin);
			stmt.setDouble(3, Emax);
			resultSet = stmt.executeQuery();
			resultSet.next();
			count = resultSet.getInt("num");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<Filamento> loadFilamentiById(ArrayList<Integer> id) {
		ArrayList<Filamento> filamenti = new ArrayList<Filamento>();
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < id.size(); i++) {
				String sql = "SELECT * FROM filamento WHERE id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, id.get(i));
				resultSet = stmt.executeQuery();
				resultSet.next();
				String nome = resultSet.getString("nome");
				double flusso = resultSet.getDouble("flussototale");
				double contrasto = resultSet.getDouble("contrasto");
				double densitamedia = resultSet.getDouble("densitamedia");
				double ellitticita = resultSet.getDouble("ellitticita");
				double temperatura = resultSet.getDouble("tempmedia");
				String strumento = resultSet.getString("nomstrumento");
				String satellite = resultSet.getString("nomsatellite");
				Filamento filamento = new Filamento(id.get(i), densitamedia, temperatura, contrasto, nome, flusso,
						ellitticita, strumento, satellite);
				filamenti.add(filamento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filamenti;
	}

	public ArrayList<BeanFilNumSeg> filamentiNumSeg(ArrayList<Filamento> filamenti) {
		ArrayList<BeanFilNumSeg> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql:ProgettoDB", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < filamenti.size(); i++) {
				String sql = "SELECT count(id) AS numseg FROM scheletro WHERE idfilamento = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, filamenti.get(i).getID());
				resultSet = stmt.executeQuery();
				resultSet.next();
				int numSeg = resultSet.getInt("numseg");
				BeanFilNumSeg bean = new BeanFilNumSeg(filamenti.get(i), numSeg);
				result.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			resultSet.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
}