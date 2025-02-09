package A30;

public class Main {
    @FunctionalInterface
    interface StringMethod{
        String run(String eingabe);
    }

    public static void main(String[]strg){

        StringMethod bsp1 = s -> s + "!";
        StringMethod bsp2 = s -> "??" + s ;

        printFormatted(bsp1, "hallo");
        printFormatted(bsp2, "bye");
    }


    public static void printFormatted(StringMethod input, String result){
        result = input.run(result);
        System.out.println(result);

    }

}
