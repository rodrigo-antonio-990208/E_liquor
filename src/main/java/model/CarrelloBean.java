package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarrelloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List <Prodotto> prodotti;
	
	public CarrelloBean() {
		prodotti = new ArrayList <Prodotto> ();
	}
	
	public void addProdotto(Prodotto prod) {
		prodotti.add(prod);
	}
	
	
	public void rimuoviProdotto (Prodotto prod) {
		for (Prodotto p : prodotti) {
			if (p.getIdProdotto() == prod.getIdProdotto()) {
				prodotti.remove(p);
				break;
			}
		}
	}
	
	public float getPrezzoTotale() {
		float prezzo = 0;
		for (Prodotto p : prodotti) {
			prezzo += p.getPrezzo();
		}
		return prezzo;
	}
	
	public List<Prodotto> getProdotti(){
		return prodotti;
	}
	
	public boolean isEmpty() {
		if (prodotti.size()==0) {
			return true;
		}
		return false;
	}

	public int getQuantitaProd (int codice) {
		int cont =0;
		for (Prodotto p : prodotti) {
			if (p.getIdProdotto() == codice) {
			cont ++;
			}
		}
		return cont;
	}
	
}
