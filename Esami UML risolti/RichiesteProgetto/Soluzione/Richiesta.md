```java
public class Richiesta implements Listener {
	private final int codice;
	private String descrizione;
	private final Utente creatore;
	private final HashSet<File> files; //DEVE avere almeno un elemento
	private LinkRiguarda linkProgetto;
	// considero il codice della richiesta, l'utente creatore e il progetto che riguarda come valori immutabili
	
	static enum Stato {CREATA, CHIUSA, INVIATA, ATTESA};
	Stato statoCorrente = CREATA;
	Utente manutentore;
	
	public Richiesta(int codice, String descrizione, Utente creatore) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.creatore = creatore;
		this.modifica = new HashSet<File>;
	}

	public synchronized void fired(Evento e) {
		if (e == null) return;
		TaskExecutor.getInstance().perform(new RichiestaFired(this, e));
	}

	public void insertLinkProgetto(LinkRiguarda l) {
		if (l == null || !l.getRichiesta.equals(this) ) return;
		ManagerRiguarda.insert(l);
	}

	public void insertForManager(MangerRiguarda m) {
		if ( m == null ) return;
		linkProgetto = m.getLink();
	}

	// non metto eliminaProgetto() e eliminaForManager perché ho considerato il Progetto come un valore immutabile
	
	public void addFile(File f) {
		if (f == null) return;
		files.add(f);
	}
	
	public Set<File> getFiles() {
		if (files.size() >= 1) {
			return files;
		} 
		throw new RuntimeException("Illegal state: files must contain at least 1 File");
	}

	public int quantiFiles() {
		return files.size();
	}

	public void setDescrizione(String s) {
		descrizione = s;
	}

	public String getDescrizione() {
		return descrizione.copy();
	}

	public Utente getCreatore() {
		return this.creatore;
	}

	public LinkRiguarda getLink() {
		if (linkProgetto == null) {
			throw new RuntimeException("Illegal state, must contain at least 1 Progetto")
			return null;
		}
		return linkProgetto;
	}

	public int quantiLink() {
		return (linkProgetto==null)? 0 : 1;
	}
	
	public Progetto getProgetto() {
	//metodo non necessario ma comodo
		return getLink().getProgetto();
	}

}

class RichiestaFired implements Task {
	private final Richiesta that;
	private final Evento e;
	private boolean eseguita;

	RichiestaFired(Richiesta that, Evento e) {
		this.that = that;
		this.e = e;
	}

	public synchronized void esegui() {
		if (eseguita) return;
		eseguita = true;
		
		switch(that.statoCorrente) {
			case CREATA:
				if (e.getClass().equals(Chiudi.class) && e.getMitt().equals(that.getCreatore()) {
					that.statoCorrente = Richiesta.Stato.CHIUSA;
				} else if (e.getClass().equals(Invia.class) && e.getMitt().equals(that.getCreatore()) {
					that.statoCorrente = Richiesta.Stato.INVIATA;
				}
				break;
			case CHIUSA:
				// Chiusa é un ending point, in questo stato l'oggetto non é reattivo
				break;
			case INVIATA:
				if (that.getProgetto().getManutentori().contains(e.getMitt())) {
					if (e.getClass().equals(Chiudi.class)) {
						that.statoCorrente = Richiesta.Stato.CHIUSA;
					} else if (e.getClass().equals(Suggerimento.class)) {
						that.statoCorrente = Richiesta.Stato.ATTESA;
						that.manutentore = (Utente) e.getMitt();
					}
				}
				break;
			case ATTESA:
				if (that.getCreatore().equals(e.getMitt())) {
					if (e.getClass().equals(Chiudi.class)) {
						that.statoCorrente = Richiesta.Stato.CHIUSA;
						Environment.addEvento(new Notifica(that, manutentore));
						manutentore = null;
					} else if (e.getClass().equals(Modifica.class)) {
						that.statoCorrente = Richiesta.Stato.INVIATA;
						Environment.addEvento(new Notifica(that, manutentore));
						manutentore = null;
					}
				break;
			default:
				throw new RuntimeException("Unrecognized Status");
		}
	}

}

public class LinkRiguarda {
	private final Progetto progetto;
	private final Richiesta richiesta;

	public LinkRiguarda(Progetto progetto, Richiesta richiesta) {
		this.progetto = progetto;
		this.richiesta = richiesta;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public Richiesta getRichiesta() {
		return richiesta;
	}

	@Override
	public int hashcode() {
		return progetto.hashcode() + 3*richiesta.hashcode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || !o.getClass().equals(this.getClass())) return false;
		LinkRiguarda l = (LinkRiguarda) o;
		return l.getRichiesta().equals(richiesta) && l.getProgetto().equals(progetto);
	}
}

public final class ManagerRiguarda {
	private final LinkRiguarda link;
	
	private ManagerRiguarda(LinkRiguarda link) {
		this.link = link;
	}

	public static void insert(LinkRiguarda link) {
		if (link == null || link.getRichiesta().quantiLink() == 0) return;
		link.getRichiesta().insertForManager(new ManagerRiguarda(link));
		link.getProgetto().addForManager(new ManagerRiguarda(link));
	}
}
```
