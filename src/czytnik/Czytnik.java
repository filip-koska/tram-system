package czytnik;

import java.util.Scanner;
public final class Czytnik {
    // dane

    private static final Scanner wejście = new Scanner(System.in);

    // techniczne

    private Czytnik() {}

    // operacje

    public static Scanner odczyt() {
        return wejście;
    }

}
