package A33;

public class MyThread2 implements Runnable{
    String name;
    MyThread2(String name){
        this.name=name;
    }

    @Override
    public void run(){
        for(int i = 5; i >= 0; i--){
            System.out.println(name + i);
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

        }

    }
}
