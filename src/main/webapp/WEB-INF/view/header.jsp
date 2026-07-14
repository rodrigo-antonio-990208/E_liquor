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
<script>const contextPath = "${pageContext.request.contextPath}"
</script>
<div class = "nav_container">
<div>
*/ aggiungere immagine */</div>
<nav>
<ul>
<li><a href = "${pageContext.request.contextPath}/catalogo"> HOME</a></li>
<li><a href = "${pageContext.request.contextPath}/catalogo?categoria=gin">GIN</a></li>
<li><a href = "${pageContext.request.contextPath}/catalogo?categoria=tequila">TEQUILA</a></li>
<li><a href = "${pageContext.request.contextPath}/catalogo?categoria=whiskey">WHISKEY</a></li>
<li><a href = "${pageContext.request.contextPath}/catalogo?categoria=liquori">LIQUORI</a></li>

<%if (utente == null){ %>
<li><a href = "${pageContext.request.contextPath}/index">ACCEDI</a></li>
<li><a href = "${pageContext.request.contextPath}/Registrazione">REGISTRATI</a></li>

<%} %>


<% if (ruolo.equalsIgnoreCase("admin")){ %>
<li><a href = "${pageContext.request.contextPath}/admin/GestioneProdottoServlet">AREA ADMIN</a></li>
<li><a href = "${pageContext.request.contextPath}/admin/GestioneOrdini">GESTIONE ORDINI</a></li>
<li><a href = "${pageContext.request.contextPath}/Logout">LOGOUT</a></li>


	<% } else if (ruolo.equalsIgnoreCase("user")){ %>

	
	<li><a href = "${pageContext.request.contextPath}/common/MieiOrdini">I MIEI ORDINI</a></li>
	<li><a href= "${pageContext.request.contextPath}/catalogo?action=vediCarrello">CARRELLO</a></li>
	<li><a href = "${pageContext.request.contextPath}/Logout">LOGOUT</a></li>
	
	
	<% } %>
	
</ul></nav>

 </div>

</header>