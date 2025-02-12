import bank.Payment;
import bank.Transfer;

public class Main {

    public static void main(String[] args) {

        // Test for the Payment class: Deposit
        Payment paymentEinzahlung = new Payment(null, 1000.0, "Deposit", 0.05, 0.1);
        System.out.println("=== Payment Einzahlung ===");
        System.out.println("Payment: " + paymentEinzahlung);
        System.out.println("Berechneter amount nach Einzahlung mit incomingInterest: " + paymentEinzahlung.calculate());

        // Test for the Payment class: Withdrawal
        Payment paymentAuszahlung = new Payment("2024-10-28", -1000.0, "Withdrawal", 0.05, 0.1);
        System.out.println("\n=== Payment Auszahlung ===");
        System.out.println("Payment: " + paymentAuszahlung);
        System.out.println("Calculated amount after withdrawal with outgoingInterest: " + paymentAuszahlung.calculate());

        // Test for the Payment Copy Constructor
        Payment paymentCopy = new Payment(paymentEinzahlung);
        System.out.println("\n=== Payment Copy ===");
        System.out.println("Copy von Payment: " + paymentCopy);
        System.out.println("Calculated amount of the copy: " + paymentCopy.calculate());

        // Comparison of objects with equals()
        System.out.println("\n=== Payment Vergleiche ===");
        System.out.println("Vergleich von original und copy mit equals(): " + paymentEinzahlung.equals(paymentCopy));
        System.out.println("Vergleich von original und Auszahlung mit equals(): " + paymentEinzahlung.equals(paymentAuszahlung));

        // Test for the Transfer class: Transfer
        Transfer transfer = new Transfer("2024-10-28", "Transfer", 500.0, "Alice", "Bob");
        System.out.println("\n=== Transfer ===");
        System.out.println("Transfer: " + transfer);
        System.out.println("Berechneter amount (Transfer): " + transfer.calculate());

        // Test for the Transfer Copy Constructor
        Transfer transferCopy = new Transfer(transfer);
        System.out.println("\n=== Transfer Copy ===");
        System.out.println("Copy von Transfer: " + transferCopy);
        System.out.println("Berechneter amount von copy (Transfer): " + transferCopy.calculate());

        // Comparison of Transfer objects with equals()
        System.out.println("\n=== Transfer Vergleich ===");
        System.out.println("Vergleich vom original und copy (Transfer) mit equals(): " + transfer.equals(transferCopy));
        System.out.println("Vergleich vom original und Auszahlung (Payment) mit equals(): " + transfer.equals(paymentAuszahlung));
    }
}