package zdarzenia;

import czas.Moment;
import pojemnik.KolejkaZdarzeń;

// Event class
public abstract class Zdarzenie {

    // data

    private Moment moment;

    // technicalities

    public Zdarzenie(Moment moment) {
        this.moment = moment;
    }

    // operations

    public Moment moment() {
        return this.moment;
    }

    // Handles the event: updates appropriate variables, logs the event etc.
    public abstract void obsłuż(KolejkaZdarzeń q);
}
