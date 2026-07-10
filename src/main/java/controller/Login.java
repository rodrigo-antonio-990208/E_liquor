package controller;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	List <String> errors = new ArrayList<>();
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	username = validateField(username, "username", errors);
	password = validateField(password, "password", errors);
		RequestDispatcher dispatcher = request.getRequestDispatcher ("/WEB-INF/view/Login.jsp");
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors );
			dispatcher.forward(request, response);
			return;  /*per non eseuguire il resto del codice*/
		}
		
		String digest = Login.toDigest(password);
	
		try{
			UtenteBean utente = dao.doRetrieveByLogin(username, digest);
			if (utente !=null) {
				request.getSession().setAttribute("utente", utente);
				
		if (utente.getRuolo().equalsIgnoreCase("admin")) {
			request.getSession().setAttribute ("ruolo","admin");
			response.sendRedirect("admin/welcome");
		}
		else if (utente.getRuolo().equalsIgnoreCase("user")) {
			request.getSession().setAttribute("ruolo", "user");
			response.sendRedirect("common/welcome");
		}}
		else  {
			errors.add("Le credenziali non sono corrette");
			request.setAttribute("errors", errors);
			dispatcher.forward(request,response);
		} 
			}
		
		catch (SQLException e) {
			System.err.println ("errore"+e);
		}
	}
	
	private String validateField (String value , String field, List<String> erros ) {
		if (value == null || value.trim().isEmpty()){
			erros.add("il campo "+field +" non puo' essere vuoto");
			return "";
		}
		return value.trim();
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
