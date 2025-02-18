package linie;

import czas.Moment;
import pojazdy.Pojazd;
import pojazdy.Tramwaj;
import pojemnik.KolejkaZdarzeń;
import przystanki.Przystanek;
import symulacja.DaneSymulacji;
import czytnik.Czytnik;
import zdarzenia.Postój;

public class Linia {
    // dane

    private int numer;
    private Przystanek[] przystanki;
    private int[] czasy;

    // Celowa decyzja projektowa: być może będziemy chcieli w przyszłości rozwinąć symulację o linie hybrydowe, o różnych typach pojazdów
    private Pojazd[] pojazdy;
    private int odstęp;

    // techniczne

    public Linia(int numer, DaneSymulacji daneSymulacji) {
        this.numer = numer;
        int liczbaTramwajówLinii = Czytnik.odczyt().nextInt();
        int długośćLinii = Czytnik.odczyt().nextInt();
        this.pojazdy = new Tramwaj[liczbaTramwajówLinii];
        this.przystanki = new Przystanek[długośćLinii];
        this.czasy = new int[długośćLinii];
        int sumaCzasów = 0;
        for (int i = 0; i < długośćLinii; ++i) {
            String nazwaPrzystanku = Czytnik.odczyt().next();
            this.przystanki[i] = daneSymulacji.znajdźPrzystanek(nazwaPrzystanku);
            this.czasy[i] = Czytnik.odczyt().nextInt();
            sumaCzasów += this.czasy[i];
        }
        this.odstęp = this.obliczOdstęp(sumaCzasów);
        int zmianaStrony = this.pojazdy.length / 2;
        if (this.pojazdy.length % 2 == 1) {
            ++zmianaStrony;
        }
        for (int i = 0; i < this.pojazdy.length; ++i) {
            int kierunek;
            Przystanek pętlaDomowa;
            if (i < zmianaStrony) {
                kierunek = 1;
                pętlaDomowa = this.przystanki[0];
            }
            else {
                kierunek = -1;
                pętlaDomowa = this.przystanki[this.przystanki.length - 1];
            }
            this.pojazdy[i] = new Tramwaj(daneSymulacji.nowyNumerTramwaju(), this,
                                            pętlaDomowa, daneSymulacji.pojemnośćTramwaju(), kierunek);
        }
    }

    // operacje

    @Override
    public String toString() {
        String wynik = "\nLinia nr " + this.numer + ", liczba tramwajów: " + this.pojazdy.length + "\n";
        wynik += "Przystanki: \n";
        for (int i = 0; i < this.przystanki.length; ++i) {
            wynik += this.przystanki[i] + " ";
        }
        wynik += "\nCzasy przejazdów: \n";
        for (int i = 0; i < this.czasy.length - 1; ++i) {
            wynik += this.przystanki[i] + "<->" + this.przystanki[i + 1] + ": " + this.czasy[i] + "\n";
        }
        wynik += "Czas postoju na pętli: " + this.czasPostojuNaPętli();
        return wynik + "\n";
    }

    public int numer() {
        return this.numer;
    }
    public int czasPostojuNaPętli() {
        return this.czasy[this.czasy.length - 1];
    }

    public int długośćLinii() {
        return this.przystanki.length;
    }

    // Pobiera przystanek linii o danym indeksie
    public Przystanek przystanek(int i) {
        assert i >= 0 && i < this.przystanki.length: "Niepoprawny indeks";
        return this.przystanki[i];
    }

    // Pobiera czas o indeksie i z tablicy czasów przejazdu
    public int czas(int i) {
        assert i >= 0 && i < this.długośćLinii(): "Niepoprawny indeks";
        return this.czasy[i];
    }

    // Oblicza odstęp między porannymi odjazdami tramwajów z tej samej pętli
    private int obliczOdstęp(int sumaCzasów) {
        int wynik;
        // jeśli linia ma 0 pojazdów
        if (this.pojazdy.length == 0) {
            wynik = 1;
        }
        else wynik = Math.max((2 * sumaCzasów) / this.pojazdy.length, 1);
        return wynik;
    }

    // Wypuszcza rano pojazdy na pętle
    public void wypuśćPojazdy(KolejkaZdarzeń q, int dzień) {
        int zmianaStrony = this.pojazdy.length / 2;
        if (this.pojazdy.length % 2 == 1) {
            ++zmianaStrony;
        }
        for (int i = 0; i < this.pojazdy.length; ++i) {
            this.pojazdy[i].wróćNaPętlęDomową();
            int indeksPętliDomowej = this.pojazdy[i].pętlaDomowa().równy(this.przystanki[0]) ? 0 : this.przystanki.length - 1;
            q.wstaw(new Postój(new Moment(6 * 60 + this.odstęp * (i % zmianaStrony), dzień),
                    indeksPętliDomowej, this.pojazdy[i]));
        }
    }

    // Opróżnia wszystkie pojazdy linii z jadących pasażerów
    public void opróżnijPojazdy() {
        for (int i = 0; i < this.pojazdy.length; ++i) {
            this.pojazdy[i].opróżnij();
        }
    }

}
