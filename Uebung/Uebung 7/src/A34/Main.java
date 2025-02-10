package A34;

public class Main {
    public static void main(String[]args) throws InterruptedException {

        Addierer zahl = new Addierer();
        Thread thread1= new Thread(zahl);
        Thread thread2= new Thread(zahl);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(zahl.a);
    }
}
