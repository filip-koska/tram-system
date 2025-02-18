package pojazdy;

import czas.Moment;
import linie.Linia;
import pasażerowie.Pasażer;
import pojemnik.KolejkaPasażerów;
import pojemnik.KolejkaPasażerówCykliczna;
import pojemnik.KolejkaZdarzeń;
import przystanki.Przystanek;
import zdarzenia.Postój;

// Abstract class representing a vehicle
public abstract class Pojazd {

    // data

    // unique ID number
    private int numerBoczny;
    // line served by the vehicle
    private Linia linia;
    // queue of passengers currently riding the vehicle
    private KolejkaPasażerów jadący;
    // home terminal
    private Przystanek pętlaDomowa;

    // technicalities

    public Pojazd(int numerBoczny, Linia linia, Przystanek pętlaDomowa, int pojemność) {
        this.numerBoczny = numerBoczny;
        this.linia = linia;
        this.pętlaDomowa = pętlaDomowa;
        this.jadący = new KolejkaPasażerówCykliczna(pojemność);
    }


    // operations

    // Force subclasses to implement a tailored print operation
    public abstract String toString();

    // Force subclasses to provide an alternative print operation for a Stop event
    public abstract String wypiszDoPostoju();

    public int numerBoczny() {
        return this.numerBoczny;
    }

    public Przystanek pętlaDomowa() {
        return this.pętlaDomowa;
    }

    public Linia linia() {
        return this.linia;
    }

    // Adds a passenger to the vehicle
    public void dodajPasażeraPojazd(Pasażer pasażer) {
        if (!this.czyZapełniony()) {
            this.jadący.wstaw(pasażer);
        }
    }

    // Empties the vehicle
    public void opróżnij() {
        this.jadący.opróżnij();
    }

    // Tells if the vehicle has reached its maximum capacity
    public boolean czyZapełniony() {
        return this.jadący.czyPełna();
    }

    // Tells if the vehicle is empty
    public boolean czyPusty() {
        return this.jadący.czyPusta();
    }

    // Force subclasses to handle a Stop event, i.e. arrival, unloading of passengers, loading of passengers and departure
    public abstract void stańNaPrzystanku(Postój postój, KolejkaZdarzeń q);

    // Returns a random destination for a pending new passenger between its current stop
    // and the terminus it's approaching
    public abstract Przystanek nowyCel(int indeksPrzystanku);

    // Handles the unloading of passengers at a given stop at a given moment in time
    public void wysiadanie(Przystanek przystanek, Moment moment) {
        // The passengers get off the vehicle until the vehicle is empty or the stop is full
        int chętny = this.jadący.znajdźChętnegoDoWysiadki(przystanek);
        while (chętny != -1 && !przystanek.czyZapełniony()) {
            Pasażer pasażer = this.jadący.usuńZeŚrodka(chętny);
            pasażer.wejdźNaPrzystanek(this, przystanek, moment);
            chętny = this.jadący.znajdźChętnegoDoWysiadki(przystanek);
        }
    }

    // Makes the vehicle return to its home terminal and prepare for next day service
    public abstract void wróćNaPętlęDomową();

    // Tells if the Stop event will cause unloading of passengers
    protected abstract boolean czyDojechał(Postój postój);

    // Tells if the stop will cause loading of passengers
    protected abstract boolean czyOdjeżdża(Postój postój);

    // Tells if the vehicle will be departing from the terminus later the same day
    // Only false iff the vehicle reached its home terminus in the given Stop event
    // and the next departure would happen after the end of service
    protected abstract boolean czyWyjeżdża(Postój postój);

    // Returns the index of the next stop on the vehicle's route based on the current Stop event
    protected abstract int następnyPrzystanek(Postój postój);

    // Tells if the stop with given ID is a terminal
    protected boolean czyPętla(int indeksPrzystanku) {
        return indeksPrzystanku == 0 || indeksPrzystanku == this.linia.długośćLinii() - 1;
    }

    // Computes the interval between given and next Stop event
    protected abstract int odstęp(Postój postój);

    // Enqueues the next Stop event onto the event queue
    protected abstract void wstawNastępnyPostój(Postój postój, KolejkaZdarzeń q);

}
