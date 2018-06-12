package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import entity.Stella;
import entity.posContorno;

//RF 9
public class StelleDAO {
	public ArrayList<Stella> FindStelleInFilamento(int IdFil){
		ArrayList<Stella> stelle, stelleInFil = null;
		ArrayList<posContorno> posizioniContorno; 
		stelle = LoadAllStelle();
		posizioniContorno = posContornoById(IdFil);
		if(posizioniContorno.size() == 0) {
			return stelle;
		}
		stelleInFil = new ArrayList<Stella>();
		for(int i = 0; i <= stelle.size() -1; i++) {
			Stella stella = stelle.get(i);
			double sum = 0;
			for(int j = 0; j <= posizioniContorno.size() -2; j++) {
				posContorno pos0 = posizioniContorno.get(j);
				posContorno pos1 = posizioniContorno.get(j+1);
				double num = (pos0.getLongitudine()-stella.getLongitudine())*(pos1.getLatitudine()-stella.getLatitudine())- (pos0.getLatitudine() - stella.getLatitudine())*(pos1.getLongitudine() - stella.getLongitudine());
				double den = (pos0.getLongitudine() - stella.getLongitudine())*(pos1.getLongitudine()- stella.getLongitudine())+(pos0.getLatitudine()- stella.getLatitudine())*(pos1.getLatitudine() - stella.getLatitudine());
				double arctan = (double) Math.atan(num/den);
				sum += arctan;
			}
			if (Math.abs(sum) >= 0.01) {
				stelleInFil.add(stella);
			}
		}
		return stelleInFil;
		
		/*
		 * Da aggiungere nel controller della RF per le informazioni sulle stelle trovate
		 * 
		 * int protoStelle;
		 * int preStelle;
		 * int unbound;
		 * numStelle = stelleInFIl.size();
		 * for (int i = 0; int <= stelleInFil.size()-1; i++){
		 * 	if ((stelleInFil.get(i)).getCategoria().equals("PROTOSTELLAR")){
		 * 		protoStelle++;
		 * 	}else if( ){
		 * 
		 * 	}else{
		 *  
		 *  }
		 * }
		 */
	}
	
	//RF 10
	public ArrayList<ArrayList<Stella>> StelleInFilRettangolo(double latCentro, double lonCentro, double base, double altezza){
		ArrayList<Stella> stelle = LoadStelleInRettangolo(latCentro, lonCentro, base, altezza);
		ArrayList<Integer> idFil = LoadFilamentiInRettangolo(latCentro, lonCentro, base, altezza);
		ArrayList<ArrayList<Stella>> result = new ArrayList<ArrayList<Stella>>();
		ArrayList<Stella> stelleInFil = new ArrayList<Stella>();
		ArrayList<Stella> stelleNotInFil = new ArrayList<Stella>();
		ArrayList<posContorno> contorno = null;
		result.add(stelleInFil);
		result.add(stelleNotInFil);
		for (int i = 0; i <= idFil.size() -1; i++) {
			contorno = posContornoById(idFil.get(i));
			for ( int j = 0; j <= stelle.size() -1; j++) {
				int sum = 0;
				for(int k = 0; k <= contorno.size() -2; k++) {
					posContorno pos0 = contorno.get(j);
					posContorno pos1 = contorno.get(j+1);
					double num = (pos0.getLongitudine()-(stelle.get(j)).getLongitudine())*(pos1.getLatitudine()-stelle.get(j).getLatitudine())- (pos0.getLatitudine() - stelle.get(j).getLatitudine())*(pos1.getLongitudine() - stelle.get(j).getLongitudine());
					double den = (pos0.getLongitudine() - (stelle.get(j)).getLongitudine())*(pos1.getLongitudine()- stelle.get(j).getLongitudine())+(pos0.getLatitudine()- stelle.get(j).getLatitudine())*(pos1.getLatitudine() - stelle.get(j).getLatitudine());
					double arctan = (double) Math.atan(num/den);
					sum += arctan;
				}
				if (Math.abs(sum) >= 0.01) {
					// aggiungo la j stella nell'array delle stelle comprese nei filamenti
					stelleInFil.add(stelle.get(j));	
				}else {
					stelleNotInFil.add(stelle.get(j));
				}
			}
		}
		
		// bisogna fare parse dei dati per ottenere valori percentuali
		return result;
	}
	
	public ArrayList<Stella> LoadStelleInRettangolo(double latCentro, double lonCentro, double base, double altezza){
		ArrayList<Stella> stelle = new ArrayList<Stella>();
		double semiBase = ((double)base)/2;
		double semiAltezza = ((double)altezza)/2;
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		Stella stella;
		int ID;
		String nome;
		double latitudine;
		double longitudine;
		double valoreFlusso;
		String tipologia;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB","postgres","postgres");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT * FROM Stella WHERE longitudine >= ? AND longitudine <= ? AND latitudine >= ? AND latitudine <= ?";
			stmt = connection.prepareStatement(sql);
			stmt.setDouble(1 , (lonCentro - semiBase));
			stmt.setDouble(2 , (lonCentro +semiBase));
			stmt.setDouble(3 , (latCentro - semiAltezza));
			stmt.setDouble(4 , (latCentro + semiAltezza));
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				ID = resultSet.getInt("ID");
				nome = resultSet.getString("nome");
				latitudine = resultSet.getDouble("latitudine");
				longitudine = resultSet.getDouble("longitudine");
				valoreFlusso = resultSet.getDouble("valoreFlusso");
				tipologia = resultSet.getString("tipologia");
				stella = new Stella( ID, nome, latitudine, longitudine, valoreFlusso, tipologia);
				stelle.add(stella);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stelle;
	}
	
	public ArrayList<Integer> LoadFilamentiInRettangolo(double latCentro, double lonCentro, double base, double altezza){
		ArrayList<Integer> filamenti = new ArrayList<Integer>();
		double semiBase = ((double)base)/2;
		double semiAltezza = ((double)altezza)/2;
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB","postgres","postgres");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT distinct(idFil) FROM PosContorno WHERE Longitudine >= ? AND Longitudine <= ? AND Latitudine >= ? AND Latitudine <= ?";
			stmt = connection.prepareStatement(sql);
			stmt.setDouble(1 , (lonCentro - semiBase));
			stmt.setDouble(2 , (lonCentro +semiBase));
			stmt.setDouble(3 , (latCentro - semiAltezza));
			stmt.setDouble(4 , (latCentro + semiAltezza));
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				filamenti.add(resultSet.getInt("idFil"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filamenti;
	}
	
	public ArrayList<Stella> LoadAllStelle(){
		ArrayList<Stella> stelle = new ArrayList<Stella>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Stella stella;
		int ID;
		String nome;
		double latitudine;
		double longitudine;
		double valoreFlusso;
		String tipologia;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB","postgres","postgres");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT * FROM \"Stella\"";
			stmt = connection.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				ID = resultSet.getInt("ID");
				nome = resultSet.getString("nome");
				latitudine = resultSet.getDouble("latitudine");
				longitudine = resultSet.getDouble("longitudine");
				valoreFlusso = resultSet.getDouble("valoreFlusso");
				tipologia = resultSet.getString("tipologia");
				stella = new Stella( ID, nome, latitudine, longitudine, valoreFlusso, tipologia);
				stelle.add(stella);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stelle;
	}
	
	
	
	static public ArrayList<posContorno> posContornoById(int ID){
		ArrayList<posContorno> posizioniContorno = new ArrayList<posContorno>();
		posContorno posizioneContorno;
		double latitudine;
		double longitudine;
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql:ProgettoDB","postgres","postgres");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			String sql = "SELECT Latitudine, Longitudine FROM PosContorno WHERE idFil = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, ID);
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				latitudine = resultSet.getDouble("Latitudine");
				longitudine = resultSet.getDouble("Longitudine");
				posizioneContorno = new posContorno(latitudine, longitudine, ID);
				posizioniContorno.add(posizioneContorno);	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return posizioniContorno;
	}
	public static void main(String[] args) {
		StelleDAO dao = new StelleDAO();
		dao.FindStelleInFilamento(5);
	}
}
