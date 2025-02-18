package zdarzenia;

import czas.Moment;
import pojemnik.KolejkaZdarzeń;

// Klasa przechowująca szeroko pojęte zdarzenie
public abstract class Zdarzenie {

    // dane

    private Moment moment;

    // techniczne

    public Zdarzenie(Moment moment) {
        this.moment = moment;
    }

    // operacje

    public Moment moment() {
        return this.moment;
    }

    // Odpowiada za szeroko pojętą obsługę zdarzenia
    public abstract void obsłuż(KolejkaZdarzeń q);
}
