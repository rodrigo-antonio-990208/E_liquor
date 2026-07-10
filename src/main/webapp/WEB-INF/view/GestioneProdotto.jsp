

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page contentType= "text/html ; charset=UTF-8" import ="model.Prodotto" %> 
    <%@taglib prefix = "c" uri= "jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogo lista liquori per amministratore</title>

<style >
.prodotto_card {border: 1px solid;
padding: 15px;
width: 200px;
display: inline-block;			/*ricoda di inserirlo nel foglio css*/
vertical-align: top; }
</style>

</head>
<body>
<jsp:include page = "header.jsp" />

<div class="sort">
<h3>Ordina Prodotti Per:</h3>
<a href ="GestioneProdottoServlet?sort=Id_Prodotto"><button style = "margin-right:5px">ID</button></a>
<a href ="GestioneProdottoServlet?sort=nome"><button style = "margin-right:5px">Nome</button></a>
<a href ="GestioneProdottoServlet?sort=prezzo"><button style = "margin-right:5px">Prezzo</button></a>
</div>

<div>
<h2>Prodotti</h2>

<c:forEach items = "${prodotti}" var= "prod">

<div class = "prodotto_card">

<img src = "immagini?action=show&codice=${prod.idProdotto}" alt= "${prod.nome}" width= "60" height="60">

<h3>${prod.nome}</h3>
<p>${prod.descrizione}</p>
<p>${prod.formato} ml</p>
<p>${prod.prezzo} €</p>
<a href = "GestioneProdottoServlet?action=read&codice=${prod.idProdotto}"><button>Dettagli</button></a>
<a href = "GestioneProdottoServlet?action=delete&codice=${prod.idProdotto}"><button>Elimina Prodotto</button></a>
 
 </div>
</c:forEach>

</div>

<div>
<h2>Dettagli</h2>

<% Prodotto prod = (Prodotto) request.getAttribute ("prodotto");
if (prod != null){
	%>
<div class="prodotto_card">
<img src ="immagini?action=show&codice=<%=prod.getIdProdotto() %>" alt ="<%=prod.getNome()%>"  height ="60" width ="60">
<h3><%=prod.getNome()%></h3>
<p><%=prod.getDescrizione()%></p>
<p><%=prod.getFormato()%> ml</p>
<p><%=prod.getPrezzo()%> €</p>

</div>

<form method ="post" action ="Immagine" enctype = "multipart/form-data">
<input type ="hidden" name = "action" value = "upload">
<input type = "hidden" name ="codiceProd" value = "<%=prod.getIdProdotto()%>">
<label for ="immagine"> Carica Immagine</label><br><input type ="file" id =immagine name ="immagine" accept = "image/*">
<input type = "submit" value = "carica">
</form>

<% } %>

</div>

<div>
<h2>Aggiungi Prodotto</h2>

<form action = "GestioneProdottoServlet" method ="post">
<input type ="hidden" name ="action" value ="aggiungi">

<label>Nome Prodotto:</label>
<input type = "text" name = "nome" placeholder = "inserisci nome prodotto" required>

<label>Descrizione:</label>
<textarea name ="descrizione" rows="15" cols ="40" placeholder ="inserisci descrizione" required></textarea><br>

<label>Categoria</label>
<select name = "categoria">
<option value ="1">GIN</option>
<option value = "2">TEQUILA</option>
<option value = "3">WHISKEY</option>
<option value = "4">LIQUORI</option>
</select><br>

<label>Prezzo</label>
<input type = "number" step = "0.01" name = "prezzo" placeholder = "inserisci prezzo" required><label>€</label>

<label>Quantità</label>
<input type = "number" min = "0" name = "quantita" placeholder = "inserisci quantità" required>

<label>Formato </label>
<input type = "number" min = "0" name ="formato" placeholder = "inserisci formato" required><label>cl</label>
 
<label>Gradazione</label>
<input type = "number" step = "0.01" name = "gradazione"  placeholder = "inserisci gradazione" required> <label>%</label>

<input type = "submit" value = "inserisci">
</form></div>

<jsp:include page= "footer.jsp"/>

</body>
</html>