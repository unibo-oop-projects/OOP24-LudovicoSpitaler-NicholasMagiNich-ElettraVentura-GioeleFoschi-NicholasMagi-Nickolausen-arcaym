<div align="center">
  <img src="https://raw.githubusercontent.com/4realgames/OOP24-arcaym/refs/heads/main/src/main/resources/assets/title.png"/>
</div>
Email dei componenti:

* gioele.foschi@studio.unibo.it

* nicholas.magi@studio.unibo.it

* ludovico.spitaleri@studio.unibo.it

* elettra.ventura@studio.unibo.it



Il gruppo si pone come obiettivo la realizzazione un editor di livelli per il videogioco "World's Hardest Game".

Il gioco "World's Hardest Game" consiste in diversi livelli "visti dall'alto" in cui una pallina, che parte da un punto A, deve raggiungere un punto B evitando diversi ostacoli. Il nostro fine principale è realizzarne un editor che permetta di costruire livelli, che poi possano essere giocati. 



Funzionalità minimali ritenute obbligatorie (realizzabili in circa il 60-70% del tempo a disposizione):  

* Gioco minimale con un player, punto di partenza e arrivo (con relativo percorso in mezzo), un ostacolo di base. Gestione di collisioni e interazioni tra oggetti.

* Level editor: menù di selezione di oggetti per la creazione del livello, piazzamento e rimozione degli oggetti sul livello. 

* Possibilità di testing del livello appena creato.

* Possibilità di salvare il livello in creazione nell'editor.

* Sistema di punteggio minimale, implementato tramite la presenza di oggetti collezionabili durante il percorso.



Funzionalità opzionali (a completamento del 100% del tempo a disposizione):

* Gestione di multipli livelli.

* Gestione di più stage per un singolo livello.

* Rendere personalizzabili i tasti utilizzati per comandare il player, tramite menu apposito. 

* Arricchimento del menù di oggetti dell'editor di livello.



"Challenge" principali:

* Implementazione di un sistema di transizione da "progetto di livello" a "livello giocabile".

* Reperimento e organizzazione degli oggetti nel menù dell'editor in maniera dinamica.

* Realizzazione di un game engine.

* L'elevata modularità del level editor richiederà un design attento.



Suddivisione di massima del lavoro (con parte significativa del model a ognuno):

* Foschi: gestione della rappresentazione e della logica della griglia di oggetti dell'editor e transizione "progetto di livello"/"livello giocabile".

* Magi: implementazione dello shop e gestione dello stato dell'utente. Creazione della view dell'editor e rappresentazione grafica del game object. 

* Spitaleri: realizzazione del game engine. Realizzazione del menu principale dell'applicazione.

* Ventura: realizzazione dei componenti che costituiscono un game object. Realizzazione della view dello shop.

# Commands
## Editor
>While in the editor

- Click on an object in the sidebar to select the object.

Once the object is selected:
- With the mouse click and drag on the grid area where you want to place the object

> [!WARNING] 
> While dragging you will not see the objects being placed.
> The objects will be placed once the mouse click has been released and if the placement of the object follows it's constraints.

Undo-Button
- Click on the undo button to cancel the last operation

## In-Game Movements

| Movement | Binding |
|----------|---------|
| Up       | w       |
| Left     | a       |
| Down     | s       |
| Right    | d       |
