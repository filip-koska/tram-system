package zdarzenia;

import pasażerowie.Pasażer;
import czas.Moment;
import pojemnik.KolejkaZdarzeń;

public class DojścieRano extends Zdarzenie {

    // dane

    private Pasażer pasażer;

    // techniczne

    public DojścieRano(Moment moment, Pasażer pasażer) {
        super(moment);
        this.pasażer = pasażer;
    }

    // operacje

    @Override
    public void obsłuż(KolejkaZdarzeń q) {
        String komunikat = this.moment() + "" + this.pasażer
                        + " przyszedł rano na swój przystanek domowy " + this.pasażer.przystanekDomowy() + "; ";
        if (this.pasażer.przystanekDomowy().czyZapełniony()) {
            komunikat += "przystanek był przepełniony, więc wrócił do domu";
        }
        else {
            komunikat += " udało mu się wejść na przystanek";
            this.pasażer.przystanekDomowy().dodajPasażeraPrzystanek(this.pasażer);
        }
        System.out.println(komunikat);
    }
}
