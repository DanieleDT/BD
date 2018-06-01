package entity;

public class Scheletro {
	private int id;
	private boolean tipo;
	private Filamento filamento;
	
	public Scheletro (int id, boolean tipo, Filamento filamento) {
		this.id = id;
		this.tipo = tipo;
		this.filamento = filamento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public Filamento getFilamento() {
		return filamento;
	}

	public void setFilamento(Filamento filamento) {
		this.filamento = filamento;
	}
}
