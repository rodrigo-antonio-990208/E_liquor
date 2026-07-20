<%@ page language="java" contentType="text/html ; charset=UTF-8"
    pageEncoding="UTF-8"  import =  " java.util.* , model.*"%>

<!DOCTYPE html>
<html>
<head>


<link rel = "stylesheet" type="text/css" href ="${pageContext.request.contextPath}/styles/style.css">

<meta charset="UTF-8">
<title>Carrello</title>
</head>

<body>

<jsp:include page = "header.jsp"/>

<div id = "actionC"></div>

<%CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
if (carrello != null && !carrello.isEmpty()){%>


<h2>Carrello</h2>


<div class="carrello">

<label >Prodotti: </label>
<% List <Prodotto> lista = carrello.getProdotti();

List <Integer> visited = new ArrayList<>();

for (Prodotto p: lista){
	
	if (!visited.contains (p.getIdProdotto())) {
			visited.add(p.getIdProdotto()); %>	
			
		<div class = "item-carrello">
			
			<img src = "Immagini?action=show&codice=<%=p.getIdProdotto()%>" alt = "<%=p.getDescrizione()%>" width = "60" height = "60">
			
			<div class = "dati-prodotto">		
			<p>Nome: <%=p.getNome() %></p>
	
			<p>Quantità: <%=carrello.getQuantitaProd(p.getIdProdotto()) %> </p>
	
			<p>Prezzo: <%=p.getPrezzo()%> €</p>
		
			</div>
	</div>
	
	<button onclick = "rimuoviCarrello(<%=p.getIdProdotto() %>)">Elimina dal carrello</button>
<%} 
}%>



<div class = "totale-box">
<h3>Totale Carrello</h3><br>
<h2><%=carrello.getPrezzoTotale() %></h2>
</div>


<hr>

<button id = "checkoutBtn" onclick = "mostraCheckout()">Procedi All'acquisto</button>
	 
<div id = "contenitoreCheckout" style = "display : none;">
	
	 <%  UtenteBean utente = (UtenteBean) request.getSession().getAttribute ("utente");
if (utente != null  && utente.getRuolo().equalsIgnoreCase("user")){
	 %>
	  
	<jsp:include page = "checkout.jsp"/>
 	
 <% } else {	%>	
<h3>Per effettuare l'ordine occore effettuare l'accesso</h3>

<a href = "${pageContext.request.contextPath}/index"><button>Effettuare il Login</button></a>
<h3>Non sei registrato ?</h3>
<a href = "${pageContext.request.contextPath}/Registrazione"><button>Registarti</button></a>

<%}%>
</div>

<%}else {%>

<h2>Il tuo carrello è vuoto</h2>

<%}%>

<jsp:include page = "footer.jsp"/>
<script src = "scripts/checkout.js"></script>
<script src = "scripts/aggiungiCarrello.js"></script>
</body>
</html>