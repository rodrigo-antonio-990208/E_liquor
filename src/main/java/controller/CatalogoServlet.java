package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.CarrelloBean;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import model.Prodotto;
@WebServlet("/catalogo")

public class CatalogoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ProdottoDao dao;
	
	
public void init (ServletConfig servletConfig) throws ServletException{
	super.init(servletConfig);
	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	if (ds == null) {
		throw new ServletException("datasource non disponibile nel contesto");
	}
	dao = new ProdottoDaoImpl(ds);
}

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	CarrelloBean cart = (CarrelloBean) request.getSession().getAttribute("carrello");
	if (cart == null) {
		cart = new CarrelloBean();
		request.getSession().setAttribute("carrello", cart);
	}
	processAction(request,cart);
	
	request.getSession().setAttribute("carrello", cart);
	loadProductList (request);
	
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/VistaProdotti.jsp");
	dispatcher.forward(request, response);

}

private void processAction (HttpServletRequest request, CarrelloBean cart) {
String action = request.getParameter ("action");
try {
	if (action != null) {
		if (action.equalsIgnoreCase("addC")) {
			aggiungiCarrello (request, cart);
		}
		else if (action.equalsIgnoreCase("deleteC")) {
			rimuoviCarrello(request, cart);
		}
		else if (action.equalsIgnoreCase("read")) {
			leggiProdotto(request);
		}
		else if (action.equalsIgnoreCase("delete")) {
			eliminaProdotto(request);
		}
		else if (action.equalsIgnoreCase("aggiungi")) {
			aggiungiProdotto(request);
		}
	}
}catch (SQLException e) {
	System.err.println ("Errore "+ e.getMessage());
}
}

private void aggiungiProdotto (HttpServletRequest request) throws SQLException{
	String nome = request.getParameter("nome");
	String descrizione = request.getParameter ("descrizione");
	float prezzo = Float.parseFloat(request.getParameter("prezzo"));
	int quant = Integer.parseInt (request.getParameter("quantita"));
	boolean attivo = Boolean.parseBoolean(request.getParameter("attivo"));
	int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
	float gradazione = Float.parseFloat(request.getParameter("gradazione"));
	int formato = Integer.parseInt(request.getParameter("formato"));
	Prodotto prod = new Prodotto ();
	prod.setNome(nome);
	prod.setDescrizione(descrizione);
	prod.setFormato(formato);
	prod.setIdCategoria(idCategoria);
	prod.setGradazione(gradazione);
	prod.setPrezzo(prezzo);
	prod.setQuantita(quant);
	prod.setAttivo(attivo);
	dao.doSave(prod);
	
}

private void eliminaProdotto (HttpServletRequest request) throws SQLException {
int codice = Integer.parseInt(request.getParameter("codice"));
dao.doDelete(codice);
}

private void leggiProdotto( HttpServletRequest request) throws SQLException {
	int codice = Integer.parseInt(request.getParameter("codice"));
	request.setAttribute("prodotto", dao.doRetrieveByKey(codice));
}

private void rimuoviCarrello (HttpServletRequest request, CarrelloBean cart) throws SQLException {
int codice = Integer.parseInt(request.getParameter("codice"));
cart.rimuoviProdotto(dao.doRetrieveByKey(codice));
}

private void aggiungiCarrello (HttpServletRequest request, CarrelloBean cart) throws SQLException {
	int codice = Integer.parseInt(request.getParameter("codice"));
	cart.addProdotto(dao.doRetrieveByKey(codice));
}

private void loadProductList (HttpServletRequest request) {
	String sort = request.getParameter("sort");
	
	try {
		request.setAttribute("prodotti", dao.doRetrieveAll(sort));
	}catch(SQLException e) {
		
	}
}

protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request,response);
}

}
