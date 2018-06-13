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
		System.out.println("filamenti spitzer caricati");
		csvManager = new CsvManager(path + "/filamenti_Herschel.csv");
		csvManager.uploadFile(1);
		System.out.println("filamenti herschel caricati");
		
		//caricamento contorni dei filamenti
		csvManager = new CsvManager(path + "/contorni_filamenti_Herschel.csv");
		csvManager.uploadFile(0);
		System.out.println("contorni filamenti herschel caricati");
		csvManager = new CsvManager(path + "/contorni_filamenti_Spitzer.csv");
		csvManager.uploadFile(0);
		System.out.println("contorni filamenti spitzer caricati");
		
		//caricamento scheletro dei filamenti
		csvManager = new CsvManager(path + "/scheletro_filamenti_Herschel.csv");
		csvManager.uploadFile(2);
		System.out.println("scheletro filamenti herschel caricati");
		csvManager = new CsvManager(path + "/scheletro_filamenti_Spitzer.csv");
		csvManager.uploadFile(2);
		System.out.println("scheletro filamenti spitzer caricati");
		
		//caricamento stelle
		csvManager = new CsvManager(path + "/stelle_Herschel.csv");
		csvManager.uploadFile(3);
		System.out.println("stelle caricate");
	}
}
