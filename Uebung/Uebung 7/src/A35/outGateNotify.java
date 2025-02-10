package A35;

class outGateNotify implements Runnable{
    NotifyCounter count;
    outGateNotify(NotifyCounter c){
        count = c;
    }
    @Override
    public void run(){
        count.dec();
    }
}

