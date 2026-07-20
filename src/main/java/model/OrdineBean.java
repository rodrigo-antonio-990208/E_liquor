package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdineBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private int idOrdine;
	private int idUtente;
	private Timestamp data;
	private String indirizzo;
	private String pagamento;
	private float totale;
	private String stato;
	List<DettagliOrdineBean> ordineAcquistato;
	
	public OrdineBean() {
		ordineAcquistato = new ArrayList<>();
	}
	
	public int getIdOrdine() {
		return idOrdine;
	}
	
	public void setIdOrdine(int i) {
		idOrdine=i;
	}
	
	public Timestamp getData() {
		return data;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	public String getPagamento () {
		return pagamento;
	}
	
	public float getTotale() {
		return totale;
	}
	
	public String getStato() {
		return stato;
	}
	public int getIdUtente() {
		return idUtente;
	}
	
	public void setData(Timestamp t) {
		data = t;
	}
	
	public void setIdUtente(int i) {
	idUtente = i;	
	}
	
	public void setIndirizzo(String t) {
		indirizzo = t;
	}
	
	public void setPagamento(String p) {
		pagamento = p;
	}
	
	public void setTotale(float t) {
		totale= t;
	}
	
	public void setStato(String s) {
		stato = s;
	}
	
	public void setOrdineAcquistato(List<DettagliOrdineBean> dett) {
		ordineAcquistato = dett;
	}
	
	public List<DettagliOrdineBean> getOrdineAcquistato (){
		return ordineAcquistato;
	}
}
