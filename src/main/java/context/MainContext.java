package context;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.sql.DataSource;


public class MainContext implements ServletContextListener {
	
public void contextInitialized(ServletContextEvent con) {
	DataSource ds = null;
	ServletContext context = con.getServletContext();
	try {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		ds = (DataSource) envCtx.lookup ("jdbc/e_liquor");
	}catch (NamingException e) {
		System.out.println ("errore" + e.getMessage());
	}
	context.setAttribute ("DataSource", ds);
}
	
public void contextDestroyed(ServletContextEvent conn) {
	
}
}
