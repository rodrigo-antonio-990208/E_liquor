package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;

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
	String action = request.getParameter("action");
	
	if (action != null && (action.equalsIgnoreCase("addC") || action.equalsIgnoreCase("deleteC"))) {
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
	processAction(request,cart);
	request.getSession().setAttribute("carrello", cart);

	JSONObject json = new JSONObject ();
	json.put("status","success");
	
	if(action.equalsIgnoreCase("addC")) {
		
			json.put("message", "Aggiunto al carrello");
			}
	else 
			{json.put("message", "Eliminato dal carrello");
			}
	
	out.write(json.toString());
	return;
	}
	
	
	processAction(request,cart);
	
	if (request.getParameter("action") != null) {
	if (request.getParameter("action").equalsIgnoreCase("vediCarrello")) {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/view/VistaCarrello.jsp");
		dispatcher.forward(request, response);
		return;
	}}
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
	
		}
	}catch (SQLException e) {
	System.err.println ("Errore "+ e.getMessage());
}}



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
	String categoria = request.getParameter("categoria");
	
	try {
		if (categoria != null && !categoria.trim().isEmpty()) {
			request.setAttribute("prodotti", dao.doRetrieveByCategoria(Integer.parseInt(categoria)));
			
		}
		else {
		request.setAttribute("prodotti", dao.doRetrieveAll(sort));
		}
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
}

protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request,response);
}

}
