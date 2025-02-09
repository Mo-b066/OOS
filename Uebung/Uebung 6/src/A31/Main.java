package A31;
import java.util.ArrayList;
public class Main {
    public static void main(String[]arg){

        ArrayList<Integer> zahlenliste = new ArrayList<Integer>();
        zahlenliste.add(1);
        zahlenliste.add(2);
        zahlenliste.add(3);
        zahlenliste.add(4);

        zahlenliste.forEach( zahl -> System.out.println( zahl + 1));





    }
}
