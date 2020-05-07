# Esperimenti Efficienza Temporale

Sviluppare un programma che permetta di eseguire degli esperimenti
atti a misurare l'efficienza temporale di diversi algoritmi.

Tutte le classi e le interfacce devono trovarsi nel package `it.polito.oop.timefficiency`

## R1 - Definizione esperimenti

La classe principale è `Experiment` che permette di confrontare due o più algoritmi
che possono essere definiti tramite il metodo `addAlgorithm()` che riceve come parametri
una stringa con il nome e un oggetto `Consumer` (dal package `java.util.function`).

Gli algoritmi vengono misurati sulla base di un dataset che può essere
generato tramite un fornitore di dati che viene definito tramite
il metodo `setDataGenerator()` che riceve come parametro un oggetto che implementa
l'interfaccia `Supplier` (dal package `java.util.function`).

I dati generati dal fornitore e poi consumati dagli algoritmi possono
essere di diverso tipo, perciò la classe `Experiment` è definita come generica
dove il parametro di tipo è corrisponde ai dati.

È possibile ottenere la lista dei nomi degli algoritmi tramite il metodo `algorithms()`.

## R2 - Misura

Il metodo `perform()`, che accetta come parametro il nome di un algoritmo
permette di eseguire l'algoritmo sui dati forniti dal generatore e restituisce
il tempo richiesto dall'esecuzione (esclusa la generazione dei dati) come
un double in millisecondi.

## R3 - Esperimento

Lo svolgimento dell'esperimento consiste nell'eseguire ciascun algoritmo
un numero predefinito di volte registrando i tempi di ciascuna esecuzione
di ogni algoritmo.

Il metodo `run()` svolge l'esperimento e restituisce il numero di ripetizioni
svolte per ciascun algoritmo. Tale numero è per default pari a 30 ma può 
essere modificato tramite il metodo `setRepeat()`, e letto tramite `getRepeat()`.

Il metodo `getMeasures()` restituisce le misure raccolte sotto forma di una `Map`
che associa al nome degli algoritmi una lista con i tempi di esecuzione.


## R4 - Diagramma

Per mostrare i valori delle misure è possibile costruire un diagramma
(utlizzando soltanto dei caratteri) che riporta il valore minimo (`<`), quello
massimo (`>`) e la media (`|`), per ciascuno degli algoritmi.

La larghezza massima del diagramma è definita tramite il metodo `setPlotFormat()`
che riceve due interi: il primo è il numero di caratteri massimo con cui
riportare il nome degli algoritmi, e il secondo è il numero di caratteri massimo
su cui rappresentare gli intervalli.

Un esempio di diagramma è il seguente:

```
   Primo : <----|--->
 Secondo :         <----------|--------->
```

I nomi degli algoritmi sono riportati su 8 caratteri seguiti da ` : `
e dal diagramma su 30 caratteri.

Il primo carattere corrisponde al valore minimo tra tutte le misure,
mentre l'ultimo carattere corrisponde al valore massimo.

Il diagramma viene generato tramite il metodo `plotInterval()` che
restituisce una stringa contenente il diagramma stesso. 

Suggerimento:

* il metodo statico `String.format(fmt,args...)` permette di formattare 
	opportunamente le stringhe, ad esempio `"%8.10s"` permette si stampare
	una stringa su almeno 8 caratteri e al più 10.


