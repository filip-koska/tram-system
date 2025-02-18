package pojemnik;

import czas.Moment;
import przystanki.Przystanek;
import pasażerowie.Pasażer;

// Interface for a passenger queue
public interface KolejkaPasażerów {

    // enqueue
    void wstaw(Pasażer p);

    // dequeue
    Pasażer zdejmij();

    // erase
    Pasażer usuńZeŚrodka(int i);

    // isEmpty
    boolean czyPusta();

    // isFull
    boolean czyPełna();

    // Finds a passenger willing to get off at given stop
    int znajdźChętnegoDoWysiadki(Przystanek przystanek);

    // Add waiting times of the passengers in the queue
    int dodajCzasyOczekiwania(Moment ostatniMomentDnia);

    // clear
    void opróżnij();
}
