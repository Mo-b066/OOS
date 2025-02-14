import bank.IncomingTransfer;
import bank.Payment;
import bank.PrivateBank;
import bank.Transfer;

import java.io.File;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String HEADER = "\u001B[95m";
    public static final String SUCCESS = "\u001B[92m";
    public static final String ERROR = "\u001B[91m";

    public static void main(String[] args) {
        printHeader("TESTING FILE I/O OPERATIONS");

        try {
            // Create bank instance with directory
            String directory = "bank_accounts";
            PrivateBank bank = new PrivateBank("TestBank", 0.05, 0.1, directory);

            // Create test transactions
            Payment salary = new Payment("2024-03-20",2500.0, "Monthly Salary",  0.05, 0.1);
            Payment rent = new Payment("2024-03-21", 800.0,"Rent Payment",  0.05, 0.1);
            Transfer incoming = new IncomingTransfer("2024-03-22", "Gift", "Alice", "Bob", 200.0);

            // Create account and add transactions
            System.out.println("\nCreating account and adding transactions...");
            bank.createAccount("Alice");
            bank.addTransaction("Alice", salary);
            bank.addTransaction("Alice", rent);
            bank.addTransaction("Alice", incoming);

            // Verify file exists
            File accountFile = new File(directory, "Alice.json");
            System.out.println("File created: " + SUCCESS + accountFile.exists() + RESET);
            System.out.println("File path: " + SUCCESS + accountFile.getAbsolutePath() + RESET);

            // Create new bank instance to read data
            System.out.println("\nReading data from file with new bank instance...");
            PrivateBank newBank = new PrivateBank("TestBank", 0.05, 0.1, directory);

        } catch (Exception e) {
            System.out.println(ERROR + "Error: " + e.getMessage() + RESET);
            e.printStackTrace();
        }
    }

    private static void printHeader(String text) {
        System.out.println(HEADER + "=" + "=".repeat(text.length()) + "=" + RESET);
        System.out.println(HEADER + " " + text + " " + RESET);
        System.out.println(HEADER + "=" + "=".repeat(text.length()) + "=" + RESET);
    }
}