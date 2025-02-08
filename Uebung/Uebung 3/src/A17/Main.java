package A17;

// b)
public class Main {

    public static void main(String[]args){

        MeineException me = new MeineException("");

        try{
            me.ExWerfenUndWeiterleiten();

        }
        catch(MeineException e){
            System.out.print("Exception wurde geworfen");
        }


    }


}
