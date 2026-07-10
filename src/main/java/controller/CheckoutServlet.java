package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import model.CarrelloBean;
import dao.OrdineDao;
import dao.UtenteDao;
import dao.UtenteDaoImpl;
import dao.OrdineDaoImpl;
import model.UtenteBean;
import model.OrdineBean;


@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private OrdineDao dao;  
    
	
  public void init (ServletConfig config) throws ServletException{
	  super.init(config);
	  DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	  if (ds != null) {
		  dao = new OrdineDaoImpl(ds);
	  }
	  else throw new ServletException("data Source non trovato");
  }
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
RequestDispatcher disp = request.getRequestDispatcher("/WEB-INF/view/Checkout.jsp");
disp.forward(request, response);
	}


protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	UtenteBean utente = (UtenteBean)request.getSession().getAttribute("utente");
	if (utente == null) {
		RequestDispatcher dis2 = request.getRequestDispatcher("/WEB-INF/view/Login.jsp");
		dis2.forward(request, response);
		return;
		}
	List <String> errors = new ArrayList<>();
	CarrelloBean cart = (CarrelloBean) request.getSession().getAttribute("carrello");
	String citta = request.getParameter("citta");
	String  cap = request.getParameter("cap");
	String via = request.getParameter("via");
	String provincia = request.getParameter("provincia");
	String paese = request.getParameter("paese");
	String pagamento = request.getParameter("pagamento");
	
	paese = validate(paese, "Paese", errors);
	citta = validate(citta, "Città", errors);
	cap = validate(cap, "Cap", errors);
	via= validate(via,"Via", errors);
	provincia = validate(provincia, "Provincia", errors);
	pagamento = validate (pagamento, "Metodo di Pagamento", errors);
	
	RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/view/Checkout.jsp");
	if (!errors.isEmpty()) {
		request.setAttribute("errors", errors);
		dis.forward(request,response);
		return;
	}

	
	
	 try {
	int id = utente.getIdUtente();
	String indirizzo = paese+", "+ citta+", "+provincia+", "+cap+", "+via;
	OrdineBean ordine = new OrdineBean();
	ordine.setIdUtente(id);
	ordine.setIndirizzo(indirizzo);
	ordine.setPagamento(pagamento);
	ordine.setTotale(cart.getPrezzoTotale());
	int codice = dao.doSave(ordine);
	dao.doSaveComposizione(cart, codice);
	
	request.getSession().removeAttribute("carrello");
	response.sendRedirect("Successo");
	
	}catch (SQLException e) {
		System.err.println("errore"+e.getMessage());
	}

}



private String validate (String value, String field, List<String> err  ) {
	if (value == null || value.trim().isEmpty()) {
		err.add("Riempiere correttamente il campo "+ field);
		return "";
	}
	return value.trim();
}
}
