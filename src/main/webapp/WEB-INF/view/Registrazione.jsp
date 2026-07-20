<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= "java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina di Registrazione</title>
</head>
<body>

<jsp:include page = "header.jsp"/>

<div class = "registrazione-container">
<div class= "registrazione-box">

<h1>Registrati </h1>

<div id = "registrazioneError"></div>


<form id = "formRegistrazione" onsubmit= "registrati(event)">



  <fieldset>
  
  	<legend>Dati Personali</legend>

	<label for = "nome">Nome:</label>
	<input type = "text"  id = "nome" name = "nome" placeholder = "Inserisci nome" >
	<label for ="cognome">Cognome:</label>
	<input type = "text" id= "cognome" name = "cognome" placeholder = "Inserisci cognome">
	<label for = "username">Email (username):</label>
	<input type = "text" id="username" name = "username" placeholder = "Inserisci email">
	<label for ="password">Password:</label>
	<input type = "password" id="password" name = "password" placeholder = "Inserisci password">
	
	<div class= "form-buttons">
		<input class ="btn-submit" type = "submit" value ="Registrati">
		<input class ="btn-reset" type = "reset" value = "Reset">
	</div>
	
  </fieldset>

</form>
</div>
</div>

<jsp:include page = "footer.jsp"/>

<script src = "scripts/Registrazione.js"></script>
</body>
</html>