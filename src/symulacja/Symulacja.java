package symulacja;

import czas.Moment;
import main.Losowanie;
import pojemnik.KolejkaZdarzeń;
import pojemnik.KolejkaZdarzeńWektor;
import zdarzenia.Zdarzenie;

public class Symulacja {

    // dane

    private DaneSymulacji daneSymulacji;

    // techniczne

    public Symulacja() {
        this.daneSymulacji = new DaneSymulacji();
    }

    // operacje

    @Override
    public String toString() {
        return this.daneSymulacji + "";
    }

    // Przeprowadza symulację
    public void symuluj() {
        KolejkaZdarzeń q = new KolejkaZdarzeńWektor();
        for (int i = 0; i < this.daneSymulacji.liczbaDni(); ++i) {
            this.wypuśćPasażerów(q, i);
            for (int j = 0; j < this.daneSymulacji.liczbaLinii(); ++j) {
                this.daneSymulacji.linia(j).wypuśćPojazdy(q, i);
            }
            Moment ostatniMomentDnia = new Moment(DaneSymulacji.pierwszaGodzinaPrzychodzenia(), i);
            while (!q.czyPusta()) {
                Zdarzenie z = q.zdejmij();
                z.obsłuż(q);
                ostatniMomentDnia = z.moment();
            }
            this.koniecDnia(ostatniMomentDnia);
        };
        System.out.println("\nDane dla całej symulacji:");
        System.out.println("Średni czas oczekiwania: " + this.obliczŚredniCzasOczekiwania());
        System.out.println("Całkowita liczba przejazdów: " + this.daneSymulacji.całkowitaLiczbaPrzejazdów());
    }

    // Wypuszcza rano pasażerów na ich przystanki domowe
    private void wypuśćPasażerów(KolejkaZdarzeń q, int dzień) {
        for (int i = 0; i < this.daneSymulacji.liczbaPasażerów(); ++i) {
            Moment nowyMoment = new Moment(Losowanie.losuj(DaneSymulacji.pierwszaGodzinaPrzychodzenia(),
                                                    DaneSymulacji.ostatniaGodzinaPrzychodzenia()), dzień);
            this.daneSymulacji.pasażer(i).przyjdźRano(q, nowyMoment);
        }
    }

    // Obsługuje koniec dnia: opróżnia wszystkie przystanki i pojazdy, aktualizuje czasy oczekiwania i liczbę przejazdów
    // oraz wypisuje statystyki dzienne
    private void koniecDnia(Moment ostatniMomentDnia) {
        int dziennaLiczbaPrzejazdów = 0;
        int dziennyCzasOczekiwania = 0;
        for (int i = 0; i < this.daneSymulacji.liczbaPrzystanków(); ++i) {
            dziennyCzasOczekiwania += this.daneSymulacji.przystanek(i).opróżnij(ostatniMomentDnia);
        }
        for (int i = 0; i < this.daneSymulacji.liczbaLinii(); ++i) {
            this.daneSymulacji.linia(i).opróżnijPojazdy();
        }
        for (int i = 0; i < this.daneSymulacji.liczbaPasażerów(); ++i) {
            dziennaLiczbaPrzejazdów += this.daneSymulacji.pasażer(i).odczytajLiczbęPrzejazdów();
            dziennyCzasOczekiwania += this.daneSymulacji.pasażer(i).odczytajCzasOczekiwania();
        }
        System.out.println("Koniec dnia " + ostatniMomentDnia.dzień());
        System.out.println("Dzienny czas oczekiwania: " + dziennyCzasOczekiwania / 60 + " godzin "
                            + (dziennyCzasOczekiwania - (dziennyCzasOczekiwania / 60) * 60) + " minut");
        System.out.println("Dzienna liczba przejazdów: " + dziennaLiczbaPrzejazdów + "\n");
        this.daneSymulacji.dodajCzasOczekiwania(dziennyCzasOczekiwania);
        this.daneSymulacji.dodajPrzejazdy(dziennaLiczbaPrzejazdów);
    }

    // Oblicza średni czas oczekiwania pasażerów
    private String obliczŚredniCzasOczekiwania() {
        if (this.daneSymulacji.całkowitaLiczbaPrzejazdów() == 0) {
            return "0 minut 0 sekund";
        }
        double wynik = this.daneSymulacji.całkowityCzasOczekiwania();
        wynik /= daneSymulacji.całkowitaLiczbaPrzejazdów();
        long minuty = Math.round(Math.floor(wynik));
        long sekundy = Math.round(Math.floor((wynik - Math.floor(wynik)) * 60));

        return minuty + " minut " + sekundy + " sekund";//return Math.floor(wynik) + " minut " + Math.floor((wynik - Math.floor(wynik)) * 60)+ " sekund";
    }


}
