```java
public class Giocatore implements Listener {
	private final String nome;
	private final HashSet<Nave> flotta;
	private LinkPartecipa linkGioco; // deve essere sempre almeno uno
	
	public Giocatore(String nome) {
		this.nome = nome;
		flotta = new HashSet<>();
	}
	
//vincoli di molteplicitá
	public int quantiGioco() {
		return (linkGioco==null)? 0 : 1;
		// si potrebbe fare anche con una funzione booleana isGiocoPresent()
	}

	public LinkPartecipa getLinkGioco() {
		if (linkGioco == null) {
			throw new RuntimeException("Vincoli di molteplicitá violati");
		}
		return linkGioco;
	}
//inserisci il link con responsabilitá multipla
	public void insertLinkGioco(LinkPartecipa l) {
		if (l==null || !l.getGiocatore().equals(this)) {
			return;
		}
		ManagerLink.insert(l);
	}

	public void insertForManager(ManagerLink m) {
		if (m == null) return;
		linkGioco = m.getLink();
	}

	public void deleteLinkGioco(LinkPartecipa l) {
		if (l==null || !l.equals(this.linkGioco)) {
			return;
		}
		ManagerLink.delete(l);
	}

	public void deleteForManager(ManagerLink m) {
		if (m == null) return;
		linkGioco = null;
	}
	
// Listener e stati
	
	public static enum Stato {RIPOSO, IN_GIOCO}
	Stato statoCorrente = RIPOSO;
	//non ci sono var aux

	public void fired(Evento e) {
		if (e==null) return;
		TaskExecutor.perform(new GiocatoreFired(this, e);
	}

//Getters
	public Gioco getGioco() {
	//funzione utile ma non necessaria
		return this.getLinkGioco().getGioco();
	}

//utility
	boolean isColpito(int x, int y) {
		for (Nave n : flotta) {
			if (n.getClass().equals(NaveSemplice.class) && if n.getPosizione().getX() == x && n.getPosizione().getY() == y) {
				return true;
			} else if (n.getClass().equals(NaveSpeciale.class) {
				for (Posizione p: n.getPosizioni()) {
					if (p.getX() == x && p.getY()==y) {
						return true;
					}
				}
			}
		}
		return false;
	}

	int generateX() { ... }
	int generateY() { ... } //mi sono date, io non le definisco
}

class GiocatoreFired implements Task {
	private final Giocatore that;
	private final Evento e;
	private boolean eseguita = false;

	public GiocatoreFired(Giocatore that, Evento e) {
		this.that = that;
		this.e = e;
	}

	public synchronized void esegui() {
		if (eseguita) return;
		eseguita = true;

		if (e == null || (!e.getDest().equals(that) && e.getDest() != null)) return;

		switch(that.statoCorrente) {
		case RIPOSO:
			if (e.getClass().equals(SiGioca.class) &&  e.getMitt().equals(that.getGioco().getArbitro()) {
				that.statoCorrente = Giocatore.Stato.IN_GIOCO;
			break;
		case IN_GIOCO:
			if (e.getClass().equals(Colpo.class)) {
				Colpo colpo = (Colpo) e;
				if (that.isColpito(colpo.getX(), colpo.getY())) {
					that.setColpito(true, colpo.getX(), colpo.getY());
				} else {
					Environment.addEvent(new Colpo(that, colpo.getMitt(), that.generateX(), that.generateY()));
				}
			} else if (e.getClass().equals(Fine.class) && e.getMitt().equals(that.getGioco().getArbitro()) {
				that.statoCorrente = RIPOSO;
			}
			break;
		default:
			throw new RuntimeException("Stato non riconosciuto");
		}
	}
}


public class LinkPartecipa {
	private final Gioco gioco;
	private final Giocatore giocatore;

	public LinkPartecipa(Gioco gioco, Giocatore giocatore) {
		this.gioco = gioco;
		this.giocatore = giocatore;
	}

	@Override
	public int hashcode() {
		return gioco.hashcode() + 8*giocatore.hashcode;
	}
		
	@Override
	public boolean equals(Object o) {
		if (o == e) return true;
		if (o == null || o.getClass().equals(LinkPartecipa.class)) return false;	
		LinkPartecipa l = (LinkPartecipa) o;
		return gioco.equals(l.getGioco()) && giocatore.equals(l.getGiocatore());
	}

	//Getters
	public Gioco getGioco() {
		return gioco;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}
}

public final class ManagerLink {
	private final LinkPartecipa link;
	
	private ManagerLink(LinkPartecipa link) {
		this.link = link;
	}
	
	public static void insert(LinkPartecipa link) {
		if (link == null || link.getGiocatore().quantiGioco() == 1) return;
		
		link.getGiocatore().insertForManager(new ManagerLink(link));
		link.getGioco().insertForManager(new ManagerLink(link));
	}

	public static void delete(LinkPartecipa link) {
		if (link == null || link.getGiocatore().quantiGioco() == 0 || !link.getGiocatore().getLinkGioco().equals(link) || !link.getGioco.getLinkGiocatori().contains(link)) return;
		
		link.getGiocatore().deleteForManager(new ManagerLink(link));
		link.getGioco().deleteForManager(new ManagerLink(link));
	}
	
	public LinkPartecipa getLink() {
		return link;
	}
}
```