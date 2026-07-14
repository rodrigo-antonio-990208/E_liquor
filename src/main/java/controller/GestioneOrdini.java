package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import dao.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import model.*;


@WebServlet("/admin/GestioneOrdini")
public class GestioneOrdini extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
     private  OrdineDao ordine;
  
     public void init (ServletConfig config) throws ServletException {
    	 super.init(config);
    	 DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
    	 if (ds != null) {
    		 ordine = new OrdineDaoImpl (ds);
    	 }
    	 else throw new ServletException("impossibile collegarsi al Ds");
     }
     
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUtente = request.getParameter ("idUtente");
		String dataX = request.getParameter ("dataX");
		String dataY = request.getParameter("dataY");
		
		try {
			List <OrdineBean> listaOrdini;
			
			if (idUtente != null && !idUtente.trim().isEmpty()) {
				int id = Integer.parseInt(idUtente);
				listaOrdini = ordine.doRetrieveByUser(id);
			}
			
			else if ((dataX != null && !dataX.trim().isEmpty()) &&  (dataY != null && dataY.trim().isEmpty())) {
				listaOrdini = ordine.doRetrieveByDate(dataX,dataY);
			}
			
			else listaOrdini = ordine.doRetrieveAll();
				
			request.setAttribute("ordini", listaOrdini);
			
		}catch (SQLException e ) {
			
			System.err.println("ERROR: "+e.getMessage());
		
		request.setAttribute("error", "errore con il database :"+e.getMessage());
	}
		
		
	RequestDispatcher disp = request.getRequestDispatcher("/WEB-INF/view/gestioneOrdiniAdmin.jsp");
	disp.forward(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
