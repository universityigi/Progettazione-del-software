```
SpecificaAttivitaAtomica Controllo
	controllo(i : Insieme(Richiesta)) : (boolean)
		pre: i != null;
		post: result é true se nessuna delle richieste in i é di tipo RichiestaBloccante
			false altrimenti
FineSpecifica

SpecificaAttivitaAtomica Compila
	compila(f : Insieme(File)) : (Insieme(FileCompilati)) //FileCompilati é un tipo ad hoc che rappresenta file compilati, se fossimo in java questo corrisponderebbe a file con estensione .class
		pre: f != null;
		post: compila i file e il result sono i file compilati;
FineSpecifica	

SpecificaAttivitaAtomica Testa
	testa(f : Insieme(FileCompilati)) : (boolean)
		pre: f != null;
		post: testa che il programma funzioni (non sono forniti maggiori dettagli) result é true se é funziona, false altrimenti;
FineSpecifica

SpecificaAttivitaAtomica Assembla
	assembla(f : Insieme(File)) : (Pacchetto)
		pre: f != null;
		post: comprime tutti i file in uno solo (come se creasse il .jar del progetto) result é tale pacchetto;
FineSpecifica	

SpecificaAttivitaAtomica RilascioPacchetto
	rilascioPacchetto(f : File) : ()
		pre: --;
		post: carica il pacchetto su qualche server(?) e lo rilascia al pubblico
	//Questa potrebbe essere considerata anche un'attivitá di output peró boh
FineSpecifica
```