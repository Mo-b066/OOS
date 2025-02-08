package A9;

// a)

public class Point {

     int x;
     int y;

    Point(){}

    // Kopie Konstruktor
    Point(Point p){
        this.x= p.x;
        this.y= p.y;
    }

    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    Point getLocatin(){
        return (new Point(this));
    }

    public void setLocation(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean equals(Object obj) {
        Point pt= (Point) obj;
        return (x == pt.x) && (y == pt.y);
    }

    @Override
    public String toString(){

        return ("X: " + this.x + " , " + "Y: " + this.y);
    }
}
