```
InizioSpecificaTransizioni Richiesta
	Transizione: CREATA -> CHIUSA
		Segnatura: chiudi {mitt: this.creatore}
		Evento: Chiudi()
		Condizione: chiudi.getMitt() equals this.creatore;
		Azione:
			pre: --
			post: statoCorrente = CHIUSA

	Transizione: CREATA -> INVIATA
		Segantura: invia {mitt: this.creatore}
		Evento: Invia()
		Condizione: invia.getMitt() equals this.creatore
		Azione:
			pre:--
			post: statoCorrente = INVIATA

	Transizione: INVIATA -> CHIUSA
		Segnatura: chiudi {mitt: manutentore}
		Evento: Chiudi()
		Condizione: this.getProgetto().getManutentori() contains chiudi.getMitt()
		Azione: 
			pre:--
			post: statoCorrente = CHIUSA

	
	Transizione: INVIATA -> ATTESA
		Segnatura: suggerimento {mitt: manutentore
		Evento: Suggerimento()
		Condizione: this.getProgetto().getManutentori() contains suggerimento.getMitt()
		Azione: 
			pre:--
			post: 
				statoCorrente = ATTESA
				manutentore = suggerimento.getMitt()
				// bisogna ricordarsi chi é il 
				manutentore perché dopo gli andrá
				notificato l'eventuale chiusura/modifica della richiesta

	Transizione: ATTESA -> CHIUSA
		Segnatura: chiudi {mitt: this.creatore}/notifica{dest:manutentore}
		Evento: Chiudi()
		Condizione: chiudi.getMitt() equals this.creatore
		Azione: 
			pre:--
			post: 
				statoCorrente = CHIUSA
				nuovoevento = notifica(this,this.manutentore)	

	Transizione: ATTESA -> INVIATA
		Segnatura: modifica{mitt: this.creatore} / notifica{dest: this.manutentore}
		Evento: Modifica()
		Condizione: modifica.getMitt() equals this.creatore
		Azione: 
			pre:--
			post: 
				statoCorrente = INVIATA
				nuovoevento = notifica(this,this.manutentore)
FineSpecifica
```