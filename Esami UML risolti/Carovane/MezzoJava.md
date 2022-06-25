```java
public abstract class Mezzo implements Listener {
	private final String tipo;
	protected final HashSet<LinkPersona> linkPersone; // deve avere almeno una persona

	protected Mezzo(String tipo) {
		this.tipo = tipo;
		this.linkPersone = new HashSet<LinkPersona>();
	}

	//controllo vincoli molteplicitá
	public int quantePersone() {
		return linkPersone.size();
	}

	public HashSet<linkPersona> getLinkPersone() {
		if (quantePersone() < 1) {
			throw new RuntimeException("Deve avere almeno una persona");
		}
		return linkPersone; //dovrei restitutire una copia di questo?
	}

	
	// Funzioni per i Link
	public void addPersona(LinkPersona link) {
		if (link == null || !link.getMezzo().equals(this)) {
			return; //dovrei mandare un errore?
		}
		ManagerLinkPersone.insert(link);
	}

	public void addForManager(ManagerLinkPersone m) {
		if (m==null) return;
		linkPersone.put(m.getLink());
	}

	public void remove(LinkPersona link) {
		if (link==null || !linkPersone.contains(link)) {
			return;
		}
		ManagerLinkPersone.remove(link);
	}

	public void removeForManager(ManagerLinkPersona m) {
		if (m==null) return;
		linkPersone.remove(m.getLink());
	}
	
	
	//Listener
	public static enum Stato {RIPOSO, IN_MARCIA, ATTESA, TRASBORDO}
	Stato statoCorrente = RIPOSO;
	Mezzo soccorso;
	
	public void fired(Evento e) {
		TaskExecutor.getInstance().perform(new MezzoFired(this, e);
	}

	//Getters & Utility
	public HashSet<Persona> getPassegeri() {
		HashSet<LinkPersone> links = getLinkPersone();
		HashSet<Persona> result = new HashSet<>();

		for (LinkPersona l : links ) {
			result.put(l.getPersona());
		}
		return result;
	}

	public void bulkAddPersone(HashSet<Persona> persone) {
		for (Persona p : persone) {
			addPersona(new LinkPersona(this, p);
		}
	}

	public void bulkRemovePersone(HashSet<Persona> persone) {
		for (Persona p : persone) {
			removePersona(new LinkPersona(this, p);
		}
	}

	public void removePassegeri() {
		bulkRemovePersone(this.getPassegeri());
	}

	
}

public class MezzoGuidaAutonoma extends Mezzo {
	private int collaudo;

	public MezzoGuidaAutonoma(String tipo, int collaudo) {
		super(tipo);
		this.collaudo = collaudo;
	}
}

public class MezzoGuidaManuale extends Mezzo {
	private LinkPilota pilota; //Deve essere almeno uno (non puó essere null)
				//Deve essere compreso nei passeggeri
	
	public MezzoGuidaManuale(String tipo) {
		super(tipo);
	}

	public boolean pilotaExist() {
		return pilota!=null && linkPersone.contains(pilota);
		// controlla che il pilota ci sia e che sia contenuto nei passeggeri
	}

	public LinkPilota getPilota() {
		if ( !pilotaExist ) {
			throw new RuntimeException("Ci deve essere il pilota!")
		}
		return pilota;
	}

	// inserisci, inserisciForManager, rimuovi, rimuoviForManager sono simili a quelle che stanno sopra
	

	@Override
	public HashSet<Persona> getPassegeri() {
		HashSet<Persona> persone = super.getPasseggeri();
		persone.remove(getPilota().getPersona());
		return persone;
	}
}

public class LinkPersona {
	private final Mezzo mezzo;
	private final Persona persona;
	
	public LinkPersona(Mezzo mezzo, Persona persona) {
		this.mezzo = mezzo;
		this.persona = persona;
	}

	public Mezzo getMezzo() {
		return mezzo;
	}

	public Persona getPersona() {
		return persona;
	}
		

	public int hashcode() {
		return mezzo.hashcode() + persona.hashcode();
	}

	public boolean equals (Object o) {
		if (o==this) return  true;
		if (o==null || !o.getClass.equals(this.getClass()) ) return false;
		LinkPersona l = (LinkPersona) o;
		return l.getPersona().equals(this.persona) && l.getMezzo().equals(this.mezzo) ;
	}
}

public class LinkPilota extends LinkPersona {
	private int numeroGuide;
	
	public LinkPilota(Mezzo mezzo, Persona persona, int numeroGuide) {
		super(mezzo, persona);
		this.numeroGuide = numeroGuide;
	}
}

public final class ManagerLinkPersone {
	private final LinkPersona l;
		
	private ManagerLinkPersone(LinkPersona l) {
		this.l=l;
	}

	public static void insert(LinkPersona l) {
		if (l==null || l.getPersona().getLink() != null ) return;
		l.getPersona().insertForManager(new ManagerLinkPersone(l));
		l.getMezzo().addForManager(new ManagerLinkPersone(l));
	}

	public static void remove(LinkPersona l) {
		if (l==null || ! l.getPersona().getLink().equals(l) || ! l.getMezzo().getLinkPersone().conatains(l) ) return;
		l.getPersona().removeForManager(new ManagerLinkPersone(l));
		l.getMezzo().removeForManager(new ManagerLinkPersone(l));
	}
}

public final class ManagerLinkPilota {
	// quasi uguale a sopra
}		

class MezzoFired implements Task {
	private final Mezzo that;
	private final Evento e;
	private boolean eseguita = false;

	MezzoFired(Mezzo that, Evento e) {
		this.that = that;
		this.e = e 
	}

	public synchronized void esegui() {
		if (eseguita) return;
		eseguita = true;

		if (e==null || !(e.getMitt() == null || e.getMitt().equals(that)) )  return;

		switch(that.statoCorrente) {
		case RIPOSO: 
			if (e.getClass().equals(Partenza.class)) {
				that.statoCorrente = Mezzo.Stato.IN_MARCIA;
			}
			break;
		case IN_MARCIA:
			if (e.getClass().equals(Stop.class)) {
				that.statoCorrente = Mezzo.Stato.RIPOSO;
			} else if (e.getClass().equals(Carica.class)) {
				Carica c = (Carica) e;
				that.bulkAddPersone(c.getPersone());
				Environment.addEvent(new Caricate(that, e.getMitt());
			} else if (e.getClass().equals(Guasto.class)) {
				that.statoCorrente = Mezzo.Stato.ATTESA;
				Environment.addEvent(new Accogliere(that, null)) //broadcasting
			}
			break;
		case ATTESA:
			if (e.getClass().equals(Ok.class)) {
				that.statoCorrente = Mezzo.Stato.TRASPOSTO;
				that.soccorso = e.getMitt();
				HashSet<Persona> passegeri = that.getPassegeri();
				that.removePassegeri();
				Environment.addEvent(new Carica(that, that.soccorso, passegeri);
			}
			break;
		case TRASBORDO:
			if (e.getClass().equals(Caricate.class) && e.getMitt().equals(that.soccorso) ) {
 				that.statoCorrente = Mezzo.Stato.RIPOSO;
				that.soccorso = null;
			}
			break;
		default:
			throw new RuntimeException("Stato non riconosciuto");
		}
	}

	public synchronized boolean estEseguita() {
		return eseguita;
	}

}
```