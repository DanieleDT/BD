package bean;

import java.util.ArrayList;

import entity.Filamento;

public class BeanFilamentiConEll {
	private ArrayList<Filamento> filamenti;
	private int totale;
	private int parziale;
	float percentuale;
	
	public BeanFilamentiConEll(ArrayList<Filamento> filamenti, int totale, int parziale) {
		this.filamenti = filamenti;
		this.totale = totale;
		this.parziale = parziale;
		this.percentuale = (((float)parziale) / ((float)totale));
	}

	public ArrayList<Filamento> getFilamenti() {
		return filamenti;
	}

	public int getTotale() {
		return totale;
	}

	public int getParziale() {
		return parziale;
	}

	public float getPercentuale() {
		return percentuale;
	}
	
}
