package controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class Filter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException {
		String path = request.getServletPath();
		
		if (!path.startsWith ("/admin") && !path.startsWith("/common")) {
			chain.doFilter(request,response);
			return;
		}
		
		HttpSession session = request.getSession(false);
		String ruolo;
		if (session != null) {
		 ruolo =(String) session.getAttribute("ruolo");
		}
		else ruolo = null;
		
		boolean autorizzato = false;
		if (ruolo != null) {
			if (path.startsWith("/admin")) {
				autorizzato = ruolo.equals("admin");
			}
			if (path.startsWith("/common")) {
				autorizzato = ruolo.equals("admin") ||  ruolo.equals("user");
			}
		}
		if (autorizzato != false) {
			chain.doFilter(request, response);
		}
		else response.sendRedirect(request.getContextPath()+"/index");
		}

}