package A10;

public class main {
    public static void main(String [] args){

   Mitarbeiter[] ma = new Mitarbeiter[5];
        ma[0] = new Manager(10.0,10.0,10.0,10.0,10.0);
        ma[1] = new Arbeiter(10.0,10.0,10.0,10.0,10.0);
        ma[2] = new Arbeiter(10.0,10.0,10.0,10.0,10.0);
        ma[3] = new Angestellter(10.0,10.0,10.0);
        ma[4] = new Angestellter(10.0,10.0,10.0);

        // Brutto berechnen
        double bruttoSumme = 0.0;
        for(int i = 0; i < ma.length; i++){
            bruttoSumme += ma[i].montasBrutto();
            System.out.println(ma[i]);
        }
        System.out.println("Bruttosumme = "+bruttoSumme);


    }
}
