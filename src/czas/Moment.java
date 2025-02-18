package czas;

// Klasa przechowująca moment w czasie
public class Moment {

    // dane

    // Trzymamy czas jako godzinę wyrażoną w minutach
    private int czas;

    private int dzień;

    // techniczne

    public Moment(int czas, int dzień) {
        this.czas = czas;
        this.dzień = dzień;
    }

    // operacje


    // Metoda toString zamienia czas w minutach na format GG:MM (dwie cyfry na godziny i dwie na minuty, oddzielone dwukropkiem)
    @Override
    public String toString() {
        return "Dzień " + this.dzień + ", godz. "
                + this.czas / 60 + ":" + (this.czas % 60 < 10 ? "0" + this.czas  % 60 : this.czas % 60) + ": ";
    }

    public int czas() {
        return this.czas;
    }

    public int dzień() {
        return this.dzień;
    }


}
