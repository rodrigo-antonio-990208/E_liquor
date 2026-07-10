package controller;

import java.sql.SQLException;
import javax.sql.DataSource;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import model.Prodotto;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

@WebServlet("/GestioneProdottoServlet")
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
	
	private void processAction (HttpServletRequest request) throws  ServletException{
		String action = request.getParameter("action");
		try {
		if (action != null) {
			if (action.equalsIgnoreCase ("aggiungi")) {
				aggiungiProdotto(request);
			}
			else if (action.equalsIgnoreCase("delete")) {
				
				eliminaProdotto(request);
			}
			else if (action.equalsIgnoreCase("read")) {
				leggiProdotto(request);
			}
		}
	}catch (SQLException e) {
		System.err.println("error: "+e.getMessage());
}}
	
	private void aggiungiProdotto (HttpServletRequest request)throws SQLException {
		Prodotto prod = new Prodotto ();
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		float prezzo = Float.parseFloat(request.getParameter("prezzo"));
		int quant = Integer.parseInt(request.getParameter("quantita"));
		float gradazione = Float.parseFloat(request.getParameter("gradazione"));
		int formato = Integer.parseInt(request.getParameter("formato"));
		int idCategoria = Integer.parseInt(request.getParameter("categoria"));
		prod.setNome(nome);
		prod.setDescrizione(descrizione);
		prod.setPrezzo(prezzo);
		prod.setQuantita(quant);
		prod.setGradazione(gradazione);
		prod.setFormato(formato);
		prod.setIdCategoria(idCategoria);
		prod.setAttivo(true);
		dao.doSave(prod);		
				
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
		if (action != null && (action.equalsIgnoreCase("aggiungi") || action.equalsIgnoreCase("delete")) ) {
			processAction(request);
			response.sendRedirect(request.getContextPath()+"/GestioneProdottoServlet");
			return;
		}
    	
    	processAction(request);
		
		loadProductPage(request);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/GestioneProdotti.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		
	}

}
