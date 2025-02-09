package A29;

public class Main {
    public static void main(String[]args){
        @FunctionalInterface
         interface LambdaInterface {
            public boolean lambdaMethode(int a, int b, int result);
        }
        LambdaInterface add = (x,y,z) -> {
            if (x + y == z) {
                return true;
            }
            else{
                return false;
            }
        };

        LambdaInterface sub = (x,y,z) -> {
            if(x-y==z){
                return true;
            }
            else{return false;}
        };

        LambdaInterface multi = (x,y,z) -> {
            if(x*y==z){
                return true;
            }
            else{return false;}
        };

        LambdaInterface div = (x,y,z) -> {
            if(x/y==z){
                return true;
            }
            else{return false;}
        };

        System.out.println("Adition: " + add.lambdaMethode(1,2,3));
        System.out.println("Subtraktion: " + div.lambdaMethode(4,2,2));
        System.out.println("Multiplikation: " + multi.lambdaMethode(1,2,2));
        System.out.println("Divison: " + add.lambdaMethode(1,2,3));

    }
}
