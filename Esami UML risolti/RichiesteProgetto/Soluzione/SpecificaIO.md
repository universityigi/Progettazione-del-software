```
InizioSpecificaI/O 
	ErroreRichiesta() : () 
		pre:--;
		post: stampa a video un errore che comunica che tra le richieste del progetto ce ne é una bloccante;
	
	ErroreTesting() : ()
		pre: --;
		post: stampa a video un errore che comunica che la fase di testing non é andata a buon fine
	
	Conferma() : ()
		pre: --;
		post: stampa a video un messaggio che comunica il buon esito della fase di testing
FineSpecificaI/O


opzionale:
	InputProgetto() : (Progetto)
		pre: --;
		post: Attende in input un Progetto, tale é il result;
```