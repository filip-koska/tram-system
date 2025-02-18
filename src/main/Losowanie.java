package main;

import java.util.Random;

// Klasa obsługująca losowanie liczby z przedziału
public class Losowanie {

    private static final Random r = new Random();

    private Losowanie() {}

    public static int losuj(int dolna, int gorna) {
        assert dolna <= gorna: "Niepoprawne granice losowania";
        return r.nextInt(gorna - dolna + 1) + dolna;
    }
}
