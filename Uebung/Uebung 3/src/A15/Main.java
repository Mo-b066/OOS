package A15;

public class Main {
    public static void main(String[] args) {

        try {
            Ausnahme ab = new Ausnahme();
            ab.nullptr();
        }
        catch (NullPointerException e){
            System.out.println("Fehler ist aufgetreten");
        }
    }
}