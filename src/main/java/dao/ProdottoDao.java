package dao;

import java.sql.SQLException;
import java.util.Collection;
import model.Prodotto;

public interface ProdottoDao {
	
	public void doSave (Prodotto prod) throws SQLException;
	
	public boolean doUpdateImage (Prodotto prod) throws SQLException;
	
	public boolean doDelete (int codice) throws SQLException;
	
	public Prodotto doRetrieveByKey (int codice) throws SQLException;
	
	public Collection <Prodotto> doRetrieveAll (String ordine) throws SQLException;
	
	public Collection <Prodotto> doRetrieveByCategoria(String categoria) throws SQLException;

	public void decrementaQuantità (int quant, int codice)throws SQLException;

	public void doUpdate (Prodotto codice) throws SQLException;
}
