<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import=" model.* , java.util.* " %>
    <%@ taglib prefix = "c" uri= "jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<link rel ="stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/styles/style.css">
<meta charset="UTF-8">
<title>Miei Ordini</title>
</head>
<body>
<jsp:include page = "header.jsp"/>

<div class = "ordini-container">
<h1>I MIEI ORDINI</h1>

<% List<OrdineBean> ordine = (List<OrdineBean>) request.getAttribute ("ordini"); 
if (ordine != null && !ordine.isEmpty()){
%>
<table class= "mieiOrdini">
<tr>
<th>ID Ordine</th>
<th>Data</th>
<th>Indirizzo</th>
<th>Pagamento</th>
<th>Prodotti Acquistati</th>
<th>Totale</th>
</tr>

<c:forEach items = "${ordini}"  var = "ord">
<tr>
<td>${ord.idOrdine}</td>
<td>${ord.data}</td>
<td>${ord.indirizzo}</td>
<td>${ord.pagamento}</td>
	<td>
		<ul>
			<c:forEach items = "${ord.ordineAcquistato}" var = "prod">
			<li>ID: ${prod.idProdotto}  |  Quantità: ${prod.quantita}  |  Prezzo Acquisto: ${prod.prezzoAcquisto} Euro </li>
			</c:forEach>
		</ul>
	</td>
	
<td>${ord.totale}</td>
</tr>
</c:forEach>

</table>

<%}else { %>

<h2 class ="ordine-vuoti">Qui non c'è niente da vedere !</h2>

<%} %>
</div>

</body>
</html>