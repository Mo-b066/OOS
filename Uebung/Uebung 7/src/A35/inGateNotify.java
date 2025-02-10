package A35;

class inGateNotify implements Runnable{
    NotifyCounter count;
    inGateNotify(NotifyCounter c){
        count = c;
    }
    @Override
    public void run(){
        count.inc();
    }
}

