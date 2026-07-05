package dao;

import java.sql.SQLException;


import model.UtenteBean;

public interface UtenteDao {

	public void doSave(UtenteBean utente) throws SQLException;
	
	public boolean doDelete(int codice) throws SQLException;
	
	public UtenteBean doRetrieveByKey(int codice) throws SQLException;
		
	public UtenteBean doRetrieveByMail(String email) throws SQLException;
	
	public UtenteBean doRetrieveByLogin(String email, String password) throws SQLException;
}
