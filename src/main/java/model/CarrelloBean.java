package model;
import java.util.Iterator;

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
		if (prod == null) {
			return;
		}
		
		Iterator <Prodotto> it = prodotti.iterator();
		while (it.hasNext()) {
			Prodotto p = it.next();
				if (p.getIdProdotto() == prod.getIdProdotto()) {
					it.remove();
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
	
	public void incrementa (Prodotto prod) {
		if (prod!= null) {
			prodotti.add(prod);
		}
	}
	
	public void decrementa (Prodotto prod) {
		for (int i = 0; i< prodotti.size(); i++) {
			if (prodotti.get(i).getIdProdotto() == prod.getIdProdotto()) {
				prodotti.remove(i);
				break;
			}
		}
	}
	
	public void svuotaCarrello() {
		prodotti.clear();
	}
	
}
