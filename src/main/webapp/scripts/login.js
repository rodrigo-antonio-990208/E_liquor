

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

var regexUsername = /^[a-zA-Z0-9\.\_\-\@]+$/
var regexPass = /^.{4,}$/


function validateCampi (campo, regex, errormex){
	var value = campo.value.trim();
	var err = document.getElementById("error-container");
	if (!regex.test(value)){
		err.innerHTML = errormex;
		return false;
	}
	else {
		err.innerHTML = "";
		return true;
	}
}


window.onload = function (){
	var user = document.getElementById("username");
	var pass = document.getElementById("password");
	
	if (user){
		user.addEventListener("change", function(){validateCampi (user,regexUsername,"il campo Username puo' contenere solo caratteri (a-z, A-Z, 0-9, -, _, @)");});
	}
	if (pass){
		pass.addEventListener ("change", function(){validateCampi(pass,regexPass, "il campo password deve contenere almeno 4 caratteri");});
	}
};

function eseguiLogin(){
	var error = [];
	var erroriDoc = document.getElementById("error-container");
	erroriDoc.innerHTML = ""
	
	var user = document.getElementById("username").value;
	var pass = document.getElementById("password").value;
	
	if (!regexUsername.test(user)){error.push("il campo username non è valido");}
	if (!regexPass.test(pass)){error.push ("il campo password non è valido");}
	
	if (error.length > 0){
		erroriDoc.innerHTML = error.join("<br>");
		return;
	}
	
	
	var params = 'username='+encodeURIComponent(user)+'&password='+encodeURIComponent(pass);
	
	loadAjaxDoc(contextPath+"/Login","POST",params,handleLogin)
}

function handleLogin(request){
	var response = JSON.parse(request.responseText);
	if (response.status === "success"){
		window.location.href = response.redirect;
	}else {
		document.getElementById("error-container").innerHTML = response.message;
	}
}