package A222;
import java.io.*;

public class Main {
    public static void main(String [] args){
    try {
        FileOutputStream fileSer = new FileOutputStream("A222.txt");
        ObjectOutputStream objSer = new ObjectOutputStream(fileSer);
        objSer.writeInt(2);
        objSer.writeObject("hallo");
        objSer.writeObject(new Zeit(2,2));
        objSer.writeObject(new Zeit(3,3));
        objSer.close();
    }
    catch(IOException e){
        System.out.print("IOException ausgelöst");
    }

    }
}
