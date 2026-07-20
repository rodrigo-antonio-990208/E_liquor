<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.Prodotto , model.UtenteBean"%>
    
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel = "stylesheet" type="text/css" href ="${pageContext.request.contextPath}/styles/style.css">
<title>DettagliProdotto</title>
</head>

<body>
<jsp:include page = "header.jsp"/>

<%UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
Prodotto prod = (Prodotto) request.getAttribute("prodotto");
if (prod != null){
%>


<h2>Dettagli Prodotto</h2>


<div class ="prod-dett">

<div class = "dettaglio-left">
<img src = "Immagini?action=show&codice=${prodotto.idProdotto}" alt ="${prod.nome }" width = "400" height = "400">
</div>

<div class = "dettaglio-right">
<div id= "actionC"></div>

<%if (prod.getQuant() <= 10){%>
<h3> ! Pochi pezzi rimasti, affrettati ad acquistarlo ! </h3>
	<%}%>

	<p class = "dett-nome">${prodotto.nome}</p>
	<p class = "dett-descrizione">${prodotto.descrizione}</p>
	<p class = "dett-formato">Formato: ${prodotto.formato} ml</p>
	<p class = "dett-prezzo">${prodotto.prezzo} €</p>
	<p class = "dett-gradazione">Gradazione: ${prodotto.gradazione} % </p>


		<div class = "dett-azioni">
 		<%if (utente != null && utente.getRuolo().equalsIgnoreCase("user")){%>

		<button onclick= "aggiungiCarrello(${prod.idProdotto})">Aggiungi al Carrello</button> 

		<%} else if (utente == null) {%>

		<button onclick= "avvisoRegistrazione()">Aggiungi al Carrello</button>

		<%} %>

		<a href = "${pageContext.request.contextPath}/catalogo"><button>Torna al Catalogo</button></a> 
		</div>
		
	</div>
		
</div>
<%} %>

<jsp:include page = "footer.jsp"/>
<script src ="scripts/aggiungiCarrello.js"></script>

</body>
</html>