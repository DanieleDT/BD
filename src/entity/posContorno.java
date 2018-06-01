package entity;

public class posContorno {
	private double latitudine;
	private double longitudine;
	private Filamento filamento;
	
	public posContorno (double latitudine, double longitudine, Filamento filamento) {
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.filamento=filamento;
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

	public Filamento getFilamento() {
		return filamento;
	}

	public void setFilamento(Filamento filamento) {
		this.filamento = filamento;
	}
	
}
