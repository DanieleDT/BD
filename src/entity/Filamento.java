package entity;

public class Filamento {
	private int ID;
	private double densitaMedia;
	private double temperaturaMedia;
	private double contrasto;
	private String nome;
	private double flussoTotale;
	private double elletticita;
	private Strumento strumento; 
	
	
	public Filamento(int ID, double densitaMedia,double temperaturaMedia, double contrasto, String nome, double flussoTotale, double elletticita, Strumento strumento ) {
     	this.ID = ID;
		this.densitaMedia = densitaMedia;
		this.temperaturaMedia = temperaturaMedia;
		this.contrasto = contrasto;
		this.nome = nome;
		this.flussoTotale = flussoTotale;
		this.elletticita = elletticita;
		this.strumento = strumento;
	
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public double getDensitaMedia() {
		return densitaMedia;
	}


	public void setDensitaMedia(double densitaMedia) {
		this.densitaMedia = densitaMedia;
	}


	public double getTemperaturaMedia() {
		return temperaturaMedia;
	}


	public void setTemperaturaMedia(double temperaturaMedia) {
		this.temperaturaMedia = temperaturaMedia;
	}


	public double getContrasto() {
		return contrasto;
	}


	public void setContrasto(double contrasto) {
		this.contrasto = contrasto;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public double getFlussoTotale() {
		return flussoTotale;
	}


	public void setFlussoTotale(double flussoTotale) {
		this.flussoTotale = flussoTotale;
	}


	public double getElletticita() {
		return elletticita;
	}


	public void setElletticita(double elletticita) {
		this.elletticita = elletticita;
	}


	public Strumento getStrumento() {
		return strumento;
	}


	public void setStrumento(Strumento strumento) {
		this.strumento = strumento;
	}

	
}