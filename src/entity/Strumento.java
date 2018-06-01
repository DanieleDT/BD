package entity;

public class Strumento {
	private String nome;
	private String satellite;
	
	public Strumento(String nome, String satellite) {
		this.nome = nome;
		this.satellite = satellite;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSatellite() {
		return satellite;
	}

	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}

}
