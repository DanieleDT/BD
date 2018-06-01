package entity;

public class Stella {
	private int id;
	private String nome;
	private double latitudine;
	private double longitudine;
	private double valoreFlusso;
	private String tipologia;
	
	public Stella (int id, String nome, double latitudine, double longitudine, double valoreFlusso, String tipologia) {
		this.id = id;
		this.nome = nome;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.valoreFlusso = valoreFlusso;
		this.tipologia = tipologia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}

	public double getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}

	public double getValoreFlusso() {
		return valoreFlusso;
	}

	public void setValoreFlusso(double valoreFlusso) {
		this.valoreFlusso = valoreFlusso;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

}