import bank.Payment;
import bank.Transfer;


public class Main {
    public static void main(String[] args) {


        Payment payment1=new Payment("04.10.2024",511,"Bafög");
        System.out.println("Payment 1:");

        payment1.printObject();
        System.out.println("");

        Payment payment2=new Payment("05.10.2024",313.67, "Semesterbeitrag",0.2,0.1);
        System.out.println("Payment 2:");
        payment2.printObject();
        System.out.println("");

        Payment payment3=new Payment("05.10.2024",313.67, "Semesterbeitrag",2,0.1);
        System.out.println("Payment 3:");
        payment3.printObject();
        System.out.println("");

        Payment payment4=new Payment("05.10.2024",313.67, "Semesterbeitrag",0.2,2);
        System.out.println("Payment 4:");
        payment4.printObject();
        System.out.println("");

        Payment payment5=new Payment("05.10.2024",313.67, "Semesterbeitrag",2,3);
        System.out.println("Payment 5:");
        payment5.printObject();
        System.out.println("");

        Payment payment6= new Payment(payment1);
        System.out.println("Payment 6:");
        payment6.printObject();
        System.out.println("");


        Transfer transfer1=new Transfer("12.10.2024",7,"Döner");
        System.out.println("Transfer 1:");
        transfer1.printObject();
        System.out.println("");

        Transfer transfer2=new Transfer("13.10.2024",45,"Paypal", "Kelvin","MO");
        System.out.println("Transfer 2:");
        transfer2.printObject();
        System.out.println("");

        Transfer transfer3=new Transfer(transfer1);
        System.out.println("Transfer 3:");
        transfer3.printObject();
        System.out.println("");



    }

}