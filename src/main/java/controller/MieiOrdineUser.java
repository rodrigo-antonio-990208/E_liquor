package controller;


import model.*;
import dao.*;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.io.IOException;

/**
 * Servlet implementation class MieiOrdineUser
 */
@WebServlet("/common/MieiOrdini")
public class MieiOrdineUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    OrdineDao ordineDao; 
	
	public void init (ServletConfig config) throws ServletException{
		super.init(config);
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if (ds != null) {
			ordineDao = new OrdineDaoImpl(ds);
		}
		else throw new ServletException ("Data Source non disponibile");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
	
	if (utente != null) {
		
	int codice = utente.getIdUtente();
	try {
		List<OrdineBean> ordine = ordineDao.doRetrieveByUser(codice);
		
		request.setAttribute("ordini", ordine);
		
		RequestDispatcher ds = request.getRequestDispatcher("/WEB-INF/view/MieiOrdini.jsp");
		ds.forward(request, response);
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}		}
	
			else { response.sendRedirect(request.getContextPath()+"/index");
			
						}
}	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
