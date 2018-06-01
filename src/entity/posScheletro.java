package entity;

public class posScheletro {
	private double latitudine;
	private double longitudine;
	private double flusso;
	private int numeroProgressivo;
	private int idScheletro;
	
	public posScheletro (double latitudine, double longitudine, double flusso, int numeroProgressivo, int idScheletro) {
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.flusso = flusso;
		this.numeroProgressivo = numeroProgressivo;
		this.idScheletro = idScheletro;
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

	public double getFlusso() {
		return flusso;
	}

	public void setFlusso(double flusso) {
		this.flusso = flusso;
	}

	public int getNumeroProgressivo() {
		return numeroProgressivo;
	}

	public void setNumeroProgressivo(int numeroProgressivo) {
		this.numeroProgressivo = numeroProgressivo;
	}

	public int getIdScheletro() {
		return idScheletro;
	}

	public void setIdScheletro(int idScheletro) {
		this.idScheletro = idScheletro;
	}

}
