<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page  import = "java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina di Login</title>
</head>
<body>
<jsp:include page = "header.jsp"/>
<div id = "error-container"></div> 

<form id = "loginForm"  action = "javascript:void(0)" method= "post">
<fieldset>
<label for= "username">Email (Username):</label>
<input type = "text" id ="username" name = "username" placeholder ="Inserisci email">
<label for = "password">Password:</label>
<input type = "password" id ="password" name = "password" placeholder ="Inserisci password">
<input type= "submit" value ="Login" onclick = "eseguiLogin()">
<input type = "reset" value ="Reset">
</fieldset>
</form>
<jsp:include page = "footer.jsp"/>
<script src = "scripts/login.js"></script>
</body>
</html>