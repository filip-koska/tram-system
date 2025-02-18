package pojemnik;

import czas.Moment;
import przystanki.Przystanek;
import pasażerowie.Pasażer;

// Zestaw operacji określonych dla kolejki pasażerów
public interface KolejkaPasażerów {

    // Wstawia pasażera na koniec kolejki
    void wstaw(Pasażer p);

    // Zdejmuje pasażera z początku kolejki i zwraca go
    Pasażer zdejmij();

    // Usuwa pasażera na i-tym miejscu z w kolejce i zwraca go
    Pasażer usuńZeŚrodka(int i);

    boolean czyPusta();

    boolean czyPełna();

    int znajdźChętnegoDoWysiadki(Przystanek przystanek);

    int dodajCzasyOczekiwania(Moment ostatniMomentDnia);

    void opróżnij();
}
