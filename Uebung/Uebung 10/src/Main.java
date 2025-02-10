public class Main {
    public static void main(String[] args) {
        Polygon polygon = PolygonFactory.getPolygon(3);
        System.out.println(polygon.getType());
        // Pentagon
        Polygon pentagon = PolygonFactory.getPolygon(5);
        System.out.println(pentagon.getType());


    }
}