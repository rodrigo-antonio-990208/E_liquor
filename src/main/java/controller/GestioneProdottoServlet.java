package controller;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.json.JSONObject;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import model.Prodotto;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

@WebServlet("/admin/GestioneProdottoServlet")
public class GestioneProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProdottoDao dao;

	public void init (ServletConfig servletConfig)throws ServletException {
		super.init(servletConfig);
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if (ds == null) {
			throw new ServletException ("DataSorce non disponibile");
		}
		dao = new ProdottoDaoImpl(ds);
	}
	
	
	
	private void  processAction (HttpServletRequest request) throws  ServletException{
		String action = request.getParameter("action");
		try {
		if (action != null) {
				
		 if (action.equalsIgnoreCase("read")) {
				leggiProdotto(request);
			}
		 else if ("delete".equalsIgnoreCase(action)) {
			 eliminaProdotto(request);
		 }
		}
	}catch (SQLException e) {
		System.err.println("error: "+e.getMessage());
}}
	

	
	private JSONObject modificaProdotto (HttpServletRequest request , HttpServletResponse response)throws SQLException {
	
		JSONObject json = new JSONObject();
		Prodotto prod = new Prodotto ();
		
		
		String cod = request.getParameter("codice");
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String prezzo = (request.getParameter("prezzo"));
		String quant = (request.getParameter("quantita"));
		String gradazione = (request.getParameter("gradazione"));
		String formato =(request.getParameter("formato"));
		String idCategoria = (request.getParameter("categoria"));
		
		String errors = "";
		
		if (cod == null || cod.trim().isEmpty()) {errors += "il campo codice è vuoto"; }
		if (nome == null || nome.trim().isEmpty()) {errors += "il campo Nome non puo' essere vuoto <br>";}
		if (descrizione == null || descrizione.trim().isEmpty()) {errors += "il campo Descrizione non puo' essere vuoto <br>";}
		if (prezzo == null || prezzo.trim().isEmpty() ) {errors += "il campo Prezzo non puo' essere vuoto <br>";}
		if (quant == null || quant.trim().isEmpty()) {errors += "il campo Quantità non puo' essere vuoto <br>";}
		if (gradazione == null || gradazione.trim().isEmpty()) {errors += "il campo Gradazione non puo' essere vuoto <br>";}
		if (formato == null || formato.trim().isEmpty()) {errors += "il campo Formato non puo' essere vuoto <br>";}
		if (idCategoria == null || idCategoria.trim().isEmpty()) {errors += "il campo Categoria non puo' essere vuoto <br>";}
		
		if (!errors.isEmpty()) {
			json.put("status", "error");
			json.put("message", errors);
			return json;
		}
	
		try {		
			
		float price = 	Float.parseFloat(prezzo);
		int quantity = Integer.parseInt(quant);
		float grads = Float.parseFloat(gradazione);
		int form = Integer.parseInt(formato);
		int codice = Integer.parseInt(cod);
		
		if (price < 0 || quantity < 0) {
			json.put("status", "error");
			json.put("message", "prezzo e quantità non possono essere negativi ");
		return json;
		}
		
		
		prod.setIdProdotto(codice);
		prod.setNome(nome);
		prod.setDescrizione(descrizione);
		prod.setPrezzo(price);
		prod.setQuantita(quantity);
		prod.setGradazione(grads);
		prod.setFormato(form);
		prod.setIdCategoria(idCategoria);
		prod.setAttivo(true);
		
		dao.doUpdate(prod);		
		
		
		json.put("redirect",request.getContextPath()+"/catalogo");
		json.put("status", "success");
		
		}catch (NumberFormatException e) {
			System.err.println ("errore"+e.getMessage());
			json.put("status", "error");
			json.put("message", "inserire numeri validi");
			
		}catch (SQLException e) {
			json.put("status", "error");
			json.put("message", "errore del server");
		}
		
		
		return json;
	}
	
	
	
	private JSONObject aggiungiProdotto (HttpServletRequest request , HttpServletResponse response)throws SQLException {
		
		JSONObject json = new JSONObject();
		Prodotto prod = new Prodotto ();
		
		String cod = request.getParameter("codice");
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String prezzo = (request.getParameter("prezzo"));
		String quant = (request.getParameter("quantita"));
		String gradazione = (request.getParameter("gradazione"));
		String formato =(request.getParameter("formato"));
		String idCategoria = (request.getParameter("categoria"));
		
		
		String errors = "";
		
		if (nome == null || nome.trim().isEmpty()) {errors += "il campo Nome non puo' essere vuoto <br>";}
		if (descrizione == null || descrizione.trim().isEmpty()) {errors += "il campo Descrizione non puo' essere vuoto <br>";}
		if (prezzo == null || prezzo.trim().isEmpty() ) {errors += "il campo Prezzo non puo' essere vuoto <br>";}
		if (quant == null || quant.trim().isEmpty()) {errors += "il campo Quantità non puo' essere vuoto <br>";}
		if (gradazione == null || gradazione.trim().isEmpty()) {errors += "il campo Gradazione non puo' essere vuoto <br>";}
		if (formato == null || formato.trim().isEmpty()) {errors += "il campo Formato non puo' essere vuoto <br>";}
		if (idCategoria == null || idCategoria.trim().isEmpty()) {errors += "il campo Categoria non puo' essere vuoto <br>";}
		
		if (!errors.isEmpty()) {
			json.put("status", "error");
			json.put("message", errors);
			return json;
		}
		try {
		float price = 	Float.parseFloat(prezzo);
		int quantity = Integer.parseInt(quant);
		float grads = Float.parseFloat(gradazione);
		int form = Integer.parseInt(formato);
		
		if (price < 0 || quantity <0) {
			json.put("status", "error");
			json.put("message", "prezzo e quantità non possono essere negativi ");
		return json;
		}
		
		
		prod.setNome(nome);
		prod.setDescrizione(descrizione);
		prod.setPrezzo(price);
		prod.setQuantita(quantity);
		prod.setGradazione(grads);
		prod.setFormato(form);
		prod.setIdCategoria(idCategoria);
		prod.setAttivo(true);
		dao.doSave(prod);		
		
		
		json.put("redirect",request.getContextPath()+"/admin/GestioneProdottoServlet");
		json.put("status", "success");
		
		}catch (NumberFormatException e) {
			System.err.println ("errore"+e.getMessage());
			json.put("status", "error");
			json.put("message", "inserire numeri validi");
			
		}catch (SQLException e) {
			json.put("status", "error");
			json.put("message", "errore del server");
		}
		
		
		return json;
	}
	
	
	private void eliminaProdotto(HttpServletRequest request) throws SQLException {
		dao.doDelete(Integer.parseInt(request.getParameter("codice")));
	}
	
	private void leggiProdotto(HttpServletRequest request) throws SQLException {
		int codice = Integer.parseInt(request.getParameter("codice"));
		request.setAttribute( "prodotto", dao.doRetrieveByKey(codice));
	}
	
	private void loadProductPage(HttpServletRequest request) {
		String ordine = request.getParameter("sort");
		try {
			request.setAttribute("prodotti", dao.doRetrieveAll(ordine));
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
    	processAction(request);
		
		loadProductPage(request);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GestioneProdotto.jsp");
		dispatcher.forward(request, response);
	}
    
    
    
    
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject ();
		
		response.setContentType("application/json");
		
		String action = request.getParameter("action");
		
		try {
		if ("aggiungi".equalsIgnoreCase(action)) {
			json = aggiungiProdotto(request, response);
	
			
		}
		
		else if ("delete".equalsIgnoreCase(action)) {
			eliminaProdotto(request);
		
			json.put("status","success");
			json.put("redirect", request.getContextPath()+"/catalogo");
		}
		
		else if ("modifica".equalsIgnoreCase(action)) {
			json= modificaProdotto(request, response);	
			
		}
		
		else  {
			json.put("status", "error");
			json.put("message", "azione non riconosciuta");
		}
		
		}catch (SQLException e) {
			json.put("status", "error");
			json.put("message", "errore dal server");
		}
		
		
		out.print(json.toString());
		
	}

}
