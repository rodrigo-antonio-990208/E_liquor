package model;

import java.io.Serializable;

public class Prodotto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idProdotto;
	private int idCategoria;
	private String nome;
	private String descrizione;
	private boolean attivo;
	private int quant_disponibile;
	private float prezzo;
	private float gradazione;
	private int formato;
	private String immagineUrl;
	private String mimeType;
	
	public  Prodotto() {
	}
	
	public Prodotto(int idProdotto, int idCategoria, float gradazione, String nome, String descrizione, boolean attivo, int quant_disponibile, float prezzo, int formato, String immagineUrl, String mimeType ) {
		
		this.idProdotto = idProdotto;
		this.idCategoria = idCategoria;
		this.nome = nome;
		this.descrizione = descrizione;
		this.attivo = attivo;
		this.gradazione = gradazione;
		this.quant_disponibile = quant_disponibile;
		this.prezzo = prezzo;
		this.formato = formato;
		this.immagineUrl = immagineUrl;
		this.mimeType = mimeType;
	}
	
	public void setIdProdotto (int p) {
		idProdotto = p;
	}
	
	public void setIdCategoria (int c) {
		idCategoria = c;
	}
	
	public void setNome (String n) {
		nome = n;
	}
	
	public void setDescrizione (String d) {
		descrizione = d;
	}
	
	public void setAttivo (boolean a) {
		attivo = a;
	}
	
	public void setQuantita (int q) {
		quant_disponibile = q;
	}
	
	public void setPrezzo (float prezzo) {
		this.prezzo= prezzo;
	}
	
	public void setFormato (int f) {
		formato = f;
	}
	
	public void setGradazione (float g) {
		gradazione = g;
	}
	
	public void setImmagineUrl(String imm) {
		immagineUrl = imm;
	}
	
	public void setMimeType (String mi) {
		mimeType = mi;
	}
	
	public int getIdProdotto () {
		return idProdotto;
	}
	
	public int getIdCategoria() {
		return idCategoria;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public boolean isAttivo () {
		return attivo;
	}
	
	public int getQuant() {
		return quant_disponibile;
	}
	
	public float getPrezzo() {
		return prezzo;
	}
	
	public float getGradazione() {
		return gradazione;
	}
	
	public int getFormato () {
		return formato;
	}
	
	public String getImmagineUrl() {
		return immagineUrl;
	}
	
	public String getMime() {
		return mimeType;
	}
}
