package A24;
import java.io.*;
public class Main {
    public static void main (String[]args){
        try{

            FileInputStream file = new FileInputStream("C:\\Users\\mohan\\Uni\\GitHub\\OOS\\A222.txt");
            ObjectInputStream obj = new ObjectInputStream(file);

            System.out.println(obj.readInt());
            System.out.println(obj.readObject());
            System.out.println(obj.readObject());
            System.out.println(obj.readObject());
            obj.close();


        }
        catch (IOException e){
            System.out.print("IOException ausgelöst");
        }
        catch (ClassNotFoundException e) {
            System.out.println("FehlerCNF");
        }

    }
}
