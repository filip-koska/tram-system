package symulacja;

import przystanki.Przystanek;
import linie.Linia;
import czytnik.Czytnik;
import pasażerowie.Pasażer;
import main.Losowanie;

import java.util.Arrays;

// Klasa przechowująca dane pojedynczej Symulacji
// Stworzona w celu wygodnego, czytelnego przekazywania "globalnych" danych symulacji do metod innych klas
// oraz jednoczesnego uniknięcia atrybutów statycznych
public class DaneSymulacji {

    // dane

    // Stałe na ważne godziny w kontekście symulacji
    private static final int ostatniaGodzinaKursowania = 23 * 60;
    private static final int pierwszaGodzinaPrzychodzenia = 6 * 60;
    private static final int ostatniaGodzinaPrzychodzenia = 12 * 60;

    private int liczbaDni;
    private int liczbaPasażerów;
    private int liczbaPrzystanków;
    private int pojemnośćPrzystanku;
    private int pojemnośćTramwaju;
    private int liczbaLinii;
    private Linia[] linie;
    private Przystanek[] przystanki;
    private Pasażer[] pasażerowie;
    private int numerTramwaju;
    private int całkowityCzasOczekiwania;
    private int całkowitaLiczbaPrzejazdów;

    public DaneSymulacji() {
        this.liczbaDni = Czytnik.odczyt().nextInt();
        this.numerTramwaju = 0;
        this.pojemnośćPrzystanku = Czytnik.odczyt().nextInt();
        this.liczbaPrzystanków = Czytnik.odczyt().nextInt();
        this.przystanki = new Przystanek[this.liczbaPrzystanków];
        for (int i = 0; i < this.liczbaPrzystanków; ++i) {
            this.przystanki[i] = new Przystanek(this);
        }
        // Sortujemy tablicę przystanków leksykograficznie, aby móc wykonywać na niej wyszukiwanie binarne
        Arrays.sort(this.przystanki);
        this.liczbaPasażerów = Czytnik.odczyt().nextInt();
        this.pasażerowie = new Pasażer[this.liczbaPasażerów];
        for (int i = 0; i < this.liczbaPasażerów; ++i) {
            this.pasażerowie[i] = new Pasażer(i, this);
        }
        this.pojemnośćTramwaju = Czytnik.odczyt().nextInt();
        this.liczbaLinii = Czytnik.odczyt().nextInt();
        this.linie = new Linia[this.liczbaLinii];
        for (int i = 0; i < this.liczbaLinii; ++i) {
            this.linie[i] = new Linia(i, this);
        }
        this.całkowitaLiczbaPrzejazdów = this.całkowityCzasOczekiwania = 0;
    }

    // Wybiera losowy przystanek z tablicy wszystkich przystanków
    public Przystanek losujPrzystanek() {
        int indeksPrzystankuDomowego = Losowanie.losuj(0, this.liczbaPrzystanków - 1);
        return this.przystanki[indeksPrzystankuDomowego];
    }

    // Szuka przystanku po nazwie w tablicy przystanków
    // Tablica jest posortowana leksykograficznie
    public Przystanek znajdźPrzystanek(String nazwa) {
        int lewy = 0, prawy = this.przystanki.length;
        while (prawy - lewy > 1) {
            int środek = (lewy + prawy) / 2;
            int porównanie = this.przystanki[środek].nazwa().compareTo(nazwa);
            if (porównanie == 0) {
                return this.przystanki[środek];
            }
            if (porównanie > 0) {
                prawy = środek;
            } else {
                lewy = środek + 1;
            }
        }
        assert this.przystanki[lewy].nazwa().equals(nazwa): "Błąd - niepoprawna nazwa przystanku";
        return this.przystanki[lewy];
    }

    @Override
    public String toString() {
        String wynik = "";
        wynik += "Liczba dni symulacji: " + this.liczbaDni + "\n";
        wynik += "Pojemność przystanku: " + this.pojemnośćPrzystanku  + "\n";
        wynik += "Liczba przystanków: " + this.liczbaPrzystanków + "\n";
        wynik += "Przystanki: \n";
        for (int i = 0; i < this.liczbaPrzystanków; ++i) {
            wynik += this.przystanki[i] + " ";
        }
        wynik += "\nLiczba pasażerów: " + this.liczbaPasażerów + "\n";
        wynik += "Liczba linii: " + this.liczbaLinii + "\n";
        for (int i = 0; i < this.liczbaLinii; ++i) {
            wynik += this.linie[i] + "\n";
        }
        return wynik;
    }

    public int liczbaDni() {
        return this.liczbaDni;
    }
    public int liczbaPasażerów() {
        return this.liczbaPasażerów;
    }
    public int liczbaPrzystanków() {
        return this.liczbaPrzystanków;
    }
    public int pojemnośćPrzystanku() {
        return this.pojemnośćPrzystanku;
    }
    public int pojemnośćTramwaju() {
        return this.pojemnośćTramwaju;
    }
    public int liczbaLinii() {
        return this.liczbaLinii;
    }

    public static int ostatniaGodzinaKursowania() {
        return ostatniaGodzinaKursowania;
    }

    public static int pierwszaGodzinaPrzychodzenia() {
        return pierwszaGodzinaPrzychodzenia;
    }

    public static int ostatniaGodzinaPrzychodzenia() {
        return ostatniaGodzinaPrzychodzenia;
    }

    public int całkowityCzasOczekiwania() {
        return this.całkowityCzasOczekiwania;
    }

    public int całkowitaLiczbaPrzejazdów() {
        return this.całkowitaLiczbaPrzejazdów;
    }


    public Linia linia(int i) {
        assert i >= 0 && i < this.liczbaLinii: "Niepoprawna linia";
        return this.linie[i];
    }

    public Pasażer pasażer(int i) {
        assert i >= 0 && i < this.liczbaPasażerów: "Niepoprawny pasażer";
        return this.pasażerowie[i];
    }

    public Przystanek przystanek(int i) {
        assert i >= 0 && i < this.liczbaPrzystanków: "Niepoprawny przystanek";
        return this.przystanki[i];
    }

    // Zwraca numer dla nowo skonstruowanego tramwaju, a następnie aktualizuje zwracaną wartość dla przyszłych tramwajów
    public int nowyNumerTramwaju() {
        return this.numerTramwaju++;
    }

    //
    public void dodajPrzejazdy(int liczbaPrzejazdów) {
        assert liczbaPrzejazdów >= 0: "Niepoprawna liczba przejazdów";
        this.całkowitaLiczbaPrzejazdów += liczbaPrzejazdów;
    }

    public void dodajCzasOczekiwania(int czasOczekiwania) {
        assert czasOczekiwania >= 0: "Niepoprawny czas oczekiwania";
        this.całkowityCzasOczekiwania += czasOczekiwania;
    }

}
