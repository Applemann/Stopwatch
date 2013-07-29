USE Stopky;
INSERT INTO Ukoly(jmeno, cas_startu, cas_konce) VALUES('Naprogramovat zadání', '2013:04:23 09:46:06', '2013:04:23 12:50:15' );

INSERT INTO Ukoly(jmeno, cas_startu, cas_konce) VALUES('Sportování', '2013:04:26 15:24:06', '2013:04:28 5:50:46' );

INSERT INTO Ukoly(jmeno, cas_startu) VALUES('Předělat zadání', '2013:04:25 09:52:02');

INSERT INTO Ukoly(jmeno, cas_startu) VALUES('Vytvořit databázi', '2013:04:28 20:45:35');

INSERT INTO Podukoly(ukol, podukol, datum) VALUES('Vytvořit databázi', 'Vytvořeny tabulky','2013:04:29 16:24:25');

INSERT INTO Podukoly(ukol, podukol, datum) VALUES('Naprogramovat zadání', 'Udělány tabulky úkolů', '2013:04:25 16:24:06' );

INSERT INTO Podukoly(ukol, podukol, datum) VALUES('Naprogramovat zadání', 'Naprogramovány podúkoly', '2013:04:27 4:24:52' );

INSERT INTO Podukoly(ukol, podukol, datum) VALUES('Naprogramovat zadání', 'Zahájeno Testování', '2013:04:29 14:25:52' );

INSERT INTO Podukoly(ukol, podukol, datum) VALUES('Předělat zadání', 'Uděláno pár změn', '2013:04:27 09:52:02');
