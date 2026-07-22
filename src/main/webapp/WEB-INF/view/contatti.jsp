<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/styles/style.css">
<meta charset="UTF-8">
<title>Contatti</title>
</head>
<body>

<jsp:include page ="header.jsp"/>

<div class = "contact-box">
<h1>Contatti</h1>
<br><br>
<div class ="info-dettagli">
<p><strong>Indirizzo:</strong> Via XXV Luglio, 249, Cava De' Tirreni<p>
<p><strong>Numero di telefono:</strong> 3669777301<p>
<p><strong>Email: rodrimasullo@gmail.com</strong><p>

</div>
</div>
<jsp:include page= "footer.jsp"/>
</body>
</html>