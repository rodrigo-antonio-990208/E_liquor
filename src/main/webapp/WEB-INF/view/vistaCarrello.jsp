<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType = "text/html ; charset = UTF-8"  import =  " java.util.* , model.*"%> 
<!DOCTYPE html>
<html>
<head>

<style>
.carrello 
{border: 1px solid;
padding: 15px;
width: 700px;
display: inline-block;			/*ricoda di inserirlo nel foglio css*/
vertical-align: top; 
}
</style>

<meta charset="UTF-8">
<title>Carrello</title>
</head>
<jsp:include page = "header.jsp"/>
<body>

<%CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
if (carrello != null){%>
<h2>Carrello</h2>
<div class="carrello">
<label >Prodotti: </label>
<%List <Prodotto> lista = carrello.getProdotti();
for (Prodotto p: lista){%>	
	<p><%=p.getNome() %></p>
	<p><img src = "Immagini?action=show&codice=<%=p.getIdProdotto()%>" alt = "<%=p.getDescrizione()%>" width = "60" height = "60" >	</p>
	<p><%=p.getPrezzo()%> €</p>
	<a href ="catalogo?action=deleteC&codice=<%=p.getIdProdotto() %>"><button>Elimina dal carrello</button></a>
<%} %>

<h3>Totale Carrello</h3><br>
<h2><%=carrello.getPrezzoTotale() %></h2>
<%} %>
</div>
</body>
</html>