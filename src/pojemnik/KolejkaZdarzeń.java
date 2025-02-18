package pojemnik;

import zdarzenia.Zdarzenie;

// Interface for an event priority queue
public interface KolejkaZdarzeń {

    // enqueue
    void wstaw(Zdarzenie z);

    // dequeue
    Zdarzenie zdejmij();

    // isEmpty
    boolean czyPusta();

}
