```
InizioSpecificaTransizioni Aereo
	Transizione: HANGAR -> PISTA
		Segnatura: partenza(destinazione: Aeroporto)
		Evento: Partenza(destinazione : Aeroporto)
		Condizione: --;
		Azione:
			pre: --;
			post: 
				this.destinazione = partenza.getDestinazione() //payload

	Transizione: PISTA -> IN_VOLO
		Segnatura: decollo{mitt: controllo} / lasciaAeroporto
		Evento: Decollo()
		Condizione: decollo.getMitt() equals this.getAeroporto() 
			// poiché la conferma dice deve "arrivare dalla torre di controllo" e poiché tale torre di controllo non é un oggetto che esiste nel diagramma, ho interpretato che la torre di controllo é parte dell'aeroporto e l'ok a partire deve venire dall'aeroporto stesso
		Azione:
			pre: --;
			post: 
				this.aeroporto = null;

	Transizione: IN_VOLO -> HANGAR
		Segnatura: atterra/ arrivoInAeroporto
		Evento: Atterra()
		Condizione: --;
		Azione:
			pre: --;
			post: 
				this.aeroporto = this.destinazione;
				this.destinazione = null;

	Transizione: IN_VOLO -> EMERGENZA 
		Segnatura: emergenza / richiestaAtterragio {dest: aeroportoVicino()}
		Evento: Emergenza()
		Condizione: --;
		Azione:
			pre: --;
			post: 
				this.destinazione = this.aeroportoVicino();
				// in caso di emergenza la destinazione cambia nell'aeroporto piú vicino;
				nuovoEvento = richiestaAtterraggio(this, this.destinazione);
	Transizione: EMERGENZA -> EMERGENZA; 
		Segnatura: rifiutato {mitt: destinazione} / richiestaAtterragio {dest: aeroportoVicino()}
		Evento: Rifiutato();
		Condizione: this.destinazione equals rifiutato.getMitt();
		Azione:
			pre: --;
			post: 
				this.destinazione = this.aeroportoVicino();
				// suppongo che aeroportoVicino() restituisca un Aeroporto diverso dal precedente
				nuovoEvento = richiestaAtterraggio(this, this.destinazione);
	Transizione: EMERGENZA -> HANGAR
		Segnatura: accettato  {mitt: destinazione} / arrivoInAeroporto
		Evento: Accettato()
		Condizione: this.destinazione equals accettato.getMitt();
		Azione:
			pre: --;
			post: 
				this.aeroporto = this.destinazione;
				this.destinazione = null;
FineSpecifica



```