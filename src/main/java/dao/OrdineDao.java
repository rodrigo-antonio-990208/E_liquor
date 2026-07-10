package dao;

import java.sql.SQLException;
import java.io.IOException;
import model.OrdineBean;
import java.util.List;

import model.CarrelloBean;

public interface OrdineDao {

	public int doSave(OrdineBean ordine) throws SQLException;
	
	public OrdineBean doRetrieveByKey(int codice) throws SQLException;
	
	public List<OrdineBean> doRetrieveByUser(int idUser) throws SQLException;
	
	public List<OrdineBean> doRetrieveAll()throws SQLException;
	
	public void doSaveComposizione (CarrelloBean carrello, int idOrdine) throws SQLException;
}
