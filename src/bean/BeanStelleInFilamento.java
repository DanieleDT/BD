package bean;

import java.util.ArrayList;

import entity.Stella;

public class BeanStelleInFilamento {
	private ArrayList<Stella> stelle;
	private int countProtostelle;
	private int countPrestelle;
	private int countUnbound;
	
	public BeanStelleInFilamento(ArrayList<Stella> stelle, int countProtostelle, int countPrestelle, int countUnbound) {
		this.stelle = stelle;
		this.countPrestelle = countPrestelle;
		this.countProtostelle = countProtostelle;
		this.countUnbound = countUnbound;
	}

	public ArrayList<Stella> getStelle() {
		return stelle;
	}

	public void setStelle(ArrayList<Stella> stelle) {
		this.stelle = stelle;
	}

	public int getCountProtostelle() {
		return countProtostelle;
	}

	public void setCountProtostelle(int countProtostelle) {
		this.countProtostelle = countProtostelle;
	}

	public int getCountPrestelle() {
		return countPrestelle;
	}

	public void setCountPrestelle(int countPrestelle) {
		this.countPrestelle = countPrestelle;
	}

	public int getCountUnbound() {
		return countUnbound;
	}

	public void setCountUnbound(int countUnbound) {
		this.countUnbound = countUnbound;
	}
}