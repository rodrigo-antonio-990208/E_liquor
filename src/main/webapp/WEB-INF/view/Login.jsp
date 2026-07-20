<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page  import = "java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<link rel = "stylesheet" type= "text/css" href= "${pageContext.request.contextPath}/styles/style.css">
<meta charset="UTF-8">
<title>Pagina di Login</title>
</head>


<body>

	<jsp:include page = "header.jsp"/>
	
	<div class = "login-container">
		<div class = "login-box">
			
			<h1>ACCEDI</h1>
			
			<div id = "error-container"></div> 

			<form id = "loginForm"  action = "javascript:void(0)" method= "post">
				<fieldset>
					<legend>Crendenziali di Accesso</legend>
					
					<label for= "username">Email (Username):</label>
					<input type = "text" id ="username" name = "username" placeholder ="Inserisci email">
					
					<label for = "password">Password:</label>
					<input type = "password" id ="password" name = "password" placeholder ="Inserisci password">
					
				<div class = "form-buttons">
					<input class = "btn-login" type= "submit" value ="Login" onclick = "eseguiLogin()">
					<input class = "btn-reset" type = "reset" value ="Reset">
				</div>
				</fieldset>
				
			</form>
			</div>
	</div>	
	
	<jsp:include page = "footer.jsp"/>
	
<script src = "scripts/login.js"></script>
</body>
</html>