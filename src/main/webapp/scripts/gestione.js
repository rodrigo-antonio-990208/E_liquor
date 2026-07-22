

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
			document.getElementById("gestione-error").innerHTML = "il browser non supporta ajax";
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
					document.getElementById("gestione-error").innerHTML = "problemi nella richiesta : nessuna risposta ricevuta nel tempo limite";
				}else {
					document.getElementById("gestione-error").innerHTML = "problemi nell'esecuzione della richiesta"+this.statusText;
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

var regexTesto  = /^[\s\S]+$/;
var regexPrezzo = /^\d+(\.\d{1,2})?$/;
var regexInteroPositivo = /^\d+$/;

function validaTesto (campo, regex, errorMex){
	var valore = campo.value.trim();
	var error = document.getElementById("gestione-error");
	
	if (!regex.test(valore)){
		error.innerHTML = errorMex;
		return false;
	}
	else {
		error.innerHTML = "";
		return true;
	}
}

document.addEventListener("DOMContentLoaded", function(){
	var form = document.getElementById("formAggiungi");
	
	if (form){
	form.addEventListener("submit", aggiungiProdotti);
		
	form.nome.addEventListener("change", function (){validaTesto(this, regexTesto, "il campo Nome non è valido" );});
	form.descrizione.addEventListener("change",function(){validaTesto(this, regexTesto,"il campo Descrizione non è valido");});
	form.prezzo.addEventListener("change",function(){validaTesto(this, regexPrezzo,"inserire campo Prezzo con valori decimali");});
	form.formato.addEventListener("change", function (){validaTesto(this, regexInteroPositivo,"inserire campo Formato con valori interi");})
	form.gradazione.addEventListener("change", function(){validaTesto(this, regexPrezzo,"inserire campo Gradazione con valori interi");})
	form.quantita.addEventListener("change", function(){validaTesto(this, regexInteroPositivo,"inserire campo Quantità con valori interi");})
	form.categoria.addEventListener("change", function(){validaTesto(this,regexTesto, "inserire campo categoria");});
}
});


function aggiungiProdotti(event){
	if (event){
		event.preventDefault();
	}
	
	var form = document.getElementById("formAggiungi");
	var error = document.getElementById("gestione-error");
	error.innerHTML = "";
	
	var errori = [];
	
	if (!regexTesto.test(form.nome.value)){errori.push("riempire correttamente il campo Nome");}
	if (!regexTesto.test(form.descrizione.value)){errori.push("riempire correttamente il campo Descrizione");}
	if (!regexPrezzo.test(form.prezzo.value)){errori.push("riempire correttamente il campo Prezzo");}
	if (!regexPrezzo.test(form.gradazione.value)){errori.push("riempire correttamente il campo Gradazione");}
	if (!regexInteroPositivo.test(form.formato.value)){errori.push("riempire correttamente il campo Formato");}
	if (!regexInteroPositivo.test(form.quantita.value)){errori.push("riempire correttamente il campo Quantità");}
	if (!regexTesto.test(form.categoria.value)){errori.push("inserire categoria");}
	
	if (errori.length > 0){
		console.warn("invio bloccato per errori", errori)
		error.innerHTML= errori.join("<br>");
		return;
	}
	
	var actionValue = form.elements["action"].value;
	var codiceValue = form.elements["codice"].value;
	
	var params = "action="+encodeURIComponent(actionValue)+"&codice="+encodeURIComponent(codiceValue)+"&nome="+encodeURIComponent(form.nome.value)+"&descrizione="+encodeURIComponent(form.descrizione.value)+"&prezzo="+encodeURIComponent(form.prezzo.value)+
		"&gradazione="+encodeURIComponent(form.gradazione.value)+"&formato="+encodeURIComponent(form.formato.value)+"&categoria="+encodeURIComponent(form.categoria.value)+"&quantita="+encodeURIComponent(form.quantita.value);
		
		
		loadAjaxDoc(contextPath+"/admin/GestioneProdottoServlet","POST",params,handleAggiungi);
													
}


function handleAggiungi(request){
	var response = JSON.parse(request.responseText);
	
	try{
	var err = document.getElementById("gestione-error");
	
	if (response.status === "success"){
		
		err.style.color = "green";
		            err.innerHTML = "Prodotto aggiunto correttamente";
		            
		            setTimeout(function(){
		                window.location.href = response.redirect;
		            }, 1000);
					
	}else {
		err.style.color= "red";
		err.innerHTML = response.message;
	}
	
	}catch(e){
		console.error("Il server non ha restituito JSON:", request.responseText);
		        err.style.color = "red";
		        err.innerHTML = "Errore del server durante l'elaborazione (risposta non valida).";
		    }
	}




function deleteProdotto(codice){
	if(confirm ("sicuro di voler eliminare questo prodotto ?")){
	var del = "action=delete&codice="+encodeURIComponent(codice);
	loadAjaxDoc(contextPath+"/admin/GestioneProdottoServlet","POST",del,HandledeleteProdotto);
}}



function HandledeleteProdotto(request){
	var response = JSON.parse(request.responseText);
	var err = document.getElementById("gestione-error");
	if (response.status ==="success"){
		
		window.location.href = response.redirect;
	
	}
	else err.innerHTML=response.message
}