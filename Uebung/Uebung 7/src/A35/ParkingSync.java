package A35;

import java.util.concurrent.TimeUnit;

public class ParkingSync {
    public static NotifyCounter x = new NotifyCounter();
    public static void main(String[]arg){

        Thread thread1 = new Thread(new inGateNotify(x));
        Thread thread2 = new Thread(new inGateNotify(x));
        Thread thread3 = new Thread(new inGateNotify(x));
        Thread thread4 = new Thread(new outGateNotify(x));
        Thread thread5 = new Thread(new outGateNotify(x));

        // Startet die Threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // Synchronisiert den Start der Threads mit notify()
        synchronized (x) {
            System.out.println("Main ruft notify() auf");
            x.notifyAll();  // Weckt alle wartenden Threads auf
        }
    }



}
