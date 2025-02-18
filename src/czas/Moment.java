package czas;

// A class storing a timestamp
public class Moment {

    // data

    // time
    // The time is represented logically as the number of minutes since the start of the day
    private int czas;

    // day
    private int dzień;

    // technicalities

    public Moment(int czas, int dzień) {
        this.czas = czas;
        this.dzień = dzień;
    }

    // operations


    // The method converts time (czas) to GG:MM format discarding seconds (two digits for hour and two digits for minutes, colon-separated)
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
