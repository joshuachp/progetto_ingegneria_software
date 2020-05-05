# Changelog

## 05-05-2020

### Changed

-   Completato sequence diagram dell'autenticazione. Incertezza sull'alternativa in quanto l'ok del messaggio di verifica credenziali dovrebbe terminare, ma continua nell'altra alternativa. Una possibilità è di aggiungere il verifica credenziali all'alternativa.
-   Rinominato dei componenti autenticazione

## 03-05-2020

### Added

-   Aggiunto sequence diagram autenticazione

## 02-05-2020:

### Added

-   Aggiunto visualizza catalogo collegato che include visualizza carrello Non ho rimosso l'associazione da utente a visualizza carrello, anche se l'utente deve prima accedere alla homepage(catalogo e poi al carrello). Non l'ho eliminata perché se no avrei dovuto eliminare anche la visualizzazione del profilo per lo stesso principio, ma è una scelta discutibile, come la possibilità di mettere in packages diversi il carrello e il catalogo così da considerarli dei use cases ancora più separati. Un altra opzione può essere l'utilizzo di associazioni dirette in StarUML che potrebbero rappresentare questa situazione.
-   Aggiunto include visualizzazione prodotto da visualizzazione carrello.
-   Aggiunto visualizzazione profilo che include visualizzazione dello storico delle spese. Scelta discutibile però volevo inserire visualizzazione profilo de qualche parte.

### Changed

-   Autenticazione e Deautenticazione usati come use cases non associati in quanto visivamente non sono operazioni correlate, ache se sequenziali. Possiamo approfondire questo fatto nel diagramma sequenziale. - Mosso il ClienteUseCaseDiagram nel modello dei Use Case Diagram cosi da utilizzare le stessi attori e packages.
-   Cambiato da extend ad include per la rimozione di un prodotto dal carrello perché e come tutte le altre opzioni e non è opzionale in quanto deve esserci sempre la possibilità.
-   Cambiato include da visualizza carrello a eccettua spesa con conferma spesa in quanto è una conferma dei prodotti aggiunti al carrello dal catalogo.

### Removed

-   Rimosso include tra aggiunta prodotto e visualizza carrello, in quanto non si può aggiungere un nuovo prodotto dalla visualizzazione del carrello ma solo modificare la quantità.
