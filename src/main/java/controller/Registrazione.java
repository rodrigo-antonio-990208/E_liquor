package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sql.DataSource;

import org.json.JSONObject;

import java.sql.SQLException;
import jakarta.servlet.ServletConfig;
import java.util.List;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;

import dao.UtenteDao;
import dao.UtenteDaoImpl;
import model.UtenteBean;
import controller.Login;

@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
   UtenteDao dao;
   
	
 public void init (ServletConfig servletCon) throws ServletException {
	 super.init(servletCon);
	 DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	 if (ds != null) {
		 dao = new UtenteDaoImpl(ds);
	 }
	 else 
		 throw new ServletException ("Data Source non trovato");
 }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/registrazione.jsp").forward(request,response);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errori = new ArrayList<String>();
		response.setContentType("application/json");
		
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		
		JSONObject json = new JSONObject ();
		PrintWriter out = response.getWriter();
		
		email = validateField(email,"username",errori);
		password = validateField(password, "password", errori);
		nome = validateField(nome, "nome", errori);
		cognome = validateField(cognome, "cognome", errori);
		
		
		if (!errori.isEmpty()) {
			json.put("status", "error");
			json.put("message", String.join("<br>", errori));
			out.print(json.toString());
			return;
		}
		
		
		try {
			UtenteBean utenteEsistente = dao.doRetrieveByMail(email);
			
			if (utenteEsistente != null) {
				json.put("status", "error");
				json.put("message", "utente esistente");
				out.print(json.toString());
				return;
			}
			
		
		
				
		String digest = Login.toDigest(password);
	
			UtenteBean utente = new UtenteBean();
			utente.setNome(nome);
			utente.setCognome(cognome);
			utente.setEmail(email);
			utente.setPassword(digest);
			utente.setRuolo("user");
			
			dao.doSave(utente);
			
			json.put("status", "success");
			json.put("redirect", request.getContextPath()+"/Login");
			out.print(json.toString());
			
		}catch (SQLException e) {
			System.err.println("errore durante il salvataggio dell'utente "+e.getMessage());
			json.put("status", "error");
			json.put("message","errore comunicazione con il database");
			out.print(json.toString());
		}
		
	}
		
	

	private String validateField (String value, String field, List<String> err) {
		if (value == null || value.trim().isEmpty() ) {
			err.add("il campo "+field+ "non è valido");
			return "";
		}
		
		return value.trim();
	}
	
}
