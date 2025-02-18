package przystanki;

import czas.Moment;
import czytnik.Czytnik;
import pojazdy.Pojazd;
import pasażerowie.Pasażer;
import pojemnik.KolejkaPasażerów;
import pojemnik.KolejkaPasażerówCykliczna;
import symulacja.DaneSymulacji;

// A class representing a stop
public class Przystanek implements Comparable<Przystanek> {

    // data

    // unique name
    private String nazwa;

    // passenger queue
    private KolejkaPasażerów czekający;

    // technicalities

    public Przystanek(DaneSymulacji daneSymulacji) {
        this.nazwa = Czytnik.odczyt().next();
        this.czekający = new KolejkaPasażerówCykliczna(daneSymulacji.pojemnośćPrzystanku());
    }

    // Helper comparator function for lexicographical sorting by name
    @Override
    public int compareTo(Przystanek przystanek) {
        return this.nazwa.compareTo(przystanek.nazwa);
    }

    // operations

    @Override
    public String toString() {
        return this.nazwa;
    }

    // Compares stops for equality using their names
    public boolean równy(Przystanek p) {
        return this.nazwa.equals(p.nazwa());
    }

    public String nazwa() {
        return this.nazwa;
    }

    // Adds the passenger to the waiting passengers queue
    public void dodajPasażeraPrzystanek(Pasażer pasażer) {
        if (!this.czekający.czyPełna()) {
            this.czekający.wstaw(pasażer);
        }
    }

    // Empties the stop and returns total waiting time of passengers waiting at this stop until the end of service
    public int opróżnij(Moment ostatniMomentDnia) {
        int wynik = this.czekający.dodajCzasyOczekiwania(ostatniMomentDnia);
        this.czekający.opróżnij();
        return wynik;
    }

    // isEmpty
    public boolean czyPusty() {
        return this.czekający.czyPusta();
    }

    // isFull
    public boolean czyZapełniony() {
        return this.czekający.czyPełna();
    }

    // Handles loading of passengers into given vehicle at given moment
    public void wsiadanie(Pojazd pojazd, int indeksPrzystanku, Moment moment) {
        // Passengers get on the vehicle until the stop is empty or the vehicle is full
        while (!pojazd.czyZapełniony() && !this.czyPusty()) {
            Pasażer pasażer = this.czekający.zdejmij();
            pasażer.wsiądźDoPojazdu(pojazd, indeksPrzystanku, moment);
        }
    }


}
