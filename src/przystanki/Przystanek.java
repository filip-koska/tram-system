package przystanki;

import czas.Moment;
import czytnik.Czytnik;
import pojazdy.Pojazd;
import pasażerowie.Pasażer;
import pojemnik.KolejkaPasażerów;
import pojemnik.KolejkaPasażerówCykliczna;
import symulacja.DaneSymulacji;

public class Przystanek implements Comparable<Przystanek> {

    // dane

    private String nazwa;

    private KolejkaPasażerów czekający;

    // techniczne

    public Przystanek(DaneSymulacji daneSymulacji) {
        this.nazwa = Czytnik.odczyt().next();
        this.czekający = new KolejkaPasażerówCykliczna(daneSymulacji.pojemnośćPrzystanku());
    }

    // komparator przystanków do sortowania leksykograficznego tablicy przystanków w danych symulacji
    @Override
    public int compareTo(Przystanek przystanek) {
        return this.nazwa.compareTo(przystanek.nazwa);
    }

    // operacje

    @Override
    public String toString() {
        return this.nazwa;
    }

    // Operator porównania przystanków identyfikowanych nazwami
    public boolean równy(Przystanek p) {
        return this.nazwa.equals(p.nazwa());
    }

    public String nazwa() {
        return this.nazwa;
    }

    public void dodajPasażeraPrzystanek(Pasażer pasażer) {
        if (!this.czekający.czyPełna()) {
            this.czekający.wstaw(pasażer);
        }
    }

    // Opróżnia przystanek i zwraca łączny czas oczekiwania pasażerów stojących na tym przystanku do godziny końca kursowania
    public int opróżnij(Moment ostatniMomentDnia) {
        int wynik = this.czekający.dodajCzasyOczekiwania(ostatniMomentDnia);
        this.czekający.opróżnij();
        return wynik;
    }

    public boolean czyPusty() {
        return this.czekający.czyPusta();
    }

    public boolean czyZapełniony() {
        return this.czekający.czyPełna();
    }

    // Obsługuje wsiadanie pasażerów do danego pojazdu w danym momencie
    // Indeks przystanku to numer przystanku w kontekście linii pojazdu, podawany dla usprawnienia czasu działania programu
    public void wsiadanie(Pojazd pojazd, int indeksPrzystanku, Moment moment) {
        // Pasażerowie wsiadają, póki przystanek nie jest pusty i w pojeździe jest miejsce
        while (!pojazd.czyZapełniony() && !this.czyPusty()) {
            Pasażer pasażer = this.czekający.zdejmij();
            pasażer.wsiądźDoPojazdu(pojazd, indeksPrzystanku, moment);
        }
    }


}
