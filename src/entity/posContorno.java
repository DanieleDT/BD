package entity;

public class posContorno {
	private double latitudine;
	private double longitudine;
	private int idFil;
	
	public posContorno (double latitudine, double longitudine, int filamento) {
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.idFil=filamento;
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

	public int getIdFil() {
		return idFil;
	}

	public void setIdFil(int idFil) {
		this.idFil = idFil;
	}

	
}
