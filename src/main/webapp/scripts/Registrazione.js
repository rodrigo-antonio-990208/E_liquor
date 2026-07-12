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
}}


var regexTesto = /^[a-zA-Z0-9\.\@\_\-]+$/;
var regexPass = /^.{4,}$/;

function validaCampi (campo, regex, errormex){
	var value = campo.value.trim();
	var err = document.getElementById("registrazioneError");
	
	if (!regex.test(value)){
		err.innerHTML = errormex;
	return false}
	else {
		err.innerHTML = "";
		return true;
	}
}


window.onload = function (){
	var form = document.getElementById ("formRegistrazione");
	
	form.nome.addEventListener("change", function (){ validaCampi(this, regexTesto, "il campo Nome non è valido");});
	form.cognome.addEventListener("change", function(){ validaCampi(this, regexTesto, "il campo Cognome non è valido");});
	form.username.addEventListener("change", function(){ validaCampi(this, regexTesto, "il campo Email non è valido");});
	form.password.addEventListener("change", function(){ validaCampi(this, regexPass, "il campo Password non è valido");});
} ;



function registrati (event){
	if (event){
		event.preventDefault();
	}
	
	var form = document.getElementById("formRegistrazione");
	var error = document.getElementById("registrazioneError");
	error.innerHTML = "";
	
	var errori = [];
	
	if (!regexTesto.test(form.nome.value)) {errori.push("riempire il campo Nome");}
	if (!regexTesto.test(form.cognome.value)){errori.push("riempire il campo Cognome");}
	if (!regexTesto.test(form.username.value)){errori.push("riempire il campo Email");}
	if (!regexPass.test(form.password.value)){errori.push("riempire il campo errori");}
	
	if (errori.length > 0){
		error.innerHTML = errori.join ("<br>");
		return;
	}
	
	var params = "username="+encodeURIComponent(form.username.value)+"&password="+encodeURIComponent(form.password.value)+"&nome="+encodeURIComponent(form.nome.value)+
	"&cognome="+encodeURIComponent(form.cognome.value);
	
	loadAjaxDoc("Registrazione", "POST", params, handleReg);
}

function handleReg(request){
	var response = JSON.parse(request.responseText);
	var errors = document.getElementById("registrazioneError");
	
	if (response.status === "success"){
		window.location.href = response.redirect;
	}
	else 
		errors.innerHTML = response.message;
}