package pojazdy;

import czas.Moment;
import linie.Linia;
import pasażerowie.Pasażer;
import pojemnik.KolejkaPasażerów;
import pojemnik.KolejkaPasażerówCykliczna;
import pojemnik.KolejkaZdarzeń;
import przystanki.Przystanek;
import zdarzenia.Postój;

public abstract class Pojazd {

    // dane

    private int numerBoczny;
    private Linia linia;
    private KolejkaPasażerów jadący;
    private Przystanek pętlaDomowa;

    // techniczne

    public Pojazd(int numerBoczny, Linia linia, Przystanek pętlaDomowa, int pojemność) {
        this.numerBoczny = numerBoczny;
        this.linia = linia;
        //this.kierunek = kierunek;
        this.pętlaDomowa = pętlaDomowa;
        this.jadący = new KolejkaPasażerówCykliczna(pojemność);
    }


    // operacje

    // Chcemy wymusić na podklasach sformatowanie własnego komunikatu
    public abstract String toString();

    // Chemy, aby pojazdy potrafiły wypisywać się w alternatywny sposób, odpowiedni dla zdarzenia typu Postój
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

    public void dodajPasażeraPojazd(Pasażer pasażer) {
        if (!this.czyZapełniony()) {
            this.jadący.wstaw(pasażer);
        }
    }

    // Opróżnia pojazd z pasażerów
    public void opróżnij() {
        this.jadący.opróżnij();
    }

    public boolean czyZapełniony() {
        return this.jadący.czyPełna();
    }

    public boolean czyPusty() {
        return this.jadący.czyPusta();
    }

    // Obsługuje wizytę (postój) na przystanku, tzn. dojazd, wysiadanie, wsiadanie i odjazd z przystanku
    public abstract void stańNaPrzystanku(Postój postój, KolejkaZdarzeń q);

    // Losuje przystanek pomiędzy tym, na którym aktualnie się znajduje, a końcem swojej obecnej trasy
    public abstract Przystanek nowyCel(int indeksPrzystanku);

    // Obsługuje wysiadanie pasażerów na danym przystanku w danym momencie
    public void wysiadanie(Przystanek przystanek, Moment moment) {
        // Pasażerowie wysiadają, póki pojazd nie jest pusty i póki na przystanku jest miejsce
        int chętny = this.jadący.znajdźChętnegoDoWysiadki(przystanek);
        while (chętny != -1 && !przystanek.czyZapełniony()) {
            Pasażer pasażer = this.jadący.usuńZeŚrodka(chętny);
            pasażer.wejdźNaPrzystanek(this, przystanek, moment);
            chętny = this.jadący.znajdźChętnegoDoWysiadki(przystanek);
        }
    }

    // "Magicznie" powoduje powrót pojazdu na jego pętlę domową i przygotowanie do kursowania następnego dnia
    public abstract void wróćNaPętlęDomową();

    // Zwraca fałsz tylko, gdy pojazd odjeżdża z pętli
    protected abstract boolean czyDojechał(Postój postój);

    // Zwraca fałsz tylko, gdy pojazd dojechał na pętlę
    protected abstract boolean czyOdjeżdża(Postój postój);

    // Zwraca fałsz tylko, gdy pojazd znajduje się na swojej pętli domowej, a odjazd z niej musiałby nastąpić po godzinie końca odjazdów
    protected abstract boolean czyWyjeżdża(Postój postój);

    // Zwraca następny przystanek na trasie pojazdu w kontekście postoju na danym przystanku
    protected abstract int następnyPrzystanek(Postój postój);

    protected boolean czyPętla(int indeksPrzystanku) {
        return indeksPrzystanku == 0 || indeksPrzystanku == this.linia.długośćLinii() - 1;
    }

    // Oblicza odstęp między tym, a następnym postojem
    protected abstract int odstęp(Postój postój);

    // Wstawia następny postój z obliczonymi parametrami na kolejkę zdarzeń
    protected abstract void wstawNastępnyPostój(Postój postój, KolejkaZdarzeń q);

}
