

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
			document.getElementById("actionC").innerHTML = "il browser non supporta ajax";
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
					document.getElementById("actionC").innerHTML = "problemi nella richiesta : nessuna risposta ricevuta nel tempo limite";
				}else {
					document.getElementById("actionC").innerHTML = "problemi nell'esecuzione della richiesta"+this.statusText;
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
	
	loadAjaxDoc(contextPath+"/catalogo","GET", params, handleCarrello);
}



function rimuoviCarrello(codice){
	var params = "action=deleteC&codice="+encodeURIComponent(codice);
	
	loadAjaxDoc(contextPath+"/catalogo", "GET", params,function(request){ 
			
		handleCarrello(request);
		setTimeout(function(){location.reload();},1000);});	
		
}


function avvisoRegistrazione(){
	var box = document.getElementById("actionC");
	var avviso = "Per proseguire all'acquisto, <a href = 'Login'>accedi</a> o <a href = 'Registrazione'> registrati </a>";

	box.innerHTML=avviso;
	setTimeout(function(){
		box.innerHTML = "";
	}, 5000);	

}

function handleCarrello(request){
	var response = JSON.parse(request.responseText);
	var box = document.getElementById("actionC");
	
	if (response.status === "success"){
		
		box.innerHTML = response.message;
		
		setTimeout (function(){
			box.innerHTML = "";
		},3000);
	
	}
	else {
		box.innerHTML= "problema con aggiunta/rimozione prodotti";
	}
}