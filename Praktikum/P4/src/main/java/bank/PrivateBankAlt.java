package bank;

import bank.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PrivateBankAlt implements Bank {
    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private HashMap<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    public PrivateBankAlt(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    //TODO: copy constructor
    public PrivateBankAlt(PrivateBankAlt otherBank) {
        this.name = otherBank.name;
        this.incomingInterest = otherBank.incomingInterest;
        this.outgoingInterest = otherBank.outgoingInterest;
        // Note: We do not copy the accountsToTransactions map
    }

    //TODO: creating getter and setter for class attributes

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0.0 && incomingInterest <= 1.0) {
            this.incomingInterest = incomingInterest;
        } else {
            throw new IllegalArgumentException("Incoming interest must be between 0 and 1");
        }
    }
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {

        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        }
    }


    public HashMap<String, List<Transaction>> getAccountsToTransactions() {
        return this.accountsToTransactions;
    }

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
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account '" + account + "' already exists.");
        }
        accountsToTransactions.put(account, new ArrayList<>());
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
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
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
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
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
    }

    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        List<Transaction> transactions = accountsToTransactions.get(account);
        if (!transactions.contains(transaction)) {
            throw new TransactionDoesNotExistException("Transaction does not exist in the account.");
        }
        transactions.remove(transaction);
    }



    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if (!accountsToTransactions.containsKey(account)) {
            return false;
        }
        return accountsToTransactions.get(account).contains(transaction);
    }

    @Override
    public double getAccountBalance(String account) throws AccountDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }

        double balance = 0.0;
        for (Transaction transaction : accountsToTransactions.get(account)) {
            double amount = transaction.getAmount();

            if (transaction instanceof Transfer transfer) {
                if (transfer.getSender().equals(account)) {
                    // Outgoing transfer: subtract amount
                    amount = -Math.abs(amount);
                } else if (transfer.getRecipient().equals(account)) {
                    // Incoming transfer: add amount
                    amount = Math.abs(amount);
                } else {
                    // Transfer does not involve the account
                    amount = 0.0;
                }
            } else if (transaction instanceof Payment) {
                // For Payment, use calculate() method
                amount = transaction.calculate();
            }
            balance += amount;
        }
        return balance;
    }

    @Override
    public List<Transaction> getTransactions(String account) throws AccountDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        return new ArrayList<>(accountsToTransactions.get(account));
    }

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


    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) throws AccountDoesNotExistException {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : getTransactions(account)) {
            double amount = (transaction instanceof CalculateBill)
                    ? ((CalculateBill) transaction).calculate()
                    : transaction.getAmount();
            if ((positive && amount >= 0) || (!positive && amount < 0)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    //TODO: equals und toString
    @Override
    public String toString() {
        return "PrivateBankAlt{" +
                "name='" + name + '\'' +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                ", accountsToTransactions=" + accountsToTransactions +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivateBankAlt)) return false;
        PrivateBankAlt that = (PrivateBankAlt) o;
        return Double.compare(that.incomingInterest, incomingInterest) == 0 &&
                Double.compare(that.outgoingInterest, outgoingInterest) == 0 &&
                name.equals(that.name) &&
                accountsToTransactions.equals(that.accountsToTransactions);
    }

}
