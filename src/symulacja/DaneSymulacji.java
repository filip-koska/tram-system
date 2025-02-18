package symulacja;

import przystanki.Przystanek;
import linie.Linia;
import czytnik.Czytnik;
import pasażerowie.Pasażer;
import main.Losowanie;

import java.util.Arrays;

// Class storing data of a single simulation
public class DaneSymulacji {

    // data
    
    // Critical constant time values

    // last hour of service
    private static final int ostatniaGodzinaKursowania = 23 * 60;
    // first hour of passenger influx
    private static final int pierwszaGodzinaPrzychodzenia = 6 * 60;
    // last hour of passenger influx
    private static final int ostatniaGodzinaPrzychodzenia = 12 * 60;

    // number of days
    private int liczbaDni;
    // number of passengers
    private int liczbaPasażerów;
    // number of stops
    private int liczbaPrzystanków;
    // stop capacity
    private int pojemnośćPrzystanku;
    // tram capacity
    private int pojemnośćTramwaju;
    // number of lines
    private int liczbaLinii;
    // lines
    private Linia[] linie;
    // stops
    private Przystanek[] przystanki;
    // passengers
    private Pasażer[] pasażerowie;
    // helper variable for tram number generation
    private int numerTramwaju;
    // total waiting time
    private int całkowityCzasOczekiwania;
    // total number of rides
    private int całkowitaLiczbaPrzejazdów;

    // technicalities

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

    // Randomly selects a stop from the stops array
    public Przystanek losujPrzystanek() {
        int indeksPrzystankuDomowego = Losowanie.losuj(0, this.liczbaPrzystanków - 1);
        return this.przystanki[indeksPrzystankuDomowego];
    }
    
    // Searches for the stop in the stops array using binary search
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

    // Returns an ID number for a newly constructed tram and updates the return value for future trams
    public int nowyNumerTramwaju() {
        return this.numerTramwaju++;
    }

    // Updates total number of rides
    public void dodajPrzejazdy(int liczbaPrzejazdów) {
        assert liczbaPrzejazdów >= 0: "Niepoprawna liczba przejazdów";
        this.całkowitaLiczbaPrzejazdów += liczbaPrzejazdów;
    }

    // Updates total waiting time
    public void dodajCzasOczekiwania(int czasOczekiwania) {
        assert czasOczekiwania >= 0: "Niepoprawny czas oczekiwania";
        this.całkowityCzasOczekiwania += czasOczekiwania;
    }

}
