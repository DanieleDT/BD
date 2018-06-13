package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.BeanFilamentiConEll;
import bean.BeanFilamento;
import bean.Centroide;
import bean.Estensione;
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
		int globalCount = 0;
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
					globalCount++;
					lastID = resultSet.getInt("idfilamento");
					idFil.add(lastID);
				} else {
					if (lastID != resultSet.getInt("idFilamento")) {
						globalCount++;
						lastID = resultSet.getInt("idFilamento");
						idFil.add(lastID);
					}
				}
			}
			idFil.add(0, globalCount);
		} catch (Exception e) {
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
			String sql = "SELECT distinct idFil FROM poscontorno WHERE SQRT(ABS(POWER((Latitudine-?),2) - POWER((Longitudine - ?), 2))) <= ? GROUP BY idfil";
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
		return count;
	}

	public static void main(String[] args) {
		 FilamentiDAO dao = new FilamentiDAO(); 
		 /* BeanFilamento bean, bean1;
		 * System.out.println("start"); bean =
		 * dao.InformazioniFilamentoDesignazione("SDC10.014-0.818"); bean1 =
		 * dao.InformazioniFilamentoId(383); System.out.println("finish");
		 * System.out.println(bean.getEstensioneLat());
		 * System.out.println(bean.getEstensioneLon());
		 * System.out.println(bean.getLatCentroide());
		 * System.out.println(bean.getLonCentroide());
		 * System.out.println(bean.getNumSeg());
		 * System.out.println(bean1.getEstensioneLat());
		 * System.out.println(bean1.getEstensioneLon());
		 * System.out.println(bean1.getLatCentroide());
		 * System.out.println(bean1.getLonCentroide());
		 * System.out.println(bean1.getNumSeg());
		 */
		 /*BeanFilamentiConEll bean;
		 ArrayList<Filamento> fil; 
		 System.out.println("start");
		 bean = dao.SearchFilConEll(500, 2, 3);
		 System.out.println("finish");
		 fil = bean.getFilamenti();
		 System.out.println(fil.size());
		 for(int i = 0; i< fil.size(); i++) {
			 System.out.println(fil.get(i).getID());
		 }
		 */
		 
		 
		 //  <<<<<<<<<N.B>>>>>>>>>.
		 // posizione 0 #elementi trovati
		 // posizione[1...N] id trovati
		 
		 /*ArrayList<Integer> id;
		 System.out.println("start");
		 id = dao.FilamentiNumSeg(1, 100000);
		 System.out.println("finish");
		 System.out.println(id.size());
		 for (int i = 0; i < id.size(); i ++) {
			 System.out.println(id.get(i));
		 }*/
		 ArrayList <Integer> id;
		 System.out.println("start");
		 id = dao.FilamentoRegioneCerchio(10,-1, 500000);
		 System.out.println("finish");
		 for (int i = 0; i< id.size(); i++) {
			 System.out.println(id.get(i));
		 }
	}
}