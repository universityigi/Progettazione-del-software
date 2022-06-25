```
SpecificaTransizioni Mezzo
	Transizione: RIPOSO -> IN_MARCIA
		Segnatura: partenza
		Evento: Partenza()
		Condizione: --;
		Azione: 
			pre:--;
			post: --;
	Transizione: IN_MARCIA -> RIPOSO
		Segnatura: stop
		Evento: Stop()
		Condizione: --
		Azione: 
			pre:--;
			post: --;
	Transizione: IN_MARCIA -> IN_MARCIA
		Segnatura: carica(persone: Insieme(Persona)) / caricate {dest: carica.getMitt()}
		Evento: Carica(persone: Insieme(Persona))
		Condizione: --; //Forse dovrei controllare che vengono dalla stessa carovana?
		Azione: 
			pre:--;
			post: 
				this.addPersone(carica.getPersone());
				nuovoEvento = Caricate(this, carica.getMitt()); 
	Transizione: IN_MARCIA -> ATTESA 
		Segnatura: guasto / accogliere
		Evento: Guasto()
		Condizione: --;
		Azione: 
			pre:--;
			post: nuovoEvento = Accogliere(this, null) //broadcasting
	Transizione: ATTESA -> TRASBORDO
		Segnatura: ok / carica(persone: Insieme(Persona)) {dest: soccorso}
		Evento: Ok()
		Condizione: --;
		Azione: 
			pre:--;
			post: 
				rimuove i passeggeri;
				soccorso = ok.getMitt();
				nuovoEvento = Carica(this, soccorso, this.getPassegeri());
	Transizione: TRASBORDO -> RIPOSO
		Segnatura: caricate{mitt: soccorso}
		Evento: Caricate
		Condizione: caricate.getMitt() equals soccorso
		Azione: 
			pre:--;
			post: --;
FineSpecifica
```