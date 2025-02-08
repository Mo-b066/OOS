package A10;

public class Angestellter extends Mitarbeiter {
    double grundGehalt;
    double ortsZuschlag;
    double zulage;

    Angestellter(double grundGehalt, double ortsZuschlag, double zulage){
        this.grundGehalt = grundGehalt;
        this.ortsZuschlag = ortsZuschlag;
        this.zulage = zulage;
    }
    boolean equals(Angestellter angestellter){
        return ( super.equals(angestellter) && this.grundGehalt ==
                angestellter.grundGehalt &&
                this.ortsZuschlag == angestellter.ortsZuschlag &&
                this.zulage == angestellter.zulage );
    }

    public String toString() {
        return( "grundGehalt: " + grundGehalt +
                " ortsZuschlag: " + ortsZuschlag +
                " zulage: " + zulage );
    }


    @Override
    double montasBrutto(){
        return (grundGehalt + ortsZuschlag + zulage);
    }

}
