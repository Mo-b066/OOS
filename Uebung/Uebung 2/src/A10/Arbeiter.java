package A10;

public class Arbeiter extends Mitarbeiter{
    double stundenLohn;
    double anzahlStunden;
    double ueberStundenZuschlag;
    double anzahlUeberStunden;
    double schichtZulage;

    Arbeiter(double stundenLohn, double anzahlStunden, double ueberStundenZuschlag, double anzahlUeberStunden, double schichtZulage) {
        this.stundenLohn = stundenLohn;
        this.anzahlStunden = anzahlStunden;
        this.ueberStundenZuschlag = ueberStundenZuschlag;
        this.anzahlUeberStunden = anzahlUeberStunden;
        this.schichtZulage = schichtZulage;
    }

    boolean equals(Arbeiter arbeiter) {
        return ( super.equals(arbeiter) && this.stundenLohn == arbeiter.stundenLohn &&
                this.anzahlStunden == arbeiter.anzahlStunden &&
                this.ueberStundenZuschlag == arbeiter.ueberStundenZuschlag &&
                this.anzahlUeberStunden == arbeiter.anzahlUeberStunden &&
                this.schichtZulage == arbeiter.schichtZulage );
    }

    public String toString() {
        return (
                "stundenLohn: " + stundenLohn +
                " anzahlStunden: " + anzahlStunden +
                " ueberStundenZuschlag: " + ueberStundenZuschlag +
                " anzahlUeberStunden: " + anzahlUeberStunden +
                " schichtZulage: " + schichtZulage);
    }

    @Override
    double montasBrutto() {
        return ( stundenLohn*anzahlStunden +
                ueberStundenZuschlag*anzahlUeberStunden +
                schichtZulage );
    }


}
