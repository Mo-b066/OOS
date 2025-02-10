package A33;

public class MyThread extends Thread {
    String name;
    MyThread(String name){

        this.name=name;
    }

    public void run(){
        for(int i = 5; i >= 0; i--){
            System.out.println(this.name + i);
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

        }

    }

}
