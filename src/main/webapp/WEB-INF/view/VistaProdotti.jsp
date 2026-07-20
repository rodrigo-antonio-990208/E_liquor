<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.UtenteBean"  %>
    
    <%@taglib prefix = "c" uri= "jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel = "stylesheet" type="text/css" href ="${pageContext.request.contextPath}/styles/style.css">


<title>Catalogo lista liquori</title>


</head>
<body>

<% UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");  %>

<jsp:include page = "header.jsp" />

<div id= "actionC"></div>

<div class="sort">
<h3>Ordina Prodotti Per:</h3>
<a href ="catalogo?sort=Id_Prodotto"><button style = "margin-right:5px">ID</button></a>
<a href ="catalogo?sort=nome"><button style = "margin-right:5px">Nome</button></a>
<a href ="catalogo?sort=prezzo"><button style = "margin-right:5px">Prezzo</button></a>
</div>


<div class = "prodotto-grid">
<c:forEach items = "${prodotti}" var= "prod">

	<div class = "prodotto_card">

<a href = "catalogo?action=read&codice=${prod.idProdotto}">
	
	<img src = "Immagini?action=show&codice=${prod.idProdotto}" alt= "${prod.nome}" width= "60" height="60">

	<h3>${prod.nome}</h3>

	<p class = "prod-formato">${prod.formato} ml</p>
	<p class = "prod-prezzo">${prod.prezzo} €</p>
	
</a>
	<%if (utente != null && utente.getRuolo().equalsIgnoreCase("user")){ %>
	<button onclick = "aggiungiCarrello(${prod.idProdotto})">Aggiungi al carrello</button>
	 <%} else if (utente == null){ %>
 
	<button onclick = "avvisoRegistrazione()">Aggiungi al Carrello</button>
 
	 <%} %>
 	</div>
 
</c:forEach>
</div>

<jsp:include page= "footer.jsp"/>

<script src = "scripts/aggiungiCarrello.js"></script>

</body>
</html>