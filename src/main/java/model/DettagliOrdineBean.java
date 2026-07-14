package model;

import java.io.Serializable;

public class DettagliOrdineBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idProdotto;
	private int idOrdine;
	private float prezzoAcquisto;
	private int quantita ;
	
	public DettagliOrdineBean () {
		
	}
	
	public int getIdProdotto () {
		return idProdotto;
	}
	
	public int getIdOrdine() {
		return idOrdine;
	}
	
	public float getPrezzoAcquisto() {
		return prezzoAcquisto;
	}
	
	public int getQuantita() {
		return quantita;
	}
	
	public void setIdProdotto(int i) {
		idProdotto = i;
	}

	public void setIdOrdine(int i) {
		idOrdine = i;
	}
	
	public void setPrezzoAcquisto ( float i) {
		prezzoAcquisto = i;
	}
	
	public void setQuantita(int i) {
		quantita = i;
	}
	
}
