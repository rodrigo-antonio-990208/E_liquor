

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

function aggiungiProdotti(event){
	if (event){
		event.preventDefault();
	}
	var form = document.getElementById("formAggiungi");
	
	var params = "action=aggiungi&nome="+encodeURIComponent(form.nome.value)+"&descrizione="+encodeURIComponent(form.descrizione.value)+"&prezzo="+encodeURIComponent(form.prezzo.value)+
		"&gradazione="+encodeURIComponent(form.gradazione.value)+"&formato="+encodeURIComponent(form.formato.value)+"&categoria="+encodeURIComponent(form.categoria.value)+"&quantita="+encodeURIComponent(form.quantita.value);
		loadAjaxDoc("GestioneProdottoServlet","POST",params,handleAggiungi);
													
	
}


function handleAggiungi(request){
	var response = JSON.parse(request.responseText);
	var err = document.getElementById("gestione-error");
	
	if (response.status === "success"){
		window.location.href = response.redirect;
	}else {
		err.innerHTML(response.message);
	}
}


function deleteProdotto(codice){
	var del = "action=delete&codice="+encodeURIComponent(codice);
	loadAjaxDoc("GestioneProdottoServlet","POST",del,HandledeleteProdotto);
}

function HandledeleteProdotto(request){
	var response = JSON.parse(request.responseText);
	var err = document.getElementById("gestione-error");
	if (response.status ==="success"){
		window.location.href = response.redirect;
	}
	else err.innerHTML=response.message
}