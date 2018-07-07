package bean;

import entity.Filamento;

public class BeanFilNumSeg {
	private Filamento filamento;
	private int numSeg;
	
	public BeanFilNumSeg(Filamento filamento, int numSeg) {
		super();
		this.filamento = filamento;
		this.numSeg = numSeg;
	}
	public Filamento getFilamento() {
		return filamento;
	}
	public void setFilamento(Filamento filamento) {
		this.filamento = filamento;
	}
	public int getNumSeg() {
		return numSeg;
	}
	public void setNumSeg(int numSeg) {
		this.numSeg = numSeg;
	}
}
