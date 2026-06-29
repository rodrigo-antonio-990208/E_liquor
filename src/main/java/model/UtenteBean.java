package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.sql.Timestamp;

public class UtenteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idUtente;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String ruolo;
	private Timestamp registrazione;
	
	public UtenteBean() {
	}
	
	public UtenteBean(int idUtente, String nome, String cognome, String email, String password, String ruolo, Timestamp registrazione) {
		this.idUtente = idUtente;
		this.nome = nome;
		this.cognome= cognome;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
		this.registrazione = registrazione;
	}
	
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int i) {
		idUtente= i;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCognome(String c) {
		cognome=c;
	}
	public String getCognome() {
		return cognome;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String e) {
		email=e;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password= password;
	}
	
	public void setRuolo (String ruolo) {
	this.ruolo = ruolo;
	}
	public String getRuolo() {
		return ruolo;
	}
	
	public void setDataRegistrazione (Timestamp d) {
		registrazione = d;
	}
	public Timestamp getDatRegistrazione() {
		return registrazione;
	}
	
}
