package A16;

public class Exceptions {
    public static void main(String[] args) {
        String[] numbers = new String[3];
        numbers[0] = "10";
        numbers[1] = "20";
        numbers[2] = "30";
        try {
            for (int base = 10; base >= 2; --base) {
                for (int j = 0; j <= 3; ++j) {
                    int i = Integer.parseInt(numbers[j], base);
                    System.out.println(numbers[j] + "base " + base + " = " + i);
                }
            }
        } catch (NumberFormatException e2) {
            System.out.println("***NFEx: ");
        } catch (IndexOutOfBoundsException e1) {
            System.out.println("***IOOEx: ");
        }
    }
}

// a)
// Die Ausgabe ist gleich, da nur der Fall IndexOutOfBoundsException auftritt. Es
// wird jedoch hierbei zuerst geprüft, ob es eine NumberFormatException ist.