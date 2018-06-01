package entity;

public class Satellite {
	private String nome ;
	private String agenzia;
	
	public Satellite (String nome, String agenzia) {
		this.nome = nome;
		this.agenzia = agenzia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAgenzia() {
		return agenzia;
	}

	public void setAgenzia(String agenzia) {
		this.agenzia = agenzia;
	}
	
}
