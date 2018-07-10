package controller;

import java.util.ArrayList;

import DAO.FilamentiDAO;
import bean.BeanFilamentiConEll;
import bean.BeanFilamento;
import entity.Filamento;

public class ControllerFilamenti {
	//RF 5 ID
	public BeanFilamento InformazioniFilamentoId(int id) {
		FilamentiDAO dao = new FilamentiDAO();
		BeanFilamento bean = dao.InformazioniFilamentoId(id);
		return bean;
	}
	//RF 5 Nome
	public BeanFilamento InformazioniFilamentoDesignazione(String nome) {
		FilamentiDAO dao = new FilamentiDAO();
		BeanFilamento bean = dao.InformazioniFilamentoDesignazione(nome);
		return bean;
	}
	//RF 6
	public BeanFilamentiConEll SearchFilamentoConEll(int brillanza, double eMin, double eMax) {
		FilamentiDAO dao = new FilamentiDAO();
		BeanFilamentiConEll bean = dao.SearchFilConEll(brillanza, eMax, eMin);
		return bean;
		
	}
	//RF 7
	public ArrayList<Filamento> FilamentiNumSegmenti(int min, int max){
		ArrayList<Integer> id;
		ArrayList<Filamento> filamenti;
		//ArrayList<BeanFilNumSeg> result;
		FilamentiDAO dao = new FilamentiDAO();
		id = dao.FilamentiNumSeg(min, max);
		filamenti = dao.loadFilamentiById(id);
		//result = dao.filamentiNumSeg(filamenti);
		return filamenti;
	}
	
	//RF 8 Quadrato
	public ArrayList<Filamento> FilamentiInRegioneQuadrato(double lonCentro, double latCentro, double lato){
		ArrayList<Integer> id;
		ArrayList<Filamento> filamenti;
		FilamentiDAO dao = new FilamentiDAO();
		id = dao.FilamentoRegioneQuadrato(latCentro, lonCentro, lato);
		filamenti = dao.loadFilamentiById(id);
		return filamenti;
	}
	
	//RF 8 Cerchio
	public ArrayList<Filamento> FilamentiInRegioneCerchio(double lonCentro, double latCentro, double raggio){
		ArrayList<Integer> id;
		ArrayList<Filamento> filamenti;
		FilamentiDAO dao = new FilamentiDAO();
		id = dao.FilamentoRegioneCerchio(latCentro, lonCentro, raggio);
		filamenti = dao.loadFilamentiById(id);
		return filamenti;
	}
	
}
