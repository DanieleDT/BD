package csv;

import java.sql.Connection;
import java.sql.DriverManager;

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
		try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql:progettoDB","postgres","postgres");
			}
		catch (Exception e) {
            e.printStackTrace();
        }
		if (type > 3|| type < 0 ) return false;
		
		return true;
	}
	
	private boolean uploadStelle() {
		return true;
	}
	
}
