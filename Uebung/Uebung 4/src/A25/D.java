package A25;

import java.io.Serializable;

public class D extends C implements Serializable {
    public int d = 4;

    public D(){
        System.out.println("Konstruktor von D");
    }

    //c)

    public String toString(){
        return(a + "," + b + "," + c + "," + d);

    }


}
