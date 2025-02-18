package zdarzenia;

import czas.Moment;
import pojazdy.Pojazd;
import pojemnik.KolejkaZdarzeń;

public class Postój extends Zdarzenie {

    private Pojazd pojazd;
    private int indeksPrzystanku;
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
