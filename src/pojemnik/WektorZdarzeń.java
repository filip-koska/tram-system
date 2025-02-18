package pojemnik;

import zdarzenia.Zdarzenie;

// Wektor zdarzeń to tablica dynamiczna ze stale utrzymywanym malejącym porządkiem czasów
public class WektorZdarzeń implements Wektor {
    // dane
    private Zdarzenie[] tab;
    private int rozmiar;

    // techniczne
    public WektorZdarzeń() {
        this.tab = new Zdarzenie[0];
        this.rozmiar = 0;
    }

    // operacje

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

    // Podwaja pojemność wektora w przypadku osiągnięcia aktualnej pojemności;
    // Działa w zamortyzowanym czasie stałym
    private void rozszerz() {
        Zdarzenie[] wynik = new Zdarzenie[this.pojemność() * 2 + (this.pojemność() == 0 ? 1 : 0)];
        System.arraycopy(this.tab, 0, wynik, 0, this.pojemność());
        this.tab = wynik;
    }

    // Metoda pomocnicza, zamienia elementy o indeksach i oraz j miejscami
    private void zamień(int i, int j) {
        Zdarzenie pom = this.tab[i];
        this.tab[i] = this.tab[j];
        this.tab[j] = pom;
    }

    // Wstawia zdarzenie w odpowiednie miejsce na wektorze
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

    // Zdejmuje ostatni (najwcześniejszy) element z wektora
    public Zdarzenie zdejmij() {
        assert !this.czyPusty(): "Próba zdjęcia zdarzenia z pustej kolejki";
        return this.tab[--this.rozmiar];
    }
}
