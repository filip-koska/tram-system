package pojemnik;

import czas.Moment;
import przystanki.Przystanek;
import pasażerowie.Pasażer;

// Implementacja kolejki pasażerów z wykorzystaniem tablicy cyklicznej
// Korzystamy z tego, że rozmiar kolejki pasażerów jest ograniczony przez min(pojemność tramwaju, pojemność przystanku)
public class KolejkaPasażerówCykliczna implements KolejkaPasażerów {

    private Pasażer[] tab;
    // konwencja: oznaczeniem pustej kolejki jest początek równy -1
    private int początek;
    private int koniec;

    public KolejkaPasażerówCykliczna(int pojemność) {
        this.tab = new Pasażer[pojemność];
        this.początek = this.koniec = -1;
    }

    // Wstawia nowego pasażera na koniec kolejki
    @Override
    public void wstaw(Pasażer p) {
        assert !this.czyPełna(): "Próba wstawienia pasażera do pełnej kolejki";
        if (this.czyPusta()) {
            this.początek = this.koniec = 0;
            this.tab[0] = p;
        }
        else {
            this.koniec = (this.koniec + 1) % this.pojemność();
            this.tab[this.koniec] = p;
        }
    }

    @Override
    public Pasażer zdejmij() {
        assert !this.czyPusta(): "Próba zdjęcia pasażera z pustej kolejki";
        Pasażer wynik = this.tab[this.początek];
        if (this.rozmiar() == 1) {
            this.początek = this.koniec = -1;
        }
        else {
            this.początek = (this.początek + 1) % this.pojemność();
        }
        return wynik;
    }

    // Metoda pomocnicza, zamieniająca dwa elementy tablicy Pasażerów miejscami
    private void zamień(int i, int j) {
        assert i >= 0 && i < this.pojemność() && j >= 0 && j < this.pojemność() : "Niepoprawny indeks";
        Pasażer pom = this.tab[i];
        this.tab[i] = this.tab[j];
        this.tab[j] = pom;
    }

    @Override
    public Pasażer usuńZeŚrodka(int i) {
        assert !this.czyPusta(): "Próba usunięcia pasażera z pustej kolejki";
        assert i >= 0 && i < this.pojemność(): "Niepoprawny indeks";
        Pasażer wynik = this.tab[i];
        int odległość;
        if (this.początek == this.koniec) {
            this.początek = this.koniec = -1;
            return wynik;
        }
        else if (this.koniec >= i) {
            odległość = this.koniec - i;
        }
        else {
            odległość = this.koniec + 1 + this.pojemność() - i;
        }
        for (int j = 0; j < odległość; ++j) {
            this.zamień((i + j) % this.pojemność(), (i + j + 1) % this.pojemność());
        }
        this.koniec = (this.koniec + this.pojemność() - 1) % this.pojemność();
        return wynik;
    }

    @Override
    public boolean czyPusta() {
        return this.początek == -1;
    }

    @Override
    public boolean czyPełna() {
        return (this.początek == 0 && this.koniec == this.pojemność() - 1) || this.początek - this.koniec == 1;
    }

    public int pojemność() {
        return this.tab.length;
    }

    public int rozmiar() {
        if (this.czyPusta()) {
            return 0;
        }
        if (this.początek <= this.koniec) {
            return this.koniec - this.początek + 1;
        }
        return this.pojemność() - this.początek + this.koniec + 1;
    }

    // Zwraca indeks pierwszego pasażera chcącego wysiąść na danym przystanku, lub -1, jeśli takiego nie ma
    @Override
    public int znajdźChętnegoDoWysiadki(Przystanek przystanek) {
        if (this.czyPusta()) {
            return -1;
        }
        int i = 0;
        while (i < this.rozmiar() && !this.tab[(this.początek + i) % this.pojemność()].cel().równy(przystanek)) {
            ++i;
        }
        if (i == this.rozmiar()) {
            return -1;
        }
        return (this.początek + i) % this.pojemność();
    }

    @Override
    public void opróżnij() {
        this.początek = this.koniec = -1;
    }

    @Override
    public int dodajCzasyOczekiwania(Moment ostatniMomentDnia) {
        int wynik = 0;
        for (int i = 0; i < this.rozmiar(); ++i) {
            wynik += ostatniMomentDnia.czas() - this.tab[(this.początek + i) % this.pojemność()].poprzedniMoment().czas();
        }
        return wynik;
    }
}
