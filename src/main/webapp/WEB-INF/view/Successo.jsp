<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel ="stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/styles/style.css">
<title>Successo Checkout</title>

</head>
<body>

<jsp:include page ="header.jsp"/>

<div class ="successo-container">
	
	<h1>Checkout avvenuto con successo !</h1>
	<p>Grazie per il tuo acquisto.</p>
	<a href= "${pageContext.request.contextPath}/catalogo"><button  class = "backHome">Torna all home</button></a>
	
</div>
</body>
</html>