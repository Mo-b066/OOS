package A34;

public class Addierer implements Runnable{
    int a = 0;

    @Override
    public void run(){
        for(int i = 0; i < 1000000; i++){
            synchronized (this){
                a = a + 1;
            }
        }

     }
}
