package csv;

import java.io.File;

public class InitDatabase {
	public static void main(String[] args) {
		String path ; 
		CsvManager csvManager;
		path= new File("CsvFiles").getAbsolutePath();
		
		//caricamento filamenti
		csvManager = new CsvManager(path + "/filamenti_Spitzer.csv");
		csvManager.uploadFile(1);
		csvManager = new CsvManager(path + "/filamenti_Herschel.csv");
		csvManager.uploadFile(1);
		
		//caricamento contorni dei filamenti
		csvManager = new CsvManager(path + "/contorni_filamenti_Herschel.csv");
		csvManager.uploadFile(0);
		csvManager = new CsvManager(path + "/contorni_filamenti_Spitzer.csv");
		csvManager.uploadFile(0);
		
		//caricamento scheletro dei filamenti
		csvManager = new CsvManager(path + "/scheletro_filamenti_Herschel.csv");
		csvManager.uploadFile(2);
		csvManager = new CsvManager(path + "/scheletro_filamenti_Spitzer.csv");
		csvManager.uploadFile(2);
		
		//caricamento stelle
		csvManager = new CsvManager(path + "/stelle_Herschel.csv");
		csvManager.uploadFile(3);
	}
}
