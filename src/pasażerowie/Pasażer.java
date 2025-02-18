package pasażerowie;

import czas.Moment;
import pojazdy.Pojazd;
import przystanki.Przystanek;
import pojemnik.KolejkaZdarzeń;
import symulacja.DaneSymulacji;
import zdarzenia.DojścieRano;

public class Pasażer {

    // data

    // unique ID number
    private int numer;
    // home stop
    private Przystanek przystanekDomowy;
    // current destination stop
    private Przystanek cel;
    // total daily waiting time
    private int czasOczekiwania;
    // total daily number of rides
    private int liczbaPrzejazdów;
    // timestamp of the previous event
    private Moment poprzedniMoment;

    // technicalities

    public Pasażer(int numer, DaneSymulacji daneSymulacji) {
        this.numer = numer;
        this.przystanekDomowy = daneSymulacji.losujPrzystanek();
        this.cel = null;
        this.czasOczekiwania = this.liczbaPrzejazdów = 0;
    }

    // operations

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

    // Reads the passenger's waiting time and resets it to prepare the passenger for the next day
    public int odczytajCzasOczekiwania() {
        int wynik = this.czasOczekiwania;
        this.czasOczekiwania = 0;
        return wynik;
    }

    // Reads the passenger's total number of rides and resets it to prepare the passenger for the next day
    public int odczytajLiczbęPrzejazdów() {
        int wynik = this.liczbaPrzejazdów;
        this.liczbaPrzejazdów = 0;
        return wynik;
    }

    // Compares two passengers for equality based on their ID number
    public boolean równy(Pasażer p) {
        return this.numer == p.numer;
    }

    // Handles the daily initial appearance of the passenger at their home stop
    public void przyjdźRano(KolejkaZdarzeń q, Moment moment) {
        this.poprzedniMoment = moment;
        q.wstaw(new DojścieRano(moment, this));
    }

    // Registers that the passenger got on a vehicle and updates passenger statistics
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

    // Registers that the passenger got off a vehicle and updates passenger statistics
    public void wejdźNaPrzystanek(Pojazd pojazd, Przystanek przystanek, Moment moment) {
        przystanek.dodajPasażeraPrzystanek(this);
        this.poprzedniMoment = moment;
        System.out.println(moment + "" + this + " wysiadł z " + pojazd.wypiszDoPostoju()
                                        + " na przystanku " + przystanek);
    }

}
