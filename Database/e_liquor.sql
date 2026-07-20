DROP DATABASE IF EXISTS e_liquor;
CREATE DATABASE e_liquor; 
USE e_liquor;

CREATE TABLE utente
(
id_utente int AUTO_INCREMENT PRIMARY KEY,
nome varchar (50) NOT NULL,
cognome varchar (50) NOT NULL,
email varchar(100) NOT NULL,
password varchar (300) NOT NULL,
ruolo varchar (10) DEFAULT 'user',
data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE indirizzo(
id_civico int AUTO_INCREMENT PRIMARY KEY,
id_utente int NOT NULL,
città varchar (50) NOT NULL,
via varchar (50) NOT NULL,
CAP varchar(10) NOT NULL,
provincia varchar(50) NOT NULL, 
FOREIGN KEY (id_utente) REFERENCES utente (id_utente) ON DELETE CASCADE
);


CREATE TABLE ordine(
id_ordine int AUTO_INCREMENT PRIMARY KEY,
id_utente int NOT NULL,
data_ordine TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
indirizzo_spedizione varchar (100) NOT NULL,
metodo_pagamento varchar (50) NOT NULL, 
totale decimal (10,2) NOT NULL,
stato varchar(50) DEFAULT 'in elaborazione' ,
FOREIGN KEY (id_utente) REFERENCES utente(id_utente) ON DELETE CASCADE
);

CREATE TABLE prodotto(
id_prodotto int AUTO_INCREMENT PRIMARY KEY,
id_categoria varchar (50) NOT NULL,
nome varchar (50) NOT NULL,
attivo BOOLEAN DEFAULT true,
quantita_disponibile int DEFAULT 0,
prezzo_attuale decimal (10,2) DEFAULT 0.00 NOT NULL,
gradazione decimal (10,2) NOT NULL,
descrizione TEXT NOT NULL,
formato int NOT NULL,
immagine_url varchar (200) DEFAULT NULL,
mime_type varchar (50) DEFAULT NULL 
);

CREATE TABLE dettagli_ordine
(
id_prodotto int ,
id_ordine int ,
prezzo_acquisto decimal(10,2) NOT NULL,
quantità int DEFAULT 1 NOT NULL,
PRIMARY KEY (id_prodotto, id_ordine),
FOREIGN KEY (id_prodotto) REFERENCES prodotto (id_prodotto),
FOREIGN KEY (id_ordine) REFERENCES ordine (id_ordine) ON DELETE CASCADE
);





INSERT INTO utente (nome, cognome, email, password, ruolo) VALUES ("Rodrigo", "Masullo", "admin", "ciao12", "admin");

INSERT INTO prodotto (nome, descrizione, prezzo_attuale, quantita_disponibile, gradazione, formato, id_categoria) VALUES 
("beefeater", "ciao", 10.00, 3, 12.00, 70,"gin");