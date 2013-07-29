CREATE DATABASE Stopky CHARACTER SET utf8 COLLATE utf8_general_ci;
USE Stopky;
CREATE TABLE Ukoly (
		jmeno VARCHAR(155), 
		cas_startu DATETIME, 
		cas_konce DATETIME, 
		PRIMARY KEY (jmeno)

);


CREATE TABLE Podukoly (
		podukol VARCHAR(155), 
		ukol VARCHAR(155), 
		datum DATETIME, 
		PRIMARY KEY (podukol)
);



