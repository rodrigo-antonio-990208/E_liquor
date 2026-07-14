package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import model.Prodotto;

public class ProdottoDaoImpl implements ProdottoDao {
	
	private static final String  TABLE_NAME = "prodotto";
	private DataSource ds = null;
	
	public ProdottoDaoImpl (DataSource ds) {
		this.ds  = ds;
	}
	
	public synchronized void doSave (Prodotto prod ) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome, descrizione, quantita_disponibile, prezzo_attuale, gradazione, formato, attivo, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = ds.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)){
			preparedStatement.setString(1, prod.getNome());
			preparedStatement.setString(2, prod.getDescrizione());
			preparedStatement.setInt(3, prod.getQuant());
			preparedStatement.setFloat(4, prod.getPrezzo());
			preparedStatement.setFloat(5, prod.getGradazione());
			preparedStatement.setInt(6, prod.getFormato());
			preparedStatement.setBoolean(7, prod.isAttivo());
			preparedStatement.setString(8, prod.getIdCategoria());
			preparedStatement.executeUpdate();
		}		
	}
	
	public synchronized boolean doUpdateImage (Prodotto prod) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET immagine_url = ?, mime_type = ? WHERE id_prodotto = ?";
		try (Connection conn = ds.getConnection ();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, prod.getImmagineUrl());
			ps.setString(2, prod.getMime());
			ps.setInt(3, prod.getIdProdotto());
			int rows = ps.executeUpdate();
			return rows != 0;
		}}
		
		public synchronized Prodotto  doRetrieveByKey(int codice) throws SQLException {
			Prodotto bean = new Prodotto();
			String  selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE id_prodotto = ?";
			try (Connection conn = ds.getConnection();
					PreparedStatement ps = conn.prepareStatement(selectSql)){
				ps.setInt(1, codice);
					try(ResultSet rs = ps.executeQuery()){
						while (rs.next()) {
							bean.setIdProdotto (rs.getInt("id_prodotto"));
							bean.setIdCategoria (rs.getString("id_categoria"));
							bean.setNome (rs.getString("nome"));
							bean.setAttivo (rs.getBoolean("attivo"));
							bean.setQuantita(rs.getInt("quantita_disponibile"));
							bean.setPrezzo(rs.getFloat("prezzo_attuale"));
							bean.setGradazione(rs.getFloat("gradazione"));
							bean.setFormato(rs.getInt("formato"));
							bean.setDescrizione(rs.getString(("descrizione")));
							bean.setImmagineUrl(rs.getString("immagine_url"));
							bean.setMimeType(rs.getString("mime_type"));
						}
					}
			}
			return bean;
		}
		
		public synchronized List <Prodotto> doRetrieveAll (String ordine) throws SQLException{
			List<Prodotto> prodotti = new LinkedList<> ();
			String selectSql = "SELECT * FROM " + TABLE_NAME+" WHERE attivo = true";
			
			if (ordine != null && !ordine.isEmpty()) {
				String str="" ;
				if (ordine.equalsIgnoreCase("nome")) {
					str= "nome";
				}
				else if (ordine.equalsIgnoreCase("id_prodotto")) {
					str="id_prodotto";
				}
				else if (ordine.equalsIgnoreCase("prezzo")) {
					str="prezzo_attuale";
				}
				
				selectSql += " ORDER BY " + str;
			}
			
			try (Connection conn = ds.getConnection();
					PreparedStatement ps = conn.prepareStatement(selectSql);
				ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
				
					Prodotto bean = new Prodotto();
					bean.setIdProdotto (rs.getInt("id_prodotto"));
					bean.setIdCategoria (rs.getString("id_categoria"));
					bean.setNome (rs.getString("nome"));
					bean.setAttivo (true);
					bean.setQuantita(rs.getInt("quantita_disponibile"));
					bean.setPrezzo(rs.getFloat("prezzo_attuale"));
					bean.setGradazione(rs.getFloat("gradazione"));
					bean.setFormato(rs.getInt("formato"));
					bean.setDescrizione(rs.getString(("descrizione")));
					bean.setImmagineUrl(rs.getString("immagine_url"));
					bean.setMimeType(rs.getString("mime_type"));
					prodotti.add(bean);
				}}
		return prodotti;	
		}
		
		
		
		
		
		public synchronized boolean doDelete(int codice) throws SQLException {
			
			String deleteSql = "UPDATE  " + TABLE_NAME + " SET attivo = ? WHERE id_prodotto = ?";
			
			
			try(Connection conn = ds.getConnection ();
					PreparedStatement ps = conn.prepareStatement(deleteSql)){
				
				ps.setBoolean(1, false);
				ps.setInt(2,codice);
				
				int risultato = ps.executeUpdate();
				
				return risultato != 0;
			}
		}
		
		
		
		
		public synchronized List <Prodotto> doRetrieveByCategoria (String categoria) throws SQLException{
			
			List <Prodotto> prodotti = new LinkedList<>();
			String sql = "SELECT * FROM "+ TABLE_NAME+ " WHERE id_categoria = ?";
			
			
			try (Connection conn = ds.getConnection();
					PreparedStatement ps = conn.prepareStatement(sql)){
				
				ps.setString(1, categoria);
				
				try(ResultSet rs = ps.executeQuery()){
					while (rs.next()) {
						Prodotto bean = new Prodotto();
						bean.setIdProdotto (rs.getInt("id_prodotto"));
						bean.setIdCategoria (rs.getString("id_categoria"));
						bean.setNome (rs.getString("nome"));
						bean.setAttivo (true);
						bean.setQuantita(rs.getInt("quantita_disponibile"));
						bean.setPrezzo(rs.getFloat("prezzo_attuale"));
						bean.setGradazione(rs.getFloat("gradazione"));
						bean.setFormato(rs.getInt("formato"));
						bean.setDescrizione(rs.getString(("descrizione")));
						bean.setImmagineUrl(rs.getString("immagine_url"));
						bean.setMimeType(rs.getString("mime_type"));
						prodotti.add(bean);
				}
			}
		}
			return prodotti;
				
	}
		
		
		public synchronized void decrementaQuantità (int quant, int codice)throws SQLException {
			
			Prodotto p = doRetrieveByKey(codice);
			String sql= "UPDATE "+ TABLE_NAME+ " SET quantita_disponibile = ? WHERE id_prodotto = ?";
			
			
				try (Connection conn = ds.getConnection();
						PreparedStatement ps = conn.prepareStatement(sql)){
					ps.setInt(1,p.getQuant() - quant);
					ps.setInt(2, codice);
					ps.executeUpdate();
						
			}
			 if (p.getQuant () <= quant) {
				sql = "UPDATE  " + TABLE_NAME + " SET attivo = ?  WHERE id_prodotto = ?";
				try (Connection conn = ds.getConnection();
						PreparedStatement ps = conn.prepareStatement(sql)){
					ps.setBoolean(1,false);
					ps.setInt(2, codice);
					ps.executeUpdate();
			}
		}
		
}



}
		





