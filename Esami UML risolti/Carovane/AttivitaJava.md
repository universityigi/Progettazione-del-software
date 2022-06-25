```java
public class AttivitaPrincipale implements Task {
	private static final TaskExecutor exe = TaskExecutor.getInstance();
	private boolean eseguita;
	private final int limitePersone;
	private final Carovana carovana;
	private boolean ok;
	private String report;
	
	public AttivitaPrincipale(Carovana carovana, int limitePersone) {
		this.carovana = carovana;
		this.limitePersone = limitePersone;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;
		
		Verifica verifica = new Verifica(carovana, limitePersone);
		exe.perform(verfica);
		ok = verifica.getRes();
		
		if(!ok) {
			exe.perform(new OutputErrore());
			return;
		}

		Analisi analisi = new Analisi(carovana);
		
		Thread t1 = new Thread(new Esecuzione(carovana));
		Thread t2 = new Thread(new Analisi(carovana));
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		report = analisi.getRes();
		exe.perform(new OutputAnalisi(report));

	}

	public synchronized boolean estEseguita() {
		return eseguita;
	}
}

public class Esecuzione implements Task {
	private static final TaskExecutor exe = TaskExecutor.getInstance();
	private boolean eseguita;
	private final Carovana carovana;
	
	public Esecuzione(Carovana carovana) {
		this.carovana = carovana;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;
		
		exe.perform(new Avvia(carovana));
		exe.perform(new AttendiTermine());

	}

	public synchronized boolean estEseguita() {
		return eseguita;
	}
}


public class Analisi implements Task {
	private static final TaskExecutor exe = TaskExecutor.getInstance();
	private boolean eseguita;
	private final Carovana carovana;
	private String result;
	
	public Analisi(Carovana carovana) {
		this.carovana = carovana;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;
	
		CalcolaReport calc = new CalcolaReport();
		exe.perform(calc);
		result = calc.getRes();
	}

	public synchronized boolean estEseguita() {
		return eseguita;
	}

	public synchronized String getRes() {
		if (result==null) {
			throw new RuntimeException("Result ancora non é stato calcolato");
		}
		return result;
}
		


```