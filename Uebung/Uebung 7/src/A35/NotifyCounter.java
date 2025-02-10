package A35;

public class NotifyCounter {
    private int count = 50;
    private int limit = 100;

    public synchronized void inc() {
        if (this.count < limit) {
            count++;
            System.out.println("Cap++: " + this.count);
        }
    }

    public synchronized void dec() {
        if (this.count > 1) {
            count--;
            System.out.println("Cap--: " + this.count);
        }
    }
}
