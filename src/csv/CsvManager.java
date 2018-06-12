package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import DAO.FilamentiDAO;

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
				
			case 2:
				
			case 3:
				
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
				FilamentiDAO filamentiDao = new FilamentiDAO();
				if(filamentiDao.existFil(ID, conn) && !existContorno(ID, lon, lat conn)) {
					insertPuntoContorno();
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
				if(existStrumento(strumento, satellite)) {
					FilamentiDAO filamentiDao = new FilamentiDAO();
					if(filamentiDao.existFil(ID, conn)) {
						updateFil(/* params*/);
					}else {
						insertFil(/*params*/);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean uploadStelle() {
		return true;
	}
	
	private void insertScheletro(BufferedReader bufRead, Connection conn) {
		String line;
		try {
			while((line = bufRead.readLine())!= null) {
				String[] linePart = line.split(",");
				int ID = Integer.parseInt(linePart[0].trim());
				double lon = Double.parseDouble(linePart[1].trim());
				double lat = Double.parseDouble(linePart[2].trim());
				FilamentiDAO filamentiDao = new FilamentiDAO();
				if(filamentiDao.existFil(ID, conn) && !existContorno(ID, lon, lat conn)) {
					insertPuntoContorno();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
