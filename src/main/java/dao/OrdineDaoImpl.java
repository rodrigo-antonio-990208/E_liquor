package dao;

import dao.OrdineDao;
import model.CarrelloBean;
import model.OrdineBean;
import model.Prodotto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

public class OrdineDaoImpl implements OrdineDao{
	
	private static final String TABLE_NAME = "ordine";
	private DataSource ds ;
	
	public OrdineDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	public int doSave(OrdineBean ordine) throws SQLException {
		String sql = "INSERT INTO "+TABLE_NAME+" (indirizzo_spedizione, metodo_pagamento, totale, stato, id_utente) VALUES (?, ?, ?, ?, ?)";
		int IdOrdine;
		try (Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
			
			ps.setString(1, ordine.getIndirizzo());
			ps.setString(2,ordine.getPagamento());
			ps.setFloat(3,ordine.getTotale());
			ps.setInt(5, ordine.getIdUtente());
			ps.executeUpdate();
			
			try(ResultSet rs = ps.getGeneratedKeys()){
				if(rs.next()) {
					IdOrdine = rs.getInt(1);
					return IdOrdine;
				}
			}
		}
		
	return -1;
	}
	
	public OrdineBean doRetrieveByKey (int codice) throws SQLException {
		String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id_ordine = ?";
		OrdineBean ordine = null;
		try(Connection conn = ds.getConnection ();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, codice);
			try(ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					ordine = new OrdineBean();
					ordine.setIdOrdine(rs.getInt("id_ordine"));
					ordine.setData(rs.getTimestamp("data_ordine"));
					ordine.setIdUtente(rs.getInt("id_utente"));
					ordine.setIndirizzo(rs.getString("indirizzo_spedizione"));
					ordine.setPagamento(rs.getString("metodo_pagamento"));
					ordine.setStato(rs.getString("stato"));
					ordine.setTotale(rs.getFloat("totale"));
				}
			}
		}
		return ordine;
	}
	
	public List<OrdineBean> doRetrieveByUser (int codiceUtente) throws SQLException {
		String sql = "SELECT * FROM "+ TABLE_NAME+" WHERE id_utente = ?";
		OrdineBean ordine= null;
		List<OrdineBean> ordini = new ArrayList<>();
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, codiceUtente);
			try(ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					ordine = new OrdineBean();
					
					ordine.setIdOrdine(rs.getInt("id_ordine"));
					ordine.setData(rs.getTimestamp("data_ordine"));
					ordine.setIdUtente(rs.getInt("id_utente"));
					ordine.setIndirizzo(rs.getString("indirizzo_spedizione"));
					ordine.setPagamento(rs.getString("metodo_pagamento"));
					ordine.setStato(rs.getString("stato"));
					ordine.setTotale(rs.getFloat("totale"));
					ordini.add(ordine);
				}
			}
		}
		return ordini;
	}
	
	public List<OrdineBean> doRetrieveAll() throws SQLException{
		List <OrdineBean> ordini = new ArrayList<>();
		String sql = "SELECT * FROM "+TABLE_NAME;
		try(Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)){
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					OrdineBean ordine = new OrdineBean();
					ordine.setIdOrdine(rs.getInt("id_ordine"));
					ordine.setData(rs.getTimestamp("data_ordine"));
					ordine.setIdUtente(rs.getInt("id_utente"));
					ordine.setIndirizzo(rs.getString("indirizzo_spedizione"));
					ordine.setPagamento(rs.getString("metodo_pagamento"));
					ordine.setStato(rs.getString("stato"));
					ordine.setTotale(rs.getFloat("totale"));
					ordini.add(ordine);
				}
			}
		}
		return ordini;
	}
	
	public void doSaveComposizione(CarrelloBean carrello ,int id_ordine) throws SQLException{
		String sql = "INSERT INTO dettagli_ordine (id_ordine, id_prodotto, prezzo_acquisto, quantità) VALUES (?, ?, ?, ?)";
		List<Prodotto> cart = carrello.getProdotti();
		List <Integer> i = new ArrayList<>();
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
		if (cart != null || !cart.isEmpty()) {
			for (Prodotto p : cart) {
				 
					if (!i.contains(p.getIdProdotto())) {
			ps.setInt(2,p.getIdProdotto());
			ps.setInt(1, id_ordine);
			ps.setFloat(3, p.getPrezzo());
			ps.setInt(4,carrello.getQuantitaProd(p.getIdProdotto()));
			i.add(p.getIdProdotto());
			ps.executeUpdate();
			}	}	}	}
		
	}

}
	

