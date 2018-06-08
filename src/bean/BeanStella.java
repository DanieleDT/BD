package bean;

public class BeanStella {
	private int id;
	private double flusso;
	private double distanza;
	private String nome;
	
	public BeanStella(int id, double flusso, double distanza, String nome) {
		this.id = id;
		this.flusso = flusso;
		this.distanza = distanza;
		this.nome = nome;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getFlusso() {
		return flusso;
	}

	public void setFlusso(double flusso) {
		this.flusso = flusso;
	}

	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
