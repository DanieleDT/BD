package entity;

public class Banda {
	private double valore;
	private Strumento strumento;
	
	public Banda (double valore, Strumento strumento) {
		this.valore = valore;
		this.strumento = strumento;
	}

	public double getValore() {
		return valore;
	}

	public void setValore(double valore) {
		this.valore = valore;
	}

	public Strumento getStrumento() {
		return strumento;
	}

	public void setStrumento(Strumento strumento) {
		this.strumento = strumento;
	}

	
}
