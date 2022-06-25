```java
public class AttivitaPrincipale implements Runnnable {
	private boolean esegui = false;
	private boolean ok;
	private String report;
	private final Gioco g;
	private static final TaskExecutor exe = TaskExecutor.getInstance();
	// quest'ultimo non é necessario ma semplifica la lettura dopo

	public AttivitaPrincipale(Gioco g) {
		this.g = g
	}
	
	public synchronized void run() {
		if (esegui) return;
		esegui = true;

		Verifica verifica = new Verifica(g.getGiocatori());
		exe.perform(verifica);
		ok = verifica.getRes();

		if (!ok) {
			exe.perform(new OutputErrore());
			return;
		}

		Analisi analisi = new Analisi(g.getGiocatori());
		
		Thread t1 = new Thread(new GiocoAttivita(g));
		Thread t2 = new Thread(analisi);
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		report = analisi.getRes();
		
		CalcoloVincitore calc = new CalcoloVincitore(g.getGiocatori());
		exe.perform(calc);

		exe.perform(new OutputFine(calc.getRes(), report));
		
	}
}



public class GiocoAttivita implements Runnable {
	private boolean eseguita = false;
	private final Gioco g;
	private static final TaskExecutor exe = TaskExecutor.getInstance();
	

	public GiocoAttivita(Gioco g) {
		this.g = g;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;
		
		exe.perform(new AttivaGioco(g));
		exe.perform(new AttendiTermine());
	}
}

public class Analisi implements Runnable {
	private boolean esegui;
	private final HashSet<Giocatore> giocatori;
	private String result;
	private static final TaskExecutor exe = TaskExecutor.getInstance();
	
	public Analisi(HashSet<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		Report report =  new Report(giocatori);
		exe.perform(report);
		result = report.gerRes();
	}

	public String getRes() {
		return result;
	}
}
```