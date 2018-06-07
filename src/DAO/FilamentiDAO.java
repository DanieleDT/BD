package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import bean.BeanFilamento;
import bean.Centroide;
import bean.Estensione;



public class FilamentiDAO {
	public Centroide getCentroideId(int id, Connection connection) {
		double longitudine;
		double latitudine;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Centroide centroide = null;
		try {
			String sql = "SELECT avg(Latitudine) as lat, avg(Longitudine) as lon FROM PosCont WHERE idFil = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			
			resultSet = stmt.executeQuery(); 
			
			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			centroide = new Centroide(latitudine, longitudine);
		}
		catch(Exception e){
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
			String sql = "SELECT avg(Latitudine) as lat, avg(Longitudine) as lon FROM PosCont WHERE idFil = (SELECT id FROM Filamento WHERE nome = ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			
			resultSet = stmt.executeQuery(); 
			
			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			centroide = new Centroide(latitudine, longitudine);
		}
		catch(Exception e){
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
			String sql = "SELECT max(longitudine) - min(longitudine) as lon, max(latitudine) - min(latitudine) as lat FROM PosCont WHERE idFil = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			
			resultSet = stmt.executeQuery(); 
			
			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			estensione = new Estensione (latitudine, longitudine);
		}
		catch (Exception e) {
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
			String sql = "SELECT max(longitudine) - min(longitudine) as lon, max(latitudine) - min(latitudine) as lat FROM PosCont WHERE idFil = (SELECT id FROM Filamento WHERE nome = ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			
			resultSet = stmt.executeQuery(); 
			
			latitudine = resultSet.getDouble("lat");
			longitudine = resultSet.getDouble("lon");
			estensione = new Estensione (latitudine, longitudine);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return estensione;
	}
	
	public int getNumSegId(int id, Connection connection) {
		int numSeg = -1;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT count* as num FROM Scheletro WHERE idFIl = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			resultSet = stmt.executeQuery(); 
			numSeg = resultSet.getInt("num");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return numSeg;
	}
	
	public int getNumSegDesignazione(String nome, Connection connection) {
		int numSeg = -1;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT count* as num FROM Scheletro WHERE idFIl =(SELECT id FROM Filamento WHERE nome = ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			resultSet = stmt.executeQuery(); 
			numSeg = resultSet.getInt("num");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return numSeg;
	}
	
	public  BeanFilamento InformazioniFilamentoId(int id) {
		Centroide centroide;
		Estensione estensione;
		int numSeg;
		BeanFilamento beanFilamento;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB","postgres","postgres");
		}catch(Exception e) {
			e.printStackTrace();
		}
		centroide = getCentroideId(id, connection);
		estensione = getEstensioneId(id, connection);
		numSeg = getNumSegId(id, connection);
		beanFilamento = new BeanFilamento(numSeg, centroide.getLat(), centroide.getLon(), estensione.getLatitudine(), estensione.getLongitudine());
		return beanFilamento;
	}
	
	public  BeanFilamento InformazioniFilamentoDesignazione(String nome) {
		Centroide centroide;
		Estensione estensione;
		int numSeg;
		BeanFilamento beanFilamento;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB","postgres","postgres");
		}catch(Exception e) {
			e.printStackTrace();
		}
		centroide = getCentroideDesignazione(nome, connection);
		estensione = getEstensioneDesignazione(nome, connection);
		numSeg = getNumSegDesignazione(nome, connection);
		beanFilamento = new BeanFilamento(numSeg, centroide.getLat(), centroide.getLon(), estensione.getLatitudine(), estensione.getLongitudine());
		return beanFilamento;
	}
}
