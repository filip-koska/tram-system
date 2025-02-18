package pojazdy;

import czas.Moment;
import linie.Linia;
import main.Losowanie;
import pojemnik.KolejkaZdarzeń;
import przystanki.Przystanek;
import symulacja.DaneSymulacji;
import zdarzenia.Postój;

public class Tramwaj extends Pojazd {
    // dane

    private int kierunek;

    // techniczne

    public Tramwaj(int numerBoczny, Linia linia, Przystanek pętlaDomowa, int pojemność, int kierunek) {
        super(numerBoczny, linia, pętlaDomowa, pojemność);
        this.kierunek = kierunek;
    }

    // operacje

    @Override
    public String toString() {
        return "Tramwaj linii nr " + this.linia().numer() + " (nr boczny " + this.numerBoczny() + ")";
    }

    @Override
    public String wypiszDoPostoju() {
        return "tramwaju linii nr " + this.linia().numer() + " (nr boczny " + this.numerBoczny() + ")";
    }

    @Override
    public void stańNaPrzystanku(Postój postój, KolejkaZdarzeń q) {
        if (this.czyDojechał(postój)) {
            System.out.println(postój.moment() + "" + this
                    + " dojechał na przystanek " + this.linia().przystanek(postój.indeksPrzystanku()));
            this.wysiadanie(this.linia().przystanek(postój.indeksPrzystanku()), postój.moment());
        }
        if (this.czyOdjeżdża(postój)) {
            this.linia().przystanek(postój.indeksPrzystanku()).wsiadanie(this, postój.indeksPrzystanku(), postój.moment());
            System.out.println(postój.moment() + "" + this
                    + " odjechał z przystanku " + this.linia().przystanek(postój.indeksPrzystanku()));
        }
        this.wstawNastępnyPostój(postój, q);
    }

    @Override
    public Przystanek nowyCel(int indeksPrzystanku) {
        int indeksCelu;
        if (this.kierunek == 1) {
            indeksCelu = Losowanie.losuj(indeksPrzystanku + 1, this.linia().długośćLinii() - 1);
        }
        else {
            indeksCelu = Losowanie.losuj(0, indeksPrzystanku - 1);
        }
        assert indeksCelu >= 0 && indeksCelu < this.linia().długośćLinii(): "Niepoprawny cel pasażera";
        return  this.linia().przystanek(indeksCelu);
    }

    @Override
    public void wróćNaPętlęDomową() {
        if (this.linia().przystanek(0).równy(this.pętlaDomowa())) {
            this.kierunek = 1;
        }
        else {
            this.kierunek = -1;
        }
    }

    @Override
    protected boolean czyDojechał(Postój postój) {
        return !((postój.indeksPrzystanku() == 0 && this.kierunek == 1)
                    || (postój.indeksPrzystanku() == this.linia().długośćLinii() - 1 && this.kierunek == -1));
    }

    @Override
    protected boolean czyOdjeżdża(Postój postój) {
        return !((postój.indeksPrzystanku() == 0 && this.kierunek == -1)
                    || (postój.indeksPrzystanku() == this.linia().długośćLinii() - 1 && this.kierunek == 1));
    }

    @Override
    protected boolean czyWyjeżdża(Postój postój) {
        if (!this.linia().przystanek(postój.indeksPrzystanku()).równy(this.pętlaDomowa())) {
            return true;
        }
        if (!this.czyPętla(postój.indeksPrzystanku())) {
            return true;
        }
        if (this.czyDojechał(postój)) {
            return postój.moment().czas() + this.linia().czasPostojuNaPętli() <= DaneSymulacji.ostatniaGodzinaKursowania();
        }
        return this.czyOdjeżdża(postój);
    }

    @Override
    protected int następnyPrzystanek(Postój postój) {
        // Jeżeli tramwaj dojechał na pętlę, zmienia kierunek jazdy i czeka na pętli
        if (!czyOdjeżdża(postój)) {
            this.kierunek *= -1;
            return postój.indeksPrzystanku();
        }
        return postój.indeksPrzystanku() + this.kierunek;
    }

    @Override
    protected int odstęp(Postój postój) {
        if (this.czyDojechał(postój) && !this.czyOdjeżdża(postój)) {
            return this.linia().czasPostojuNaPętli();
        }
        if (this.kierunek == 1) {
            return this.linia().czas(postój.indeksPrzystanku());
        }
        return this.linia().czas(postój.indeksPrzystanku() - 1);
    }

    @Override
    protected void wstawNastępnyPostój(Postój postój, KolejkaZdarzeń q) {
        if (!this.czyWyjeżdża(postój)) {
            this.kierunek *= -1;
            return;
        }
        Moment nowyMoment = new Moment(postój.moment().czas() + this.odstęp(postój), postój.moment().dzień());
        q.wstaw(new Postój(nowyMoment, this.następnyPrzystanek(postój), this));
    }
}
