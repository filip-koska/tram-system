package pojemnik;

import zdarzenia.Zdarzenie;

// Dynamic array-based implementation of priority queue
public class KolejkaZdarzeńWektor implements KolejkaZdarzeń {

    // data

    // internal dynamic array
    private WektorZdarzeń w;

    // technicalities

    public KolejkaZdarzeńWektor() {
        this.w = new WektorZdarzeń();
    }

    // operations

    @Override
    public void wstaw(Zdarzenie z) {
        this.w.wstaw(z);
    }

    @Override
    public Zdarzenie zdejmij() {
        return this.w.zdejmij();
    }

    @Override
    public boolean czyPusta() {
        return this.w.czyPusty();
    }
}
