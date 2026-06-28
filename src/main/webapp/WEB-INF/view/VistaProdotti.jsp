<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix = "c" uri= "jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogo lista liquori</title>

<style >
.prodotto_card {border: 1px solid;
padding: 15px;
width: 200px;    									//RICORDA DI METTERE QUESTO DENTOR UN FOGLIO DI STILE CSS
display: inline-block;
vertical-align: top; }
</style>

</head>
<body>
<jsp:include page = "header.jsp" />

<div class="sort">
<h3>Ordina Prodotti Per:</h3>
<a href ="catalogo?sort=Id_Prodotto"><button style = "margin-right:5px">ID</button></a>
<a href ="catalogo?sort=nome"><button style = "margin-right:5px">Nome</button></a>
<a href ="catalogo?sort=prezzo"><button style = "margin-right:5px">Prezzo</button></a>
</div>

<c:forEach items = "${prodotti}" var= "prod">

<div class = "prodotto_card">

<img src = "immaginiServlet?action=show&codice=${prod.id_prodotto}" alt= "${prod.nome}" width= "60" height="60">

<h3>${prod.nome}</h3>
<p>${prod.descrizione}</p>
<p>${prod.formato} ml</p>
<p>${prod.prezzo} €</p>
<a href = "catalogo?action=addC&codice=${prod.id_prodotto}"><button>Aggiungi al carrello</button></a>
 
 </div>
</c:forEach>

<jsp:include page= "footer.jsp"/>
</body>
</html>