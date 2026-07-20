<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <div class = checkout-section>
    <h3>Dati di spedizione e di pagamento</h3>
    
    <div id = "checkError"></div>
    
    <form id = "formCheckout"  onsubmit = "event.preventDefault() ; checkout()" >
    <label for = "paese">Paese:</label>
    <input type = "text"  id = "paese" name = "paese" placeholder = "inserisci Paese">
    
  	<label for ="citta">Città:</label> 
    <input type = "text" id = "citta" name = "citta" placeholder = "inserisci Città">
    
    <label for = "provincia">Provincia:</label>
    <input type = "text" id = "provincia" name ="provincia" placeholder ="inserisci Provincia">
    
    <label for = "cap">CAP:</label>
    <input type = "text" id ="cap" name = "cap" placeholder ="inserisci Cap">
    
    <label for = "via">Via:</label>
    <input type = "text" id ="via" name = "via" placeholder = "inserisci Via">
    
    <label for = "pagamento">Metodo di Pagamento:</label>
    <select id ="pagamento" name = "pagamento"> 
    
    <option value = "">Seleziona</option>
    
    <option value ="carta">Carta Di Credito</option>
    
    <option value = "contrassegno">Contrassegno</option>
    </select>
    
    <br><br>
    
    <input type = "submit" value = "Conferma Ordine ed Acquista">
    </form>
    </div>
    

    
    
    
    