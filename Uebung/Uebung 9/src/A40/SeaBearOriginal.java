package A40;

class SeaBearOriginal implements BearProtectInterface {
    @Override
    public void allowVisit(int visitorCode) {
        System.out.println("Visitor " + visitorCode + " is visiting the Sea Bear.");
    }
}