package A40;

public class NatureParkVisitorClient {
    public static void main(String[] args) {
        SeaBearGuardProxy proxy = new SeaBearGuardProxy();

        proxy.allowVisit(1234); // Zugriff erlaubt
        proxy.allowVisit(5678); // Zugriff verweigert
    }
}

