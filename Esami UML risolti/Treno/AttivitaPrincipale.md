```java
public class AttivitaPrincipale implements Runnable {
	private Treno treno;
	private Set<Vagone> vagoni;  // Java Collection Framework
	private String oraArrivo;
	private boolean eseguita = false;
	
	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		treno = SegnaliIO.InputTreno();
		vagoni = SegnaliIO.InputVagoni();

		Verifica verifica = new Verifica(vagoni); //Verifica é una attivitá atomica
		TaskExecutor.perfrom(verifica);
		if (verifica.getRes()) {
			SegnaliIO.OutputErrore();
			return;
		}

		Thread formazione = new Thread(new Formazione(vagoni));
		Thread Orario = new Thread(new Orario(treno));
		formazione.start();
		orario.start();

		try {
			formazione.join();
			orario.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		oraArrivo = orario.getRes(); // faccio bene a metterlo come var ausiliaria?

		StampaOra(oraArrivo);
	}
}



public class Formazione implements Runnable {
	private boolean eseguita = false;
	private final Set<Vagoni> vagoni;
	
	public Formazione(Set<Vagoni> vagoni) {
		this.vagoni = vagoni;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		TaskExecutor.perform(new Spostamenti(vagoni));
	}
}

public class Orario implements Runnable {
	private boolean eseguita = false;
	private final Treno treno;
	private Ora oraPartenza;
	private Itinerario itinerario;

	private String oraArrivo;
	
	public Orario(Treno treno) {
		this.treno = treno;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		oraPartenza = SegnaliIO.InputOraPartenza();
		itinerario = SegnaliIO.InputItinerario();

		Calcolo calcolo = new Calcolo(oraPartenza, itinerario)
		TaskExecutor.perform(calcolo);

		oraArrivo = calcolo.getRes();
	}

	public String getRes() {
		return oraArrivo;
	}
}
		
```
	 
		