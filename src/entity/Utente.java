package entity;

public class Utente {
	private String nome;
	private String cognome;
	private String userid;
	private String email;
	private Boolean tipo;

	public Utente(String nome, String cognome, String userid, String email, Boolean tipo) {
		this.nome = nome;
		this.cognome = cognome;
		this.userid = userid;
		this.email = email;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getTipo() {
		return tipo;
	}

	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}
}
