package bean;

public class BeanFilamento {
	int numSeg;
	double latCentroide;
	double lonCentroide;
	double estensioneLat;
	double estensioneLon;
	
	public BeanFilamento(int numSeg, double latCentroide, double lonCentroide, double estensioneLat, double estensioneLon) {
		this.numSeg = numSeg;
		this.latCentroide = latCentroide;
		this.lonCentroide = lonCentroide;
		this.estensioneLat = estensioneLat;
		this.estensioneLon = estensioneLon;
	}

	public int getNumSeg() {
		return numSeg;
	}

	public double getLatCentroide() {
		return latCentroide;
	}

	public double getLonCentroide() {
		return lonCentroide;
	}

	public double getEstensioneLat() {
		return estensioneLat;
	}

	public double getEstensioneLon() {
		return estensioneLon;
	}
}
