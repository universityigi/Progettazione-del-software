```
>Complesse
AttivitaPrincipale(aerei : Insieme(Aereo), aeroporto: Aeroporto) : ()
Vola(aerei : Insieme(Aereo)) : ()
Monitora(aerei : Insieme(Aereo)) : (report : String)

>Atomiche
ControlloLocazione(aerei : Insieme(Aereo), aeroporto: Aeroporto) :  boolean
Avvio(aerei : Insieme(Aereo)) : ()
AggiungiAlReport(report: String, append : String) : String

>Output
OutputErrore() : ()
StampaReport(report : String) : ()

>Input
Terminazione() : ()
RichiestaDati() : String





```