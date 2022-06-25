```java

public abstract class Aereo implements Listener {
	private final Double peso;
	private final Double serbatoio;
	private final HashSet<LinkPilotare> link;
	private ResponsabileComunicazioni assegnato;
	private Aeroporto aeroporto; 

	public static enum Stati {HANGAR, PISTA, IN_VOLO, EMERGENZA};
	Stati statoCorrente = Stati.HANGAR;
	Aeroporto aeroporto = null;

	protected Aereo(Double peso, Double serbatoio, ResponsabileComunicazioni assegnato, Aeroporto aeroporto) {
		if (peso==null || serbatoio==null || assegnato==null || aeroporto==null) throw new RuntimeException("Must be not null");
		this.peso = peso;
		this.serbatoio = serbatoio;
		this.link = new HashSet<LinkPilotare>;
		this.assegnato = assegnato;
		this.aeroporto = aeroporto; 
	}
	
	public Aeroporto AeroportoVicino() {
		//deve esserci, ma non la definisco io;
	}

	public synchronized void fired(Evento e) {
		if (e == null) return;
		TaskExecutor.getInstance().perform(new AereoFired(this, e));
	}

	public int quantiPiloti() {
		return link.size();
	}

	public LinkPiloti getLinkPiloti() {
		if (quantiPiloti() != 2) {
			throw new RuntimeException("Illegal state: i piloti devono essere esattamente due");
		}
		return link;
	}

	public void setAeroporto(Aeroporto a) {
		aeroporto = a;
	}

	public Aeroporto getAeroporto() {
		return aeroporto;
	}

	public void insertLink(LinkPilotare l) {
		if (l==null || !l.getAereo().equals(this)) return;
		ManagerLink.insert(l);
	}

	public void eliminaLink(LinkPilotare l) {
		if (l==null || !this.link.contains(l)) return;
		ManagerLink.elimina(l);
	}

	public void insertForManager(ManagerLink m) {
		if (m==null) return;
		link.add(m.getLink());
	}

	public void eliminaForManager(ManagerLink m) {
		if (m==null) return;
		link.remove(m.getLink());
	}

}

public class AereoCargo extends Aereo {
	public AereoCargo(Double peso, Double serbatoio, ResponsabileComunicazioni assegnato, Aeroporto aeroporto) {
		super(peso, serbatoio, assegnato, aeroporto);
	}
}

public class AereoPassegeri extends Aereo {
	public AereoCargo(Double peso, Double serbatoio, ResponsabileComunicazioni assegnato, Aeroporto aeroporto) {
		super(peso, serbatoio, assegnato, aeroporto);
	}
}

public class LinkPilotare {
	private final Pilota pilota;
	private final Aereo aereo;

	public LinkPilotare(Pilota pilota, Aereo aereo) {
		this.pilota = pilota;
		this.aereo = aereo;
	}

	public Pilota getPilota() {
		return pilota

	}

	public Aereo getAereo() {
		return aereo;
	}

	@Override
	public int hashcode() {
		return aereo.hashcode() + 4* pilota.hashcode();
	}

	@Override
	public boolean equals(Object o) {
		if(o==this) return true;
		if(o==null || !o.getClass().equals(this.getClass())) return false;
		LinkPilotare l = (LinkPilotare) o;
		return l.getAereo().equals(aereo) && l.getPilota().equals(pilota);
	}
}

public final ManagerLink {
	private final LinkPilotare link;
	
	private ManagerLink(LinkPilotare link) {
		this.link = link;
	}

	public static void insert(LinkPilotare l) {
		if (l==null || l.getPilota().getAereo() != null) return;
		l.getPilota().insertForManager(new ManagerLink(l));
		l.getAereo().insertForManager(new ManagerLink(l));
	}

	public static void elimina(LinkPilotare l) {
		if (l==null || l.getPilota().getAereo() != null) return;
		l.getPilota().eliminaForManager(new ManagerLink(l));
		l.getAereo().eliminaForManager(new ManagerLink(l));
	}
}

class AereoFired implements Task{
	private boolean eseguita = false;
	private final Aereo that;
	private final Evento e;

	AereoFired(Aereo aereo, Evento e) {
		this.that = aereo;
		this.e = e;
	}

	public synchronized void esegui() {
		if (eseguita) return;
		eseguita = true;

		switch(that.statoCorrente) {
			case HANGAR:
				if (e.getClass().equals(Partenza.class)) {
					that.statoCorrento = Aereo.Stati.PISTA;
					that.destinazione = ((Partenza) e).getDestinazione();
				}
				break;
			case PISTA:
				if (e.getClass().equals(Decollo.class) && e.getMitt().equals(that.getAeroporto())) {
					that.statoCorrente = Aereo.Stati.IN_VOLO;
					that.setAereoporto(null);
				break;
			case IN_VOLO:
				if (e.getClass().equals(Atterra.class)) {
					that.statoCorrente = Aereo.Stati.HANGAR;
					that.setAereoporto(that.destinazione);
					that.destinazione = null;
				} else if (e.getClass().equals(Emergenza.class)) {
					that.statoCorrente = Aereo.Stati.EMERGENZA;
					that.destinazione = that.aereoportovicino();
					Environment.addEvent(new RichiestaAtterraggio(that, that.destinazione));
				}
				break;
			case EMERGENZA:
				if (e.getMitt().equals(that.destinazione)) {
					if (e.getClass().equals(Rifiutato.class)) {
						that.destinazione = that.aereoportovicino();
						Environment.addEvent(new RichiestaAtterraggio(that, that.destinazione));
					} else if (e.getClass().equals(Accettato.class)) {
						that.statoCorrente = that.Stati.HANGAR;
						that.setAereoporto(that.destinazione);
						that.destinazione = null;
					}
				}
				break;
			default:
				throw new RuntimeException("Stato non riconosciuto");
		}
	}
}
						



```
