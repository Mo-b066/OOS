package A22;
import java.io.*;

public class Main {
    public static void main(String[]args){
      try {
          Klasse1 klasse1 = new Klasse1();
          Klasse2 klasse2 = new Klasse2();

          klasse1.attr1= klasse2;
          klasse1.attr2= 1;

          klasse2.attr1= klasse1;
          klasse2.attr2= 1;

          FileOutputStream fileIn = new FileOutputStream("A22.txt");
          ObjectOutputStream obIn = new ObjectOutputStream(fileIn);
          obIn.writeObject(klasse1);
          obIn.close();

          FileInputStream fileOut = new FileInputStream("C:\\Users\\mohan\\Uni\\GitHub\\OOS\\A22.txt");
          ObjectInputStream objOut= new ObjectInputStream(fileOut);
          Klasse1 klasseNeu = (Klasse1) objOut.readObject();
          System.out.println(klasseNeu.attr2);
          System.out.println(klasseNeu.attr1.attr2);

      }
      catch (IOException e) {
          System.out.print("IOException ausgelöst");
      }
      catch(ClassNotFoundException e){
          System.out.print("ClassNotFoundException ausgelöst");
      }


    }
}
