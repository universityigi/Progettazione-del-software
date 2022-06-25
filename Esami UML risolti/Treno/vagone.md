```java

Public class Vagone implements Listener {
	Private final String codice;
	Private LinkVagoni nextVagone;
	Private LinkVagoni precVagone;
	
	public Vagone (String codice) {
		this.codice = codice;
	}
		
	Public void fired(Evento e) {
		TaskExecutor.getInstance().perform(new VagoneFired(this, e));
	}
	
	Public void addVagoneSuccessivo(LinkVagoni l) {
		If (l==null || !l.getPrecedente().equals(this) ) return;
		ManagerVagoni.insert(l);
	}
	
	Public void addSuccessivoForManager(Manager m) {
		If (m==null) return;
		nextVagone = m.getLink();
	}

	Public void addVagonePrecedente(LinkVagoni l) {
		If (l==null || !l.getSuccessivo().equals(this) ) return;
		ManagerVagoni.insert(l);
	}
	
	Public void addPrecedenteForManager(Manager m) {
		If (m==null) return;
		precVagone = m.getLink();
	}

	Public void eliminateVagoneSuccessivo(LinkVagoni l) {
		If (l==null ||!nextVagone.equals(l) ) return;
		ManagerVagoni. eliminate(l);
	}
	
	Public void eliminateSuccessivoForManager(Manager m) {
		If (m==null) return;
		nextVagone = null;
	}
	Public void eliminateVagonePrecedente(LinkVagoni l) {
		If (l==null ||!precVagone.equals(l) ) return;
		ManagerVagoni. eliminate(l);
	}
	
	Public void eliminateSuccessivoForManager(Manager m) {
		If (m==null) return;
		precVagone = null;
	}

	Enum stato {SOSTA, MOVIMENTO}
	stato statoCorrente = Stato.SOSTA;
	Vagone vagoneCandidato;
	// non ci sono altre var ausiliarie

	//GETTERS
	Public LinkVagoni getLinkSuccessivo() {
		Return nextVagone;
	}

	Public LinkVagoni getLinkPrec() {
		Return precVagone;
	}

	Public String getCodice() {
		Return codice;
	}
	
	// Questi due getter non sono strettamente necessari, ma semplificano le cose per dopo
	Public Vagone nextVagone() {
		If (nextVagone == null) return null;
		Return nextVagone.getSuccessivo();
	}

	Public Vagone precVagone() {
		If (precVagone == null) return null;
		Return precVagone.getPrecedente();
	}
}




Public class LinkVagoni {
	Private final Vagone precedente;
	private final Vagone successivo;
	
	public LinkVagoni(Vagone prec, Vagone succ) {
		this.precedente = prec;
		this.successivo = succ;
	}

	//Getters
	Public getPrecedente() { return precedente } 
	Public getSuccessivo() { return successivo }

	Public int hashcode() {
		Return precedente.hashcode() + successivo.hashcode();
	}

	Public boolean equals(Object o) {
		If (o == this) return true;
		If (o == null || !o.getClass().equals(getClass()) ) return false;
		LinkVagoni l = (LinkVagoni) o;
		Return l.getPrecedente.equals(precedente) && l.getSuccessivo.equals(successivo);
	}

}




Public final class ManagerVagoni {
	Private final LinkVagoni link;

	Private ManagerVagoni(LinkVagoni l) { 
		This.link= link;
	}

	Public static void insert(LinkVagoni l) {
		If (l==null || l.getPrecedente().nextVagone() != null || l.getSuccessivo().precVagone() != null) {
			Return;
		}
		l.getPrecedente().addSuccessivoForManager(new ManagerVagoni(l));
		l.getSuccessivo().addPrecedenteForManager(new ManagerVagoni(l));
	}

	Public static  void eliminate(LinkVagoni l) {
		If (l==null || l.getPrecedente().nextVagone() == null || l.getSuccessivo().precVagone() == null) {
			Return;
		}
		l.getPrecedente().eliminateSuccessivoForManager(new ManagerVagoni(l));
		l.getSuccessivo().eliminatePrecedenteForManager(new ManagerVagoni(l));
	}
}


Class VagoneFired implements Task {
	Private final Vagone that;
	Private final Evento e;
	Private Boolean eseguita;
	
	VagoneFired(Vagone v, Evento e) {
		This.that = v;
		This.e = e;
	}

	Public void synchronized esegui() {
		If (eseguita || e == null || (e.getDestinatario() != null && !e.getDestinatario().equals(that))){
		Return;
		}
		Eseguita = true;

		Switch(that.statoCorrente) {
			Case SOSTA:
				If (e.getClass().equals(Sposta.class) && that.precVagone() == null && that.nextVagone() == null && that.vagoneCandidato == null) {
					Sposta spostaEvento = (Sposta) e;
					That.vagoneCandidato = spostaVagone.getVagone();
					Environment.addEvent(new Arrivo(that, that.vagoneCandidato));
				} Else If (e.getClass().equals(Arrivo.class)) {
					If (this.nextVagone() == null) {
						Environment.addEvent(that, new Libero(this, e.getMittente());
					} else {
						Environment.addEvent(that, new Occupato(this, e.getMittente());
					}
				} else if (e.getClass().equals(Libero.class) && e.getMittente().equals(that.vagoneCandidato)) {
					That.addVagonePrecedente(new LinkVagoni(that.vagoneCandidato, that));
					That.vagoneCandidato = null;
				} else if (e.getClass().equals(Arrivo.class) && e.getMittente().equals(that.vagoneCandidato)) {
					That.vagoneCandidato = null;
		
				} else if(e.getClass().equals(Partenza.class) {
					That.statoCorrente = stato.MOVIMENTO;
				}
				Break;
			Case MOVIMENTO:
				If (e.getClass().equals(Arrivo.class)) {
					That.statoCorrente = stato.SOSTA;
				}
				Break;
			Default:
				Throw new RuntimeException(“Stato non riconosciuto”);
		
		}
	}
}		
```
