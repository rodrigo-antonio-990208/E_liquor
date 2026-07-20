package dao;

import java.sql.SQLException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


import model.UtenteBean;

public class UtenteDaoImpl implements UtenteDao {

	private final static String TABLE_NAME = "utente";
	private DataSource ds;
	
	public UtenteDaoImpl(DataSource ds) {
		this.ds=ds;
	}
	
	
	public void doSave (UtenteBean utente) throws SQLException{
		String sql = "INSERT INTO "+TABLE_NAME+" (nome, cognome, email, password, ruolo)  VALUES (?, ?, ?, ?, ?)";
		try(Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)){
		
		ps.setString(1, utente.getNome());
		ps.setString(2, utente.getCognome());
		ps.setString(3, utente.getEmail());
		ps.setString(4, utente.getPassword());
		ps.setString(5, utente.getRuolo());
		ps.executeUpdate();
		}
	}


	@Override
	public boolean doDelete(int codice) throws SQLException {
		int modifica ;
		String sql = "DELETE FROM "+TABLE_NAME+ " WHERE id_utente = ?";
		try (Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
				ps.setInt(1,codice);
				 modifica = ps.executeUpdate();	
		}
		return modifica != 0;
	}


	@Override
	public UtenteBean doRetrieveByKey(int codice) throws SQLException {
		UtenteBean utente = null;
		String sql = "SELECT * FROM "+ TABLE_NAME+ " WHERE id_utente = ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement (sql)){
			ps.setInt(1, codice);
		try(ResultSet rs = ps.executeQuery() ){
			if (rs.next()) {
				utente = new UtenteBean();
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
				utente.setEmail(rs.getString("email"));
				utente.setPassword(rs.getString("password"));
				utente.setDataRegistrazione(rs.getTimestamp("data_registrazione"));
				utente.setIdUtente(rs.getInt("id_utente"));
				utente.setRuolo(rs.getString("ruolo"));
			}
		}}
	return utente;	
	}

	@Override
public UtenteBean doRetrieveByMail(String email) throws SQLException{
	UtenteBean utente = null;
	String sql = "SELECT * FROM "+TABLE_NAME+" WHERE email = ?";
	try(Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);){
		ps.setString(1, email);
		try(ResultSet rs = ps.executeQuery()){
			if (rs.next()) {
				utente = new UtenteBean();
				utente.setCognome(rs.getString("cognome"));
				utente.setNome(rs.getString("nome"));
				utente.setEmail(rs.getString("email"));
				utente.setRuolo(rs.getString("ruolo"));
				utente.setPassword(rs.getString("password"));
				utente.setIdUtente(rs.getInt("id_utente"));
				utente.setDataRegistrazione(rs.getTimestamp("data_registrazione"));
			}
		}
	}
	return utente;
}
	
	@Override
	public UtenteBean doRetrieveByLogin(String email, String password) throws SQLException {
		UtenteBean utente = null;
		String sql = "SELECT * FROM "+TABLE_NAME + " WHERE email = ? AND password = ?";
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
				System.out.println("====== ATTENZIONE =====> JAVA STA USANDO IL DATABASE: " + conn.getMetaData().getURL());
					ps.setString(1, email);
					ps.setString(2, password);
					try (ResultSet result = ps.executeQuery()){
						
						if (result.next()) {
							utente = new UtenteBean();
							utente.setNome(result.getString("nome"));
							utente.setCognome(result.getString("cognome"));
							utente.setEmail(result.getString("email"));
							utente.setDataRegistrazione(result.getTimestamp("data_registrazione"));
							utente.setRuolo(result.getString("ruolo"));
							utente.setPassword(result.getString("password"));
							utente.setIdUtente(result.getInt("id_utente"));
						}}}}
	return utente;
	}
	
	}
