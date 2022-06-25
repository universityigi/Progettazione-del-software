```
SpecificaAttivitaComplesse SottoAttivita1
	SottoAttivita1(f: Insieme(File)) : (boolean)

	Variabili Ausiliarie: 
		compilati : Insieme(FileCompilati)
		
	Inizio Processo:
		Compila(f) : compilati;
		result = Testa(compilati);
	FineProcesso
FineSpecifica


SpecificaAttivitaComplesse SottoAttivita2
	SottoAttivita2(f: Insieme(File)) : (Pacchetto)
	
	Variabili Ausiliarie: --;

	InizioProcesso:
		result = Assembla(f);
	FineProcesso
FineSpecifica


SpecificaAttivitaComplesse AttivitaPrincipale
	attivitaPrincipale(progetto Progetto) : ()

	Variabili Ausiliarie:
		ok : boolean;
		pacchetto : Pacchetto

	InizioProcesso
		ok = Controllo(progetto.getRichieste);
		if (!ok) {
			SegnaliIO.ErroreRichiesta();
			return;
		}
		
		fork {
			thread t1: SottoAttivita1(progetto.getFiles()) : ok;
			thread t2: SottoAttivita2(progetto.getFiles()) : pacchetto;
		} join t1, t2;
		
		if (!ok) {
			SegnaliIO.ErroreTesting();
			return;
		}

		SegnaliIO.Conferma();
		RilasciaPacchetto(pacchetto);
	FineProcesso
FineSpecifica
```
		