public class Main {
    public static void main(String[]args){
        @FunctionalInterface
         interface LambdaInterface {
            public boolean LambdaMethode(int a, int b, int result);
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

        System.out.println("Adition: " + add.LambdaMethode(1,2,3));
        System.out.println("Subtraktion: " + div.LambdaMethode(4,2,2));
        System.out.println("Multiplikation: " + multi.LambdaMethode(1,2,2));
        System.out.println("Divison: " + add.LambdaMethode(1,2,3));

    }
}
