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

<%List <String> errori = (List<String>) request.getAttribute ("errors"); 

if (errori != null  && !errori.isEmpty()){
	for (String err : errori){
%>
<%=err %><br>

<%
}
}
%>


<h1>Registrati</h1>
<form action = "Registrazione" method = "post">
<fieldset>
<label>Nome:</label>
<input type = "text" name = "nome" placeholder = "Inserisci nome" >
<label>Cognome:</label>
<input type = "text" name = "cognome" placeholder = "Inserisci cognome">
<label>Email (username):</label>
<input type = "text" name = "username" placeholder = "Inserisci email">
<label>Password:</label>
<input type = "password" name = "password" placeholder = "Inserisci password">
<input type = "submit" value ="Registrati">
<input type = "reset" value = "Reset">
</fieldset>
</form>

<jsp:include page = "footer.jsp"/>
</body>
</html>