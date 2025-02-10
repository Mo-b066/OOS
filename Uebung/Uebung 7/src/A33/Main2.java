package A33;

public class Main2 {
    public static void main(String[]args){
        MyThread2 runneable1 = new MyThread2("Thread 1: ");
        Thread thread1 = new Thread(runneable1);
        MyThread2 runneable2 = new MyThread2("Thread 2: ");
        Thread thread2 = new Thread(runneable2);
        thread1.start();
        thread2.start();
    }
}
