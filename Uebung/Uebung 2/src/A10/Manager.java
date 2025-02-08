package A10;

public class Manager extends Mitarbeiter{

    double fixGehalt;
    double provision1;
    double provision2;
    double umsatz1;
    double umsatz2;

    Manager(double fixGehalt, double provision1, double provision2, double umsatz1, double umsatz2){
        this.fixGehalt = fixGehalt;
        this.provision1 = provision1;
        this.provision2 = provision2;
        this.umsatz1 = umsatz1;
        this.umsatz2 = umsatz2;
    }

    @Override
    double montasBrutto(){
    return (fixGehalt + umsatz1 * provision1 / 100 + umsatz2 * provision2 / 100);
    }


    boolean equals (Manager manager) {
        return( super.equals(manager) &&
                this.fixGehalt == manager.fixGehalt &&
                this.provision1 == manager.provision1 &&
                this.provision2 == manager.provision2 &&
                this.umsatz1 == manager.umsatz1 &&
                this.umsatz2 == manager.umsatz2 );
    }

    public String toString() {
        return(
                "fixGehalt: " + fixGehalt +
                " provision1: " + provision1 +
                " provision2: " + provision2 +
                " umsatz1: " + umsatz1 +
                " umsatz2: " + umsatz2 );
    }


}
