package main;

import java.util.Random;

// A simple Singleton wrapper for a random number generator
public class Losowanie {

    private static final Random r = new Random();

    private Losowanie() {}

    public static int losuj(int dolna, int gorna) {
        assert dolna <= gorna: "Niepoprawne granice losowania";
        return r.nextInt(gorna - dolna + 1) + dolna;
    }
}
