package A19;
// a)
public class Ausnahme {

    public  void ungeradeEx(int x) throws ArithmeticException {
        if(x % 2 != 0){

            throw new ArithmeticException("Ungerade Zahl !!!");
        }
    }
}
