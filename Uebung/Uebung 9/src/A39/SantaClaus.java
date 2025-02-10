package A39;

public class SantaClaus {
    String name;

    private static SantaClaus insatnce;
    private SantaClaus () {
        name = "Santa";
    }
    public static SantaClaus getInstance(){
        if(insatnce == null){
            insatnce = new SantaClaus();
        }
        return insatnce;
    }


}
