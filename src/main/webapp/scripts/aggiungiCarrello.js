

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

function aggiungiCarrello(codice){
	var params = "action=addC&codice="+encodeURIComponent(codice);
	
	loadAjaxDoc("catalogo","GET", params, handleCarrello);
}

function rimuoviCarrello(codice){
	var params = "action=deleteC&codice="+encodeURIComponent(codice);
	
	loadAjaxDoc("catalogo", "GET", params,handleCarrello);
}

function handleCarrello(request){
	var response = JSON.parse(request.responseText);
	
	if (response.status === "success"){
		document.getElementById("actionC").innerHTML = response.message;
	}
	else {
		document.getElementById("actionC").innerHTML= "problema con aggiunta/rimozione prodotti";
	}
}