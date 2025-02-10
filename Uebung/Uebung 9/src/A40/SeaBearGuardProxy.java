package A40;

class SeaBearGuardProxy implements BearProtectInterface {
    private SeaBearOriginal seaBear = new SeaBearOriginal();

    @Override
    public void allowVisit(int visitorCode) {
        if (visitorCode == 1234) { // Beispiel für eine einfache Zugangskontrolle
            seaBear.allowVisit(visitorCode);
        } else {
            System.out.println("Access denied for visitor " + visitorCode);
        }
    }
}

