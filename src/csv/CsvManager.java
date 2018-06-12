package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import DAO.ContornoDAO;
import DAO.FilamentiDAO;
import DAO.StelleDAO;
import DAO.StrumentoDAO;

public class CsvManager {
	String path;
	
	public CsvManager(String path) {
		this.path = path;
	}
	
	public boolean uploadFile(int type){
		/*tipo di file
		 0 file contorni filamento
		 1 file filamenti
		 2 file scheletro filamenti
		 3 file stelle
		 */
		Connection conn=null;
		BufferedReader bufferedReader = null;
		try {
			conn=DriverManager.getConnection("jdbc:postgresql:progettoDB","postgres","postgres");
			}
		catch (Exception e) {
            e.printStackTrace();
        }
		if (type > 3|| type < 0 ) return false;
		try {
			bufferedReader = new BufferedReader(new FileReader(this.path));
			String header = bufferedReader.readLine(); //salta header
		}catch(Exception e) {
			e.printStackTrace();
		}
		switch(type) {
			case 0:
				insertContorni(bufferedReader, conn);
			case 1:
				insertFilamenti(bufferedReader, conn);
			case 2:
				//insertScheletro(bufferedReader, conn);
			case 3:
				//insertStelle(bufferedReader, conn);
		}
		return true;
	}
	
	private void insertContorni(BufferedReader bufRead, Connection conn) {
		String line;
		try {
			while((line = bufRead.readLine())!= null) {
				String[] linePart = line.split(",");
				int ID = Integer.parseInt(linePart[0].trim());
				double lon = Double.parseDouble(linePart[1].trim());
				double lat = Double.parseDouble(linePart[2].trim());
				if(FilamentiDAO.existFilamento(ID, conn) && !(ContornoDAO.existContorno(ID, lon, lat, conn))) {
					ContornoDAO.insertPuntoContorno(ID, lon, lat, conn);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insertFilamenti(BufferedReader bufRead, Connection conn) {
		String line;
		try {
			while((line = bufRead.readLine())!= null) {
				String[] linePart = line.split(",");
				int ID = Integer.parseInt(linePart[0].trim());
				String nome = linePart[1].trim();
				double flusso = Double.parseDouble(linePart[2].trim());
				double densita = Double.parseDouble(linePart[3].trim());
				double temperatura = Double.parseDouble(linePart[4].trim());
				double ellitticita = Double.parseDouble(linePart[5].trim());
				double contrasto = Double.parseDouble(linePart[6].trim());
				String satellite = linePart[7].trim();
				String strumento = linePart[8].trim();
				if(StrumentoDAO.existStrumento(strumento, satellite, conn)) {
					if(FilamentiDAO.existFilamento(ID, conn)) {
						FilamentiDAO.updateFilamento(ID, nome, flusso, contrasto, densita, temperatura, ellitticita, strumento, satellite, conn);
					}else {
						FilamentiDAO.insertFilamento(ID, nome, flusso, contrasto, densita, temperatura, ellitticita, strumento, satellite, conn);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/*	private void insertScheletro(BufferedReader bufRead, Connection conn) {
		String line;
		try {
			while((line = bufRead.readLine())!= null) {
				String[] linePart = line.split(",");
				int idFil = Integer.parseInt(linePart[0].trim());
				int id = Integer.parseInt(linePart[1].trim());
				String tipo = linePart[2].trim();
				double lon = Double.parseDouble(linePart[3].trim());
				double lat = Double.parseDouble(linePart[4].trim());
				int numProg = Integer.parseInt(linePart[5].trim());
				double flusso = Double.parseDouble(linePart[6].trim());
				if(FilamentiDAO.existFilamento(idFil, conn)) {
					if(!existScheletro(id, conn)){
						insertScheltro(wwwwww);
					}else {
						updateScheletro(wwww);
					}
					if(existPuntoScheletro(lat, lon , wwww)) {
						updatePuntoScheletro(wwww);
					}else {
						insertPuntoScheletro();
					}//insert scheletro se non esiste
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insertStelle(BufferedReader bufRead, Connection conn) {
		String line;
		try {
			while((line = bufRead.readLine())!= null) {
				String[] linePart = line.split(",");
				int ID = Integer.parseInt(linePart[0].trim());
				String nome = linePart[1].trim();
				double lon = Double.parseDouble(linePart[2].trim());
				double lat = Double.parseDouble(linePart[3].trim());
				double flusso = Double.parseDouble(linePart[4].trim());
				String tipologia = linePart[5].trim();
				if(!StelleDAO.exist(ID, conn)) {
					StelleDAO.insertStella(wwwww);
				}else {
					StelleDAO.updateStella(wwwww);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public static void main(String[] args) {
		CsvManager csv = new CsvManager("/home/...");
		csv.uploadFile(1);
	}
}
