package pojemnik;

import zdarzenia.Zdarzenie;

// A dynamic array of events
// The array stores events in descending order of timestamps
public class WektorZdarzeń implements Wektor {
    // data
    // internal array
    private Zdarzenie[] tab;
    // size
    private int rozmiar;

    // technicalities
    public WektorZdarzeń() {
        this.tab = new Zdarzenie[0];
        this.rozmiar = 0;
    }

    // operations

    @Override
    public boolean czyPusty() {
        return this.rozmiar == 0;
    }

    @Override
    public int rozmiar() {
        return this.rozmiar;
    }

    @Override
    public int pojemność() {
        return this.tab.length;
    }

    // Doubles the array's capacity in case of reaching current capacity
    // Works in amortised constant time
    private void rozszerz() {
        Zdarzenie[] wynik = new Zdarzenie[this.pojemność() * 2 + (this.pojemność() == 0 ? 1 : 0)];
        System.arraycopy(this.tab, 0, wynik, 0, this.pojemność());
        this.tab = wynik;
    }

    // swap
    private void zamień(int i, int j) {
        Zdarzenie pom = this.tab[i];
        this.tab[i] = this.tab[j];
        this.tab[j] = pom;
    }

    // insert
    public void wstaw(Zdarzenie z) {
        if (this.rozmiar == this.pojemność()) {
            this.rozszerz();
        }
        // Znajdujemy odpowiednie miejsce dla zdarzenia (
        int i = 0;
        while (i < this.rozmiar && this.tab[i].moment().czas() > z.moment().czas()) {
            ++i;
        }
        this.tab[this.rozmiar++] = z;
        int j = this.rozmiar - 1;
        while (j > i && j > 0) {
            this.zamień(j, --j);
        }
    }

    // pop
    public Zdarzenie zdejmij() {
        assert !this.czyPusty(): "Próba zdjęcia zdarzenia z pustej kolejki";
        return this.tab[--this.rozmiar];
    }
}
