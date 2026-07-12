function creaXMLHttpRequest (){
var request;
try{
	request = new XMLHttpRequest();
}catch (e){
	try{
		 request = new ActiveXObject("Msxml2.XMLHTTP"); 
	}catch (e){
		try{
			request = new ActiveXObject("Microsoft.XMLHTTP");
		}catch (e){
			document.getElementById("error-container").innerHTML = "il browser non supporta ajax";
			return null;
		}
	}
}
return request;
}


function loadAjaxDoc (url, method, params, cFunzioni){
	var request = creaXMLHttpRequest();
	if (request){
		
		request.onreadystatechange= function(){
			if (this.readyState == 4){
				if (this.status == 200){
				cFunzioni(this);
			}else{
				if (this.status == 0){
					document.getElementById("error-container").innerHTML = "problemi nella richiesta : nessuna risposta ricevuta nel tempo limite";
				}else {
					document.getElementById("error-container").innerHTML = "problemi nell'esecuzione della richiesta"+this.statusText;
				}
				return null;
			}
		}
	};
	setTimeout(function(){
		if (request.readyState<4){
			request.abort();
		}
	},15000);
	
	if (method.toLowerCase() == "get"){
		if (params){
			request.open("GET",url+ "?"+ params, true);
		}else{
			request.open("GET",url,true)
		}
		request.setRequestHeader("Connection", "close");
		request.send(null);
	}
	else{
		if (params){
			request.open ("POST", url, true);
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.send(params);
		}else {
			console.log ("non ci sono parametri, usa GET");
			return null;
		}
	}
}
}

function checkout(){
	var citta = document.getElementById("citta").value;
	var cap = document.getElementById("cap").value;
	var via = document.getElementById("via").value;
	var provincia = document.getElementById("provincia").value;
	var paese = document.getElementById("paese").value;
	var pagamento = document.getElementById("pagamento").value;
	var errori = document.getElementById("checkError");
	
	errori.innerHTML = "";
	
	var erroriJs = [];
	
	if (!paese || paese.trim() === ""){ erroriJs.push ( "il campo paese è obbligatorio ")}
	if (!citta || citta.trim() ===""){erroriJs.push("il campo città è obbligatorio")}
	if (!cap || cap.trim ()===""){erroriJs.push("il campo CAP è obbligatorio")}
	if (!provincia || provincia.trim() ===""){erroriJs.push("il campo provincia è obbligatorio")}
	if (!via || via.trim() ===""){erroriJs.push("il campo via è obbligatorio")}
	if (!pagamento || pagamento.trim() ===""){erroriJs.push("il campo pagamento è obbligatorio")}
	if (!paese || paese.trim() ===""){erroriJs.push("il campo paese è obbligatorio")}
	
	if (erroriJs.length > 0){
		errori.innerHTML = erroriJs.join("<br>");
		return;
	}
	
	var params = "paese="+encodeURIComponent(paese.trim())+"&citta="+encodeURIComponent(citta.trim())+"&via="+encodeURIComponent(via.trim())+"&cap="+encodeURIComponent(cap.trim())+"&provincia="+encodeURIComponent(provincia.trim())+"&pagamento="+encodeURIComponent(pagamento.trim());
	loadAjaxDoc("CheckoutServlet","POST",params,handleCheckout)
}

function handleCheckout(request){
	var response = JSON.parse(request.responseText);
	var errorCont = document.getElementById("checkError");
	
	if (response.status === "success"){
		window.location.href = response.redirect;
	}
	else if (response.status === "error"){
	if (response.redirect){
	window.location.href = response.redirect;
	}else if (response.message){
		errorCont.innerHTML= response.message;
	}
}}