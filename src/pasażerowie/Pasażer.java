package pasażerowie;

import czas.Moment;
import pojazdy.Pojazd;
import przystanki.Przystanek;
import pojemnik.KolejkaZdarzeń;
import symulacja.DaneSymulacji;
import zdarzenia.DojścieRano;

public class Pasażer {

    // dane

    private int numer;
    private Przystanek przystanekDomowy;
    private Przystanek cel;
    private int czasOczekiwania; //
    private int liczbaPrzejazdów;

    private Moment poprzedniMoment;

    // techniczne

    public Pasażer(int numer, DaneSymulacji daneSymulacji) {
        this.numer = numer;
        this.przystanekDomowy = daneSymulacji.losujPrzystanek();
        this.cel = null;
        this.czasOczekiwania = this.liczbaPrzejazdów = 0;
    }

    // operacje

    @Override
    public String toString() {
        return "Pasażer " + this.numer;
    }

    private int numer() {
        return this.numer;
    }

    public Przystanek przystanekDomowy() {
        return this.przystanekDomowy;
    }

    public Przystanek cel() {
        return this.cel;
    }

    public Moment poprzedniMoment() {
        return this.poprzedniMoment;
    }

    private void ustawNowyCel(Przystanek przystanek) {
        this.cel = przystanek;
    }

    // Odczytuje czas oczekiwania pasażera, a następnie resetuje go celem przygotowania pasażera do następnego dnia
    public int odczytajCzasOczekiwania() {
        int wynik = this.czasOczekiwania;
        this.czasOczekiwania = 0;
        return wynik;
    }

    // Odczytuje liczbę przejazdów pasażera, a następnie resetuje ją celem przygotowania pasażera do następnego dnia
    public int odczytajLiczbęPrzejazdów() {
        int wynik = this.liczbaPrzejazdów;
        this.liczbaPrzejazdów = 0;
        return wynik;
    }

    // Porównanie pasażerów identyfikowanych przez numer
    public boolean równy(Pasażer p) {
        return this.numer == p.numer;
    }

    // Obsługa przyjścia rano na przystanek domowy
    public void przyjdźRano(KolejkaZdarzeń q, Moment moment) {
        this.poprzedniMoment = moment;
        q.wstaw(new DojścieRano(moment, this));
    }

    // Wsiadanie pasażera do pojazdu z przystanku, a tym samym marker zwiększający dzienną liczbę przejazdów
    public void wsiądźDoPojazdu(Pojazd pojazd, int indeksPrzystanku, Moment moment) {
        pojazd.dodajPasażeraPojazd(this);
        this.czasOczekiwania += moment.czas() - this.poprzedniMoment.czas();
        this.poprzedniMoment = moment;
        ++this.liczbaPrzejazdów;
        this.ustawNowyCel(pojazd.nowyCel(indeksPrzystanku));
        System.out.println(moment + "" + this + " wsiadł do " + pojazd.wypiszDoPostoju() +
                            " na przystanku " + pojazd.linia().przystanek(indeksPrzystanku) +
                            " z zamiarem dojechania na przystanek " + this.cel);
    }

    // Wysiadanie pasażera z pojazdu na przystanek
    public void wejdźNaPrzystanek(Pojazd pojazd, Przystanek przystanek, Moment moment) {
        przystanek.dodajPasażeraPrzystanek(this);
        this.poprzedniMoment = moment;
        System.out.println(moment + "" + this + " wysiadł z " + pojazd.wypiszDoPostoju()
                                        + " na przystanku " + przystanek);
    }

}
