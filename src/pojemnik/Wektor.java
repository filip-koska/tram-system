package pojemnik;

// Interface for a dynamic array
// The modesty of the interface is the result of not having been yet introduced
// to generic programming in Java
public interface Wektor {

    // isEmpty
    boolean czyPusty();

    // size
    int rozmiar();

    // capacity
    int pojemność();
}
