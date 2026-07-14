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

var regexNumber = /^\d+$/;

function validaCampi (field , reg, mex){
	var errField = document.getElementById("gestioneAdmin-error");
	var value = field.value.trim();
	
	if (!reg.test(value)){
		errField.innerHTML= mex;
		return false;
	}
	else {
		errField.innerHTML ="";
		return true;
	}
}

window.onload = function (){
	var user = document.getElementById("idUtente");
	
	if (user){
		user.addEventListener("change", function(){validaCampi(user,regexNumber, "Inserire correttamente il campo username");});
	}
	
};

function validaUtente(event){
	
	var idUtente = document.getElementById("idUtente").value.trim();
	var error = document.getElementById("gestioneAdmin-error");
	error.innerHTML = "";
	
	if (!regexNumber.test(idUtente)){
		event.preventDefault();
		
		error.innerHTML = "inserire correttamente il campo Id Utente";
		return false;
	}
	return true;
		
}



function validaData(event){
	
	var dateX = document.getElementById ("dataX").value;
	var dateY = document.getElementById("dataY").value;
	var box = document.getElementById("gestioneAdmin-error");
	
	if (!dateX || !dateY){
		event.preventDefault();
		box.innerHTML = "inserire entrambe le date";
		return false ;
	}
	
	if (dateX > dateY){
		event.preventDefault();
		box.innerHTML= "la prima data deve essere minore della seconda";
		return false;
	}
	
	return true;
}




function handleGestioneOrdini (request){
	var boxErr = document.getElementById("gestioneAdmin-error");
	var response = JSON.parse(request.responseText);
	
	if (response.status === "success"){
		boxErr.innerHTML = "";
	}
	else boxErr.innerHTML = response.message;
}

