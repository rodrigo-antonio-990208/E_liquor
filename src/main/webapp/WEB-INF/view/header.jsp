<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "model.UtenteBean" %>
<%
UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
String ruolo = "";
if (utente != null){
 ruolo = utente.getRuolo();
}%>
<header>
<div class = "nav_container">
<div>
*/ aggiungere immagine */</div>
<nav>
<ul>
<li><a href = "${pageContext.request.contextPath}/catalogo"> HOME</a></li>
<li><a href = "Gin.jsp">GIN</a></li>
<li><a href = "Tequila.jsp">TEQUILA</a></li>
<li><a href = "Whiskey.jsp">WHISKEY</a></li>
<li><a href = "Liquori.jsp">LIQUORI</a></li>
<li><a href = "Drink.jsp">DRINKS</a></li>
<%if (utente == null){ %>
<li><a href = "Login.jsp">ACCEDI</a></li>
<li><a href = "Registrazione.jsp">REGISTRATI</a></li>
<%} %>
<% if (ruolo.equalsIgnoreCase("admin")){ %>
<li><a href = "${pageContext.request.contextPath}/GestioneProdottoServlet">AREA ADMIN</a></li>
<li><a href = "${pageContext.request.contextPath}/Logout">LOGOUT</a></li>
	<% } else if (ruolo.equalsIgnoreCase("user")){ %>
	<li><a href = "${pageContext.request.contextPath}/Logout">LOGOUT</a></li>
	<li><a href= "vistaCarrello.jsp">CARRELLO</a></li>
	<% } %>
</ul></nav>

 </div>

</header>