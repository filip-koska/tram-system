package czytnik;

import java.util.Scanner;

// Simple Singleton wrapper for a Scanner
public final class Czytnik {
    // data

    private static final Scanner wejście = new Scanner(System.in);

    // technicalities

    private Czytnik() {}

    // operations

    public static Scanner odczyt() {
        return wejście;
    }

}
