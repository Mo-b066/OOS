package A32;

import java.util.ArrayList;
import java.util.Comparator;

public class Sorting {

    public static void main (String [] args){
        ArrayList<Human> mensch = new ArrayList<Human>();
        mensch.add(new Human("Kelvin", 23));
        mensch.add(new Human("MO", 22));

        for(Human h:mensch){
            System.out.println(h.toString());
        }

        mensch.sort(new Comparator<Human>() {
            @Override
            public int compare(Human o1, Human o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        mensch.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        System.out.println("After sorting:");
        for(Human h:mensch){
            System.out.println(h.toString());
        }
    }
}

