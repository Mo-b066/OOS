package bank;

import java.util.List;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String HEADER = "\u001B[95m";
    public static final String SUCCESS = "\u001B[92m";
    public static final String ERROR = "\u001B[91m";

    public static void main(String[] args) {
        String directory = "bank_accounts"; // Same directory as in MainPageController
        PrivateBank bank = new PrivateBank("TestBank", 0.05, 0.1, directory);

        printHeader("CREATING TEST ACCOUNTS AND TRANSACTIONS");

        try {
            // Create test accounts
            String[] accounts = {
                "John Doe", "Jane Smith", "Bob Wilson",
                "Alice Johnson", "Charlie Brown", "Diana Prince"
            };

            // Create various transactions
            Payment salary1 = new Payment("2024-03-20", "Monthly Salary", 3000.0, 0.05, 0.1);
            Payment salary2 = new Payment("2024-03-21", "Monthly Salary", 4500.0, 0.05, 0.1);
            // Change negative payments to positive amounts with outgoing flag
            Payment rent = new Payment("2024-03-22", "Rent Payment", 1200.0, 0.1, 0.05);     // Outgoing payment
            Payment utilities = new Payment("2024-03-23", "Utilities", 200.0, 0.1, 0.05);     // Outgoing payment
            Payment groceries = new Payment("2024-03-24", "Groceries", 150.0, 0.1, 0.05);     // Outgoing payment
            
            // Transfers remain unchanged
            Transfer gift1 = new IncomingTransfer("2024-03-25", "Birthday Gift", 500.0, "Charlie Brown", "John Doe");
            Transfer gift2 = new OutgoingTransfer("2024-03-26", "Wedding Gift", 300.0, "Jane Smith", "Alice Johnson");
            Transfer loan = new IncomingTransfer("2024-03-27", "Loan Return", 1000.0, "Bob Wilson", "Diana Prince");

            System.out.println("\nCreating accounts and adding transactions...");

            // Create accounts and add transactions
            for (String account : accounts) {
                try {
                    bank.createAccount(account);
                    System.out.println(SUCCESS + "Created account: " + account + RESET);
                } catch (Exception e) {
                    System.out.println(ERROR + "Error creating account " + account + ": " + e.getMessage() + RESET);
                }
            }

            // Add transactions to accounts
            try {
                bank.addTransaction("John Doe", salary1);
                bank.addTransaction("John Doe", rent);
                bank.addTransaction("John Doe", gift1);

                bank.addTransaction("Jane Smith", salary2);
                bank.addTransaction("Jane Smith", utilities);
                bank.addTransaction("Jane Smith", gift2);

                bank.addTransaction("Bob Wilson", salary1);
                bank.addTransaction("Bob Wilson", groceries);
                bank.addTransaction("Bob Wilson", loan);

                bank.addTransaction("Alice Johnson", salary2);
                bank.addTransaction("Alice Johnson", rent);
                bank.addTransaction("Alice Johnson", gift2);

                bank.addTransaction("Charlie Brown", salary1);
                bank.addTransaction("Charlie Brown", utilities);
                bank.addTransaction("Charlie Brown", gift1);

                bank.addTransaction("Diana Prince", salary2);
                bank.addTransaction("Diana Prince", groceries);
                bank.addTransaction("Diana Prince", loan);
            } catch (Exception e) {
                System.out.println(ERROR + "Error adding transactions: " + e.getMessage() + RESET);
            }

            // Display all accounts and their balances
            System.out.println("\nAccount Balances:");
            for (String account : accounts) {
                try {
                    double balance = bank.getAccountBalance(account);
                    System.out.printf(SUCCESS + "%s: %.2f€%s%n", account, balance, RESET);
                    
                    System.out.println("Transactions:");
                    List<Transaction> transactions = bank.getTransactions(account);
                    for (Transaction t : transactions) {
                        System.out.println(SUCCESS + "  - " + t + RESET);
                    }
                    System.out.println();
                } catch (Exception e) {
                    System.out.println(ERROR + "Error getting account info for " + account + ": " + e.getMessage() + RESET);
                }
            }

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
