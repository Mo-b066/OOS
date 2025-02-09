package A25;
import java.io.*;
//b)
public class Main {
    public static void main(String[]strg){

        try {
            D d = new D();
            d.a = 10;
            d.b = 20;
            d.c = 30;
            d.d = 40;

            //c)
            //System.out.println(d);

            //d)
            /*
            FileOutputStream file = new FileOutputStream("A25(d).txt");
            ObjectOutputStream obj = new ObjectOutputStream(file);
            obj.writeObject(d);
            obj.close();
        }
        catch(IOException e){
            System.out.println("IOException");
        }
        */

           //e)
            FileInputStream file = new FileInputStream("C:\\Users\\mohan\\Uni\\GitHub\\OOS\\A25(d).txt");
            ObjectInputStream obj = new ObjectInputStream(file);
            D dNeu = (D) obj.readObject();
            System.out.println(dNeu);
            obj.close();
        }
        catch(IOException e){
            System.out.println("IOException");
        }
        catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException");
        }

    }
}
