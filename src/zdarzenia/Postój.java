package zdarzenia;

import czas.Moment;
import pojazdy.Pojazd;
import pojemnik.KolejkaZdarzeń;

// Vehicle stop event
public class Postój extends Zdarzenie {

    // data

    // vehicle
    private Pojazd pojazd;
    // stop index
    private int indeksPrzystanku;

    // technicalities
    
    public Postój(Moment moment, int indeksPrzystanku, Pojazd pojazd) {
        super(moment);
        this.pojazd = pojazd;
        this.indeksPrzystanku = indeksPrzystanku;
    }


    @Override
    public void obsłuż(KolejkaZdarzeń q) {
        this.pojazd.stańNaPrzystanku(this, q);
    }

    public Pojazd pojazd() {
        return this.pojazd;
    }

    public int indeksPrzystanku() {
        return this.indeksPrzystanku;
    }
}
