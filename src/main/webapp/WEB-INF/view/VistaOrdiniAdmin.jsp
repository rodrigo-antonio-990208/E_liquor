<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestisci Ordini</title>
</head>
<body>
<jsp:include page = "header.jsp"/>
<div class = "container">
	<h2>Pannello Amministratore - Ricerca Ordini </h2>

<div id = "gestioneAdmin-error"></div>


<div class = "ricercaUtenti">
<h4>Ricerca per ID Utente</h4>

<form action = "${pageContext.request.contextPath}/admin/GestioneOrdini" method = "GET" onsubmit = "return validaUtente(event)">

<label for ="idUtente">Inserisci ID Utente</label><br><br>

<input type = "text" name = "idUtente" id = "idUtente" placeholder = "es. 12">

<button type = "submit"> Cerca</button>
</form>
</div>
<br>
<h4>Ricerca per Data</h4>

<div class = ricercaData>
<form action = "${pageContext.request.contextPath}/admin/GestioneOrdini" method = "GET" onsubmit = "return validaData(event)">

<label for = "dataX">Da: </label>
<input type = "date" id = "dataX" name = "dataX" >

<label for = "dataY" >A:</label>
<input type = "date" id = "dataY" name = "dataY">

<button type="submit" >Cerca</button>
</form>
</div>
<br>

<a href = "${pageContext.request.contextPath}/admin/GestioneOrdini"><button>Mostra Tutti Gli Ordini</button></a>

</div>

<table border = "1">
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
<td>${ord.idOrdine}</td>
<td>${ord.idUtente}</td>
<td>${ord.data}</td>

	<td>
		<ul>
			<c:forEach items = "${ord.ordineAcquistato}" var = "dett">
				<li> ID Prodotto: ${dett.idProdotto} | Quantità: ${dett.quantita} |  Prezzo Acquisto: ${dett.prezzoAcquisto} Euro</li>
				</c:forEach>
		</ul>
	</td>
<td>${ord.totale}</td>
<td>${ord.stato}</td>
</tr>
</c:forEach>


</table>

<jsp:include page = "footer.jsp"/>

<script src = "${pageContext.request.contextPath}/scripts/GestioneOrdiniAdmin.js"></script>

</body>
</html>