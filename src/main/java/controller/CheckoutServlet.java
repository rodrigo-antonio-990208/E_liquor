package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import javax.sql.DataSource;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.CarrelloBean;
import dao.OrdineDao;
import dao.UtenteDao;
import dao.UtenteDaoImpl;
import dao.OrdineDaoImpl;
import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import model.UtenteBean;
import model.OrdineBean;
import model.Prodotto;


@WebServlet("/common/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private OrdineDao dao; 
     private ProdottoDao prodotto;
    
	
  public void init (ServletConfig config) throws ServletException{
	  super.init(config);
	  DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	  if (ds != null) {
		  dao = new OrdineDaoImpl(ds);
		  prodotto = new ProdottoDaoImpl(ds);
	  }
	  else throw new ServletException("data Source non trovato");
  }
  
  
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
RequestDispatcher disp = request.getRequestDispatcher("/WEB-INF/view/Checkout.jsp");
disp.forward(request, response);
	}





protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	response.setContentType("application/json");
	PrintWriter out = response.getWriter();
	JSONObject json = new JSONObject();
	List<String> errors = new ArrayList<>();
	
	UtenteBean utente = (UtenteBean)request.getSession().getAttribute("utente");
	if (utente == null) {
	json.put("status", "error");
	json.put("redirect", "Login.jsp");
	out.print(json.toString());
	
		return;
		}
	
	CarrelloBean cart = (CarrelloBean) request.getSession().getAttribute("carrello");
	
	if (cart == null) {
		json.put ("status", "error");
		json.put("message", "il carrello è vuoto");
		out.print(json.toString());
		return;
	}
	
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
	
	
	if (!errors.isEmpty()) {
		json.put("status", "error");
		json.put("message", String.join("<br>",errors));
		out.print(json.toString());
		return;
	}
	
	 try {
		 if (cart != null && cart.getProdotti() != null ) {
			 
			 Set <Integer> idUnici = new HashSet<>();
				
			 for (Prodotto p : cart.getProdotti()) {
				
					idUnici.add( p.getIdProdotto());
				
				 }
		
			 	for (int i : idUnici) {	 
			
			 			Prodotto tmp = prodotto.doRetrieveByKey(i);
			 
			 					if (tmp == null) {
			 						json.put("status", "error");
			 						json.put("message", "un prodotto non è piu' nel carrello");
			 						out.print(json.toString());
			 						return;
			 					}
			 						if (tmp.getQuant() < cart.getQuantitaProd(i)) {
			 							json.put("status", "error");
			 							json.put("message", "Quantità inserita non disponibile");
			 							out.print(json.toString());
			 							return;
			 						} 	 }
		
		
			 	for (int i : idUnici) {
			 		prodotto.decrementaQuantità(cart.getQuantitaProd(i), i);
			 	}
	
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
	
	json.put("status", "success");
	json.put("redirect",request.getContextPath()+"/Successo");
	out.print(json.toString());
	
	
		 }}catch (SQLException e) {
		System.err.println("errore"+e.getMessage());
		
		json.put("status", "error");
		json.put("message", "problema con il server");
		out.print(json.toString());
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
