package controller;

import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletConfig;

import java.io.*;
import javax.sql.DataSource;

import dao.UtenteDao;
import dao.UtenteDaoImpl;
import model.UtenteBean;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UtenteDao dao;
	
	public void init (ServletConfig config)throws ServletException {
		super.init(config);
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if (ds == null) {
			throw new ServletException ("data source non trovato");
		}
		dao = new UtenteDaoImpl(ds);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	RequestDispatcher dis = getServletContext().getRequestDispatcher("/WEB-INF/view/Login.jsp");
	dis.forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType ("application/json");
	PrintWriter out = response.getWriter();
	
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	
		JSONObject json = new JSONObject();

			
			if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			json.put("status", "error");
			json.put("message", "compila tutti i campi");
			
			out.print(json.toString());
			
			return;  /*per non eseuguire il resto del codice*/
		}
		
		String digest = Login.toDigest(password);
	
		try{
			UtenteBean utente = dao.doRetrieveByLogin(username, digest);
			if (utente !=null) {
				request.getSession().setAttribute("utente", utente);
				json.put("status", "success");
				
		if (utente.getRuolo().equalsIgnoreCase("admin")) {
			request.getSession().setAttribute ("ruolo","admin");
			json.put("redirect", "admin/welcome");
		}
		else if (utente.getRuolo().equalsIgnoreCase("user")) {
			request.getSession().setAttribute("ruolo", "user");
			json.put("redirect", "common/welcome");
		}}
			
		else  {
			json.put("status", "error");
			json.put("message", "nome o password errati");
		} 
			}
		
		catch (SQLException e) {
			System.err.println ("errore"+e);
			
			json.put("status", "error");
			json.put("message", "errore interno del server");
		}
		
		out.print(json.toString());
	}
	
	

	
	public static String toDigest (String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte [] digestBytes = md.digest (password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb =new StringBuilder();
			for (byte b : digestBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		}catch (NoSuchAlgorithmException e) {
			throw new RuntimeException ("Algoritmo SHA_512 non esiste", e);
		}
	}

}
