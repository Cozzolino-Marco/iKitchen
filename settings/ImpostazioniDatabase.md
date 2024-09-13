## Database di iKitchen

**1** - Scaricare e importare il database: Scarica il file "iKitchenDatabase.sql" e caricalo nel tuo database. Questo file contiene la creazione delle tabelle, le procedure memorizzate (stored procedures) e i dati necessari.

**2** - Creare due utenti con ruoli: Aggiungi due utenti al database chiamati 'utenteDomestico' e 'chef'.

**3** - Assegnare i permessi di visualizzazione: Concedi a entrambi gli utenti (utenteDomestico e chef) il permesso di accedere al database che hai caricato con il solo privilegio di SELECT, quindi possono solo visualizzare i dati.

**4** - Configurazione dei permessi delle Stored Procedures: Eseguire i seguenti comandi SQL per consentire ai due utenti di eseguire le procedure memorizzate:
	
	-- ASSEGNAZIONE PERMESSI DELLE PROCEDURE ALL'UTENTE
	GRANT EXECUTE ON PROCEDURE login TO 'utenteDomestico'@'localhost';
	GRANT EXECUTE ON PROCEDURE mostra_ingredienti TO 'utenteDomestico'@'localhost';
	GRANT EXECUTE ON PROCEDURE mostra_ricette TO 'utenteDomestico'@'localhost';
	GRANT EXECUTE ON PROCEDURE ottieni_dettagli_ricetta TO 'utenteDomestico'@'localhost';
	GRANT EXECUTE ON PROCEDURE recupera_ingredienti_dispensa TO 'utenteDomestico'@'localhost';
	GRANT EXECUTE ON PROCEDURE usa_ricetta TO 'utenteDomestico'@'localhost';
	GRANT EXECUTE ON PROCEDURE registrazione TO 'utenteDomestico'@'localhost';
	GRANT EXECUTE ON PROCEDURE recupera_nome_utente TO 'utenteDomestico'@'localhost';

	-- ASSEGNAZIONE PERMESSI DELLE PROCEDURE ALLO CHEF
	GRANT EXECUTE ON PROCEDURE login TO 'chef'@'localhost';
	GRANT EXECUTE ON PROCEDURE registrazione TO 'chef'@'localhost';
	GRANT EXECUTE ON PROCEDURE recupera_nome_utente TO 'chef'@'localhost';

**5** - Modifica il file di configurazione 'db.properties': Vai nel file 'db.properties' all'interno delle risorse dell'applicazione e inserisci il tuo URL di connessione nella variabile CONNECTION_URL. Aggiungi il nome utente e la password per il database nelle variabili LOGIN_USER e LOGIN_PASS.

NB. Se il driver di connessione al database non viene scaricato automaticamente (nonostante sia già incluso nelle dipendenze del pom.xml), puoi inserirlo manualmente. Il file del driver si trova nella cartella 'libs'.
	