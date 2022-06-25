```
InizioSpecificaStati Giocatore
	Stati: {RIPOSO, IN_GIOCO}
	Var Aux: --
	StatoIniziale:
		statoCorrente = RIPOSO
FineSpecifica

InizioSpecificaTransizioni Giocatore
	Transizione: RIPOSO -> IN_GIOCO
		Segnatura: siGioca {mitt: this.gioco.arbitro}
		Evento: SiGioca()
		Condizione: siGioca.getMitt() equals this.gioco.getArbitro();
		Azione:
			pre:--;
			post: imposta a false tutti i campi "colpita" di tutte le posizioni delle proprie navi
				
	Transizione: IN_GIOCO -> IN_GIOCO
		Segnatura: colpo(x: int, y: int)[colpita]
		Evento: Colpo(x: int, y: int)
		Condizione: tra le navi del giocatore ce ne é almeno una che ha come posizione una che abbia posizione.x == colpo.x e posizione.y == colpo.y
		Azione:
			pre:--;
			post: 
				nella posizione della nave colpita si imposta il campo "colpita" a true

	Transizione: IN_GIOCO -> IN_GIOCO
		Segnatura: colpo(x: int, y: int)[!colpita]/colpo(w: int, z: int);
		Evento: Colpo(x: int, y: int)
		Condizione: tra le navi del giocatore non ce ne é nessuna che abbia posizione corrispondente a colpo.x e colpo.y
		Azione:
			pre:--;
			post: nuovoEvento = Colpo(this.generateX(), this.generateY());
				// generateX() é la funzione che genera le coordinate  

	Transizione: IN_GIOCO -> RIPOSO
		Segnatura: fine {mitt: Arbitro}
		Evento: fine()
		Condizione: fine.getMitt() equals this.gioco.getArbitro();
		Azione:
			pre:--;
			post: --;
FineSpecifica

```