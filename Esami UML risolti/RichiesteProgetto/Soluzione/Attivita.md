```java

public class SottoAttivita1 implements Runnable {
	private boolean eseguita = false;
	private final HashSet<File> files;
	private HashSet<FileCompilati> compilati;
	private boolean result;

	public SottoAttivita1(HashSet<File> files) {
		this.files = files;
	}

	@Override
	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		
		Compila compila = new Compila(files);
		TaskExecutor.getInstance().perform(compila);
		compilati = compila.getRes(); // in realtá non é che sia proprio necessaria la var aux, la dovrei tenere lo stesso?
		
		Testa testa = new Testa(compilati);
		TaskExecutor.getInstance().perform(testa);
		
		result = testa.getRes();
	}

	public boolean getRes() {
		return result;
	}
}

public class SottoAttivita2 implements Runnable {
	private boolean eseguita = false;
	private final HashSet<File> files;
	private Pacchetto result;
	
	public SottoAttivita2(HashSet<File> files) {
		this.files = files;
	}

	@Override
	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;
		
		Assembla assembla = new Assembla(files);
		TaskExecutor.getInstance().perform(assembla);
		result = assembla.getRes();
	}

	public Pacchetto getRes() {
		return result;
	}
}

public class AttivitaPrincipale implements Runnable {
	private boolean eseguita = false;
	private boolean ok;
	private Pacchetto pacchetto;
	private Progetto progetto;

	public Progetto(Pacchetto pacchetto) {
		this.pacchetto = pacchetto;
	}

	@Override
	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		Controllo controllo = new Controllo(progetto.getRichieste);
		TaskExecutor.getInstance().perform(controllo);

		ok = controllo. getRes();

		if (!ok) {
			SegnaliIO.ErroreRichiesta();
			return;
		}
		
		SottoAttivita1 s1 = new SottoAttivita1(progetto.getFiles();
		SottoAttivita2 s2 = new SottoAttivita2(progetto.getFiles();
		Thread t1 = new Thread(s1);
		Thread t2 = new Thread(s2);
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ok = s1.getRes();
		pacchetto = s2.getRes();

		if (!ok) {
			SegnaliIO.ErroreTesting();
			return;
		}

		SegnaliIO.Conferma();
		RilasciaPacchetto(pacchetto);
	}
}
		



```