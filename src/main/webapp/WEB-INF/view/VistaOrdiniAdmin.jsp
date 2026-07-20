<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "model.*"%>
    <%@ taglib prefix = "c" uri = "jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestisci Ordini</title>
<link rel = "stylesheet" type="text/css" href ="${pageContext.request.contextPath}/styles/style.css">
</head>



<body>
<jsp:include page = "header.jsp"/>


<div class = "container">
	<h2>Pannello Amministratore - Ricerca Ordini </h2>

<div id = "gestioneAdmin-error"></div>

<div class = "admin-filtri-grid">


<div class = "ricerca-box">

<h4>Ricerca per ID Utente</h4>

<form action = "${pageContext.request.contextPath}/admin/GestioneOrdini" method = "GET" onsubmit = "return validaUtente(event)">

<label for ="idUtente">Inserisci ID Utente</label><br><br>

<div class ="input-group">
<input type = "text" name = "idUtente" id = "idUtente" placeholder = "es. 12">

<button type = "submit"> Cerca</button>
</div>

</form>
</div>

<br>

<div class = "ricerca-box">

<h4>Ricerca per Data</h4>


<form action = "${pageContext.request.contextPath}/admin/GestioneOrdini" method = "GET" onsubmit = "return validaData(event)">

<div class = "date-group">

<label for = "dataX">Da: </label>
<input type = "date" id = "dataX" name = "dataX" >

<label for = "dataY" >A:</label>
<input type = "date" id = "dataY" name = "dataY">

</div>
<button type="submit" >Cerca</button>
</form>

</div>

</div>

<br>
<div class = "azioni-admin">
<a href = "${pageContext.request.contextPath}/admin/GestioneOrdini" class = "btn-reset-ordini"><button type = "button">Mostra Tutti Gli Ordini</button></a>
</div>



<div class= "tavola-responsive">
<table class = "tavola-admin">
<tr>
<th>ID Ordine</th>
<th>ID Utente</th>
<th>Data</th>
<th>Prodotti Acquistati</th>
<th>Totale</th>
<th>Stato</th>
</tr>

<c:forEach items = "${ordini}" var = "ord">
<tr>
<td class = "text-center">${ord.idOrdine}</td>
<td class = "text-center">${ord.idUtente}</td>
<td class = "text-center">${ord.data}</td>

	<td>
		<ul class = "lista-prodotti-ordine">
			<c:forEach items = "${ord.ordineAcquistato}" var = "dett">
				<li> 
				<span class = "prod-id">ID Prodotto: ${dett.idProdotto} </span>
				<span class = "prod-quant">Quantità: ${dett.quantita} </span> 
				<span class = "prod-prezzo">Prezzo Acquisto: ${dett.prezzoAcquisto} Euro</span>
				</li>
				</c:forEach>
		</ul>
	</td>
<td class = "text-price">${ord.totale}</td>
<td>
<span class = "stato">${ord.stato}</span>
</td>
</tr>
</c:forEach>


</table>

</div>

</div>

<jsp:include page = "footer.jsp"/>

<script src = "${pageContext.request.contextPath}/scripts/GestioneOrdiniAdmin.js"></script>

</body>
</html>