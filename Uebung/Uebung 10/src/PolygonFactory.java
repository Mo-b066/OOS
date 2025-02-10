public class PolygonFactory {
    public static Polygon getPolygon(int x){
        Polygon polygon = null;

        switch (x){
            case 3: polygon = new Triangle(); break;
            case 4: polygon = new Square(); break;
            case 5: polygon = new Pentagon(); break;
            default:
        }
        return polygon;
    }



}
