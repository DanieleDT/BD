package controller;

import java.util.ArrayList;

import DAO.StelleDAO;
import bean.BeanStella;
import bean.BeanStelleInFilamento;
import entity.Stella;

public class ControllerStelle {
	// RF 9
	public BeanStelleInFilamento stelleInFIlamento(int id) {
		ArrayList<Stella> stelle;
		StelleDAO dao = new StelleDAO();
		stelle = dao.FindStelleInFilamento(id);
		int countProtostelle = 0;
		int countPrestelle = 0;
		int countUnbound = 0;
		for (int i = 0; i < stelle.size(); i++) {
			if (stelle.get(i).getTipologia().equals("UNBOUND")) {
				countUnbound++;
			} else if (stelle.get(i).getTipologia().equals("PRESTELLAR")) {
				countPrestelle++;
			} else {
				countProtostelle++;
			}
		}
		BeanStelleInFilamento bean = new BeanStelleInFilamento(stelle, countProtostelle, countPrestelle, countUnbound);
		return bean;
	}

	// RF 10
	public ArrayList<BeanStelleInFilamento> StelleInFilRettangolo(double latCentro, double lonCentro,
			double base, double altezza) {
		ArrayList<ArrayList<Stella>> stelle;
		ArrayList<BeanStelleInFilamento> beanStelle = new ArrayList<BeanStelleInFilamento>();
		StelleDAO dao = new StelleDAO();
		stelle = dao.StelleInFilRettangolo(latCentro, lonCentro, base, altezza);
		for (int i = 0; i < 2; i++) {
			int countProtostelle = 0;
			int countPrestelle = 0;
			int countUnbound = 0;
			for (int j = 0; j < stelle.get(i).size(); j++) {
				if (stelle.get(i).get(j).getTipologia().equals("UNBOUND")) {
					countUnbound++;
				}else if( stelle.get(i).get(j).getTipologia().equals("PROTOSTELLAR")) {
					countProtostelle++;
				}else {
					countPrestelle++;
				}
			}
			BeanStelleInFilamento bean = new BeanStelleInFilamento(stelle.get(i), countProtostelle, countPrestelle, countUnbound);
			beanStelle.add(bean);
		}
		return beanStelle;
	}
	
	//RF 12
	public ArrayList<BeanStella> distanzaStelleSpinaDorsale(int idFil){
		ArrayList<BeanStella> bean;
		StelleDAO dao = new StelleDAO();
		bean = dao.distanzaStelleSpinaDorsale(idFil);
		return bean;
	}
	public static void main() {
		ControllerStelle cont = new ControllerStelle();
		cont.StelleInFilRettangolo(0,0, 100000, 1000000);
		System.out.println("END");
	}
	
}
