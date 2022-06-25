```java
public class AttivitaPrincipale implements Runnable {
	private final HashSet<Aereo> aerei;
	private final Aeroporto aeroporto;
	private boolean ok;
	private String report;
	private boolean eseguita = false;
	
	public AttivitaPrincipale(HashSet<Aereo> aerei, Aeroporto aeroporto) {
		this.aerei = aerei;
		this.aeroporto = aeroporto;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		ControlloLocazione controllo = new ControlloLocazione(aerei);
		TaskExecutor.getInstance().perform(controllo);
		ok = controllo.getRes();

		if (!ok) {
			TaskExecutor.getInstance().perform(new OutputErrore());
			return;
		}

		Vola vola = new Vola(aerei);
		Monitora monitora = new Monitora(aerei);
		
		Thread t1 = new Thread(vola);
		Thread t2 = new Thread(monitora);
		
		t1.start();
		t2.start();
		
		try{
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		report = monitora.getRes();
		
			
		TaskExecutor.getInstance().perform(new StampaReport(report));
	}
}

public class Monitora implements Runnable {
	private final HashSet<Aereo> aerei;
	private String s;
	private boolean eseguita = false;
	private String report //result

	public Monitora(HashSet<Aereo> aerei) {
		this.aerei = aerei;
	}

	public synchronized void run() {
		if (eseguita) return;
		eseguita = true;

		do {
			RichiestaDati request = new RichiestaDati(aerei);
			TaskExecutor.getInstance().perform(request);
			s = request.getRes()
			
			AggiungiAlReport append = new AggiungiAlReport(report, s);
			TaskExecutor.getInstance().perform(append);
			report = append.getRes();
		} while(s != TERMINATION_STRING)
		//Immagino che ad un certo punto verrá inviata un segnale di terminazione che significherá che tutti gli aerei sono tornati a terra
	}

	public String getRes() {
		return report;
	}
}
```
