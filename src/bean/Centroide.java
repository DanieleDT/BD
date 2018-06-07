package bean;

public class Centroide {
	private double latitudine;
	private double longitudine;
	
	public Centroide(double latitudine, double longitudine) {
		this.latitudine = latitudine;
		this.longitudine = longitudine;
	}
	
	public double getLat() {
		return this.latitudine;
	}
	
	public double getLon() {
		return this.longitudine;
	}
}
