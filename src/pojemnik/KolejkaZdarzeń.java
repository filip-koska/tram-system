package pojemnik;

import zdarzenia.Zdarzenie;

public interface KolejkaZdarzeń {

    // Wstawia nowe zdarzenie do kolejki priorytetowej
    void wstaw(Zdarzenie z);

    // Zdejmuje zdarzenie z początku kolejki priorytetowej
    Zdarzenie zdejmij();

    boolean czyPusta();

}