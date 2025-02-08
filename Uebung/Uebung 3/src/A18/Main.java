package A18;

public class Main {
    public static void main(String[]args){
        int x = 0;
        int y = 5;

        try {
            int z = y / x;
        }
        catch(ArithmeticException e){
            System.out.println("Du kannst nicht durch null teilen");
        }
        finally{
            System.out.println("Wähle eine andere Zahl");
        }

    }
}
