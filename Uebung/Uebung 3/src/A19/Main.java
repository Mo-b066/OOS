package A19;
// b)
public class Main {

    public static void main(String[]args){

        try{

            Ausnahme beispiel1= new Ausnahme();
            beispiel1.ungeradeEx(3);
        }
        catch(ArithmeticException e){
            System.out.println("Ungerade Zahl");

        }
    }
}
