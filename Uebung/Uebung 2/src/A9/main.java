package A9;
// b), c), d), e), f)

public class main {
     public static void main(String[] args) {

         Point punkt1= new Point();
         Point punkt2= new Point(punkt1);
         Point punkt3= new Point(3,2);

         System.out.println(punkt1);
         System.out.println(punkt2);
         System.out.println(punkt3);


         punkt1.setLocation(punkt3);
         punkt2.setLocation(4,8);
         punkt3.move(10,15);
         System.out.println();

         System.out.println("P1: " + punkt1);
         System.out.println("P2: " + punkt2);
         System.out.println("P3: " + punkt3);

         System.out.println(punkt1.equals(punkt2));

     }
}
