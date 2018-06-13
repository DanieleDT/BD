package controller;

import java.util.ArrayList;

import DAO.ScheletroDAO;

public class ControllerScheletro {
	
	//RF 11
	public ArrayList<Double> distanzaSegmentoContorno(int id){
		ArrayList<Double> distanze;
		ScheletroDAO dao = new ScheletroDAO();
		distanze = dao.distanzaSegmentoContorno(id);
		return distanze;
	}
}