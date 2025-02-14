package bank;


import bank.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PrivateBank implements Bank {
    private String name = "";
    private double incomingInterest = 0;
    private double outgoingInterest = 0;
    private HashMap<String, List<Transaction>> accountsToTransactions = new HashMap<>();
    private String directoryName = "";

    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directoryName) throws IOException {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
        this.directoryName = directoryName;

        // Create directory if it doesn't exist
        File directory = new File(directoryName);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Failed to create directory: " + directoryName);
            }
            System.out.println("Directory for " + directoryName + " created successfully");
        }

        // Read existing accounts
        readAccounts();
    }

    public PrivateBank(PrivateBank privateBank) {
        setName(privateBank.getName());
        setIncomingInterest(privateBank.getIncomingInterest());
        setOutgoingInterest(privateBank.getOutgoingInterest());
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = this.directoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIncomingInterest() {
        return this.incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            throw new IllegalArgumentException("Incoming interest must be between 0 and 1");
        }
    }

    public double getOutgoingInterest() {
        return this.outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            throw new IllegalArgumentException("Outgoing interest must be between 0 and 1");
        }
    }
    /**
     * Gibt die Map von Konten zu Transaktionen zurück.
     *
     * @return die Map von Konten zu Transaktionen
     */

    public HashMap<String, List<Transaction>> getAccountsToTransactions() {
        return this.accountsToTransactions;
    }
    /**
     * Setzt die Map von Konten zu Transaktionen.
     *
     * @param accountsToTransactions die neue Map von Konten zu Transaktionen
     */
    public void setAccountsToTransactions(HashMap<String, List<Transaction>> accountsToTransactions) {
        if (this.accountsToTransactions == null) {
            throw new IllegalArgumentException("accountToTransactions map cannot be null");
        }
        this.accountsToTransactions = accountsToTransactions;
    }

    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account '" + account + "' already exists.");
        }
        accountsToTransactions.put(account, new ArrayList<>());
        writeAccount(account);
    }


    /**
     * Adds an account (with specified transactions) to the bank.
     * Important: duplicate transactions must not be added to the account!
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException    if the account already exists
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account '" + account + "' already exists.");
        }

        List<Transaction> newTransactions = new ArrayList<>();
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                if (transaction == null) {
                    throw new TransactionAttributeException("Transaction cannot be null.");
                }
                if (newTransactions.contains(transaction)) {
                    throw new TransactionAlreadyExistException("Duplicate transaction found.");
                }
                newTransactions.add(transaction);
            }
        }
        accountsToTransactions.put(account, newTransactions);
        writeAccount(account);
    }

    /**
     * Adds a transaction to an already existing account.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        if (transaction == null) {
            throw new TransactionAttributeException("Transaction cannot be null.");
        }
        List<Transaction> transactions = accountsToTransactions.get(account);
        if (transactions.contains(transaction)) {
            throw new TransactionAlreadyExistException("Transaction already exists in the account.");
        }

        // Overwrite interests for Payment transactions
        if (transaction instanceof Payment) {
            ((Payment) transaction).setIncomingInterest(this.incomingInterest);
            ((Payment) transaction).setOutgoingInterest(this.outgoingInterest);
        }

        transactions.add(transaction);
        writeAccount(account);
    }

    /**
     * Removes a transaction from an account. If the transaction does not exist, an exception is
     * thrown.
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionDoesNotExistException if the transaction cannot be found
     */
    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        List<Transaction> transactions = accountsToTransactions.get(account);
        if (!transactions.contains(transaction)) {
            throw new TransactionDoesNotExistException("Transaction does not exist in the account.");
        }
        transactions.remove(transaction);
        writeAccount(account);
    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     */
    public boolean containsTransaction(String account, Transaction transaction) {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not  exists: " + account);
        }
        return accountsToTransactions.get(account).contains(transaction);
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) throws AccountDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist: " + account);
        }
        double balance = 0;
        for (Transaction transaction : accountsToTransactions.get(account)) {
            balance += transaction.calculate();
        }
        return balance;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactions(String account) throws AccountDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        return new ArrayList<>(accountsToTransactions.get(account));
    }

    /**
     * Returns a sorted list (-> calculated amounts) of transactions for a specific account. Sorts the list either in ascending or descending order
     * (or empty).
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return the sorted list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) throws AccountDoesNotExistException {
        List<Transaction> transactions = getTransactions(account);
        transactions.sort((t1, t2) -> {
            double amount1 = (t1 instanceof CalculateBill)
                    ? ((CalculateBill) t1).calculate()
                    : t1.getAmount();
            double amount2 = (t2 instanceof CalculateBill)
                    ? ((CalculateBill) t2).calculate()
                    : t2.getAmount();
            return asc ? Double.compare(amount1, amount2) : Double.compare(amount2, amount1);
        });
        return transactions;
    }

    /**
     * Returns a list of either positive or negative transactions (-> calculated amounts).
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return the list of all transactions by type
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist: " + account);
        }

        // Alle Transaktionen des Kontos abrufen
        List<Transaction> allTransactions = accountsToTransactions.get(account);
        List<Transaction> filteredTransactions = new ArrayList<>();

        // Transaktionen filtern
        for (Transaction transaction : allTransactions) {
            double amount = transaction.getAmount(); // Betrag der Transaktion

            if (positive) {
                // Positive Transaktionen: Betrag > 0 und entweder Payment oder IncomingTransfer
                if (amount > 0 && (transaction instanceof Payment || transaction instanceof IncomingTransfer)) {
                    filteredTransactions.add(transaction);
                }
            } else {
                // Negative Transaktionen: Betrag < 0 und entweder Payment oder OutgoingTransfer
                if (amount < 0 && (transaction instanceof Payment) || transaction instanceof OutgoingTransfer) {
                    filteredTransactions.add(transaction);
                }
            }
        }

        return filteredTransactions;
    }

    /**
     * Read all JSON files from directoryName
     * Deserialize and populate accountsToTransactions
     */
    private void readAccounts() throws IOException {
        File directory = new File(directoryName);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new TransactionSerializer())
                .setPrettyPrinting()
                .create();

        accountsToTransactions.clear();
        File[] files = directory.listFiles((dir, name) -> name.startsWith("Konto") && name.endsWith(".json")); // Filter files

        if (files != null) {
            for (File file : files) {
                try (Reader reader = new FileReader(file)) {
                    // Extract account name from filename (remove "Konto" prefix and ".json" suffix)
                    String accountName = file.getName().substring(5, file.getName().length() - 5);

                    // Der JSON-Inhalt wird in eine Liste von Transaction-Objekten konvertiert
                    // TypeToken beschreibt den Typ der Liste, den Gson benötigt
                    Type listType = new TypeToken<List<Transaction>>(){}.getType();
                    List<Transaction> transactions = gson.fromJson(reader, listType);

                    // Add to map
                    accountsToTransactions.put(accountName, transactions != null ? transactions : new ArrayList<>());
                }
            }
        }
    }

    /**
     * Serialize account data to JSON
     * Save to file named Konto "Konto" + account + ".json"
     */
    /**
     * writing into the file
     * @param account
     */
    private void writeAccount(String account) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new TransactionSerializer())
                .setPrettyPrinting()
                .create();

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        List<Transaction> transactions = accountsToTransactions.get(account);
        File file = new File(directory, account + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            // Create JsonArray manually
            JsonArray array = new JsonArray();
            for (Transaction transaction : transactions) {
                // Create wrapper object manually
                JsonObject wrapper = new JsonObject();
                wrapper.addProperty("CLASSNAME", transaction instanceof Payment ? "Payment" : "Transfer");

                // Create instance object
                JsonObject instance = new JsonObject();
                instance.addProperty("date", transaction.getDate());
                instance.addProperty("description", transaction.getDescription());
                instance.addProperty("amount", transaction.getAmount());

                if (transaction instanceof Payment payment) {
                    instance.addProperty("incomingInterest", payment.getIncomingInterest());
                    instance.addProperty("outgoingInterest", payment.getOutgoingInterest());
                }

                wrapper.add("INSTANCE", instance);
                array.add(wrapper);
            }

            gson.toJson(array, writer);
        }
    }

    @Override
    public String toString() {
        return "PrivateBank{" +
                "Name='" + name + '\'' +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                ", accountsToTransactions=" + accountsToTransactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateBank that = (PrivateBank) o;
        return Double.compare(incomingInterest, that.incomingInterest) == 0 && Double.compare(outgoingInterest, that.outgoingInterest) == 0 && Objects.equals(name, that.name) && Objects.equals(accountsToTransactions, that.accountsToTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, incomingInterest, outgoingInterest, accountsToTransactions);
    }
}
