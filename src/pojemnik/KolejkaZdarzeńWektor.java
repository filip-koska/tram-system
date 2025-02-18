package pojemnik;

import zdarzenia.Zdarzenie;

public class KolejkaZdarzeńWektor implements KolejkaZdarzeń {

    // dane

    private WektorZdarzeń w;

    // techniczne

    public KolejkaZdarzeńWektor() {
        this.w = new WektorZdarzeń();
    }

    // operacje

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
