package A11;

public class Pyramide implements Koerper {

    int laenge;
    int hoehe;
    int breite;

    public Pyramide(int laenge, int hoehe, int breite) {
        this.laenge = laenge();
        this.hoehe = hoehe;
        this.breite = breite;
    }

    public int volumen() {
        return laenge * breite * hoehe / 3;
    }

    @Override
    public int laenge() {
        return 0;
    }

    @Override
    public int hoehe() {
        return 0;
    }

    @Override
    public int breite() {
        return 0;
    }
}
