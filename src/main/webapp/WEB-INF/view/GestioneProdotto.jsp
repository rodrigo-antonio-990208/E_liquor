

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import ="model.Prodotto"%>
  
    <%@taglib prefix = "c" uri= "jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>${empty prodotto ? 'Inserisci Nuovo Prodotto' : 'Modifica Prodotto Esistente'}</title>

</head>

<body>

<jsp:include page = "header.jsp" />

<div class ="gestione-box">
<h2>${empty prodotto ? 'Aggiungi Prodotto' : 'Modifica Prodotto'}</h2>

<form id="formAggiungi" action = "${pageContext.request.contextPath}/admin/GestioneProdottoServlet" method ="post" onsubmit ="aggiungiProdotti(event)" novalidate>
<input type ="hidden" name ="action" value ="${empty prodotto ? 'aggiungi' : 'modifica'}">
<input type ="hidden" name ="codice" value = "${prodotto.idProdotto}">

<div id = "gestione-error"></div>

<label>Nome Prodotto:</label>
<input type = "text" name = "nome" value ="${prodotto.nome}" placeholder = "inserisci nome prodotto" required>

<label>Descrizione:</label>
<textarea name ="descrizione"  rows="15" cols ="40" placeholder ="inserisci descrizione" required> ${prodotto.descrizione}</textarea><br>

<label>Categoria</label>

<select name = "categoria">
<option value ="gin" ${prodotto.idCategoria == 'gin' ? 'selected' : '' }>GIN</option>
<option value = "tequila"  ${prodotto.idCategoria == 'tequila' ? 'selected' : '' }>TEQUILA</option>
<option value = "whiskey"  ${prodotto.idCategoria == 'whiskey' ? 'selected' : '' }>WHISKEY</option>
<option value = "liquori"  ${prodotto.idCategoria == 'liquori' ? 'selected' : '' }>LIQUORI</option>
</select><br>

<label>Prezzo</label>
<input type = "number" step = "0.01" name = "prezzo" value = "${prodotto.prezzo}" placeholder = "inserisci prezzo" required><label>€</label>

<label>Quantità</label>
<input type = "number" min = "0" name = "quantita" value = "${prodotto.quant}" placeholder = "inserisci quantità" required>

<label>Formato </label>
<input type = "number" min = "0" name ="formato" value = "${prodotto.formato}" placeholder = "inserisci formato" required><label>cl</label>
 
<label>Gradazione</label>
<input type = "number" step = "0.01" name = "gradazione" value = "${prodotto.gradazione}" placeholder = "inserisci gradazione" required> <label>%</label>

<br><br>
<input type = "submit" value = "inserisci">
</form>

<div class = "upload-box" style ="display: ${empty prodotto ? 'none' : 'block'};">
	<h3 class = "upload-title"> Aggiungi Foto</h3>
	
	<form method = "post" action ="${pageContext.request.contextPath}/Immagini" enctype = "multipart/form-data">
		<input type ="hidden" name = "action" value ="upload">
		<input type = "hidden" name = "codiceProd" value ="${prodotto.idProdotto}">
		
		<input type ="file" name = "immagine" accept="image/*" class="upload-input">
		
		<input type="submit" value = "Carica Nuova Foto" class = "btn-carica-foto">
		
	</form>

</div>

<a href = "${pageContext.request.contextPath}/catalogo" class = "btn-annulla" style ="display: ${empty prodotto ? 'none' : 'block'};">Annulla ed esci dalla modifica</a>

</div>


<jsp:include page= "footer.jsp"/>

<script src = "${pageContext.request.contextPath}/scripts/gestione.js"></script>

</body>
</html>