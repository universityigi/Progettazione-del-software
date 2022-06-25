```
InizioSPecificaAttivita: AttivitaPrincipale
	Segnatura: AttivitaPrincipale(limite: int, carovana: Carovana) 
	Variabili di processo: 
		report : String
		ok : boolean
	InizioProcesso:
		if (!Verifica) {  //Task
			OutputErrore(); //IO
			return;
		}
		fork {
			t1: Esecuzione(carovana); //Complessa
			t2: Analisi(carovana) : report;  //Complessa
		} join t1, t2;
		
		OutputAnalisi(report); //IO
	FineProcesso
FineSpecifica

InizioSPecificaAttivita: Esecuzione
	Segnatura: esecuzione(Carovana carovana):()
	Variabili di processo: --;
	InizioProcesso:
		Avvia(carovana); //Task
		AttendiTermine(); //IO
	FineProcesso
FineSpecifica

InizioSPecificaAttivita: Analisi
	Segnatura: Analisi(Carovana carovana) : (result String)
	Variabili di processo: 
		result: String;
	InizioProcesso:
		CalcoloReport(carovana) : result; // Task
	FineProcesso
FineSpecifica

```