package bank;

import bank.exceptions.*;

import java.util.*;

public class PrivateBank implements Bank{
    private String name="";
    private double incomingInterest=0;
    private double outgoingInterest=0;
    private Map<String, List<Transaction>> accountsToTransactions= new HashMap<>();

    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }
    public PrivateBank(PrivateBank privateBank) {
        setName(privateBank.getName());
        setIncomingInterest(privateBank.getIncomingInterest());
        setOutgoingInterest(privateBank.getOutgoingInterest());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name= name;
    }
    public double getIncomingInterest() {
        if(incomingInterest < 0 || incomingInterest > 1) {
            System.out.println("Invalid interest rate");
        }
        return incomingInterest;
    }
    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest= incomingInterest;
    }
    public double getOutgoingInterest() {
        if(outgoingInterest < 0 || outgoingInterest > 1) {
            System.out.println("Invalid interest rate");
        }
        return outgoingInterest;
    }
    public void setOutgoingInterest(double outgoingInterest) {
        this.outgoingInterest= outgoingInterest;
    }

    @Override
    public String toString() {
        return "PrivateBank{" +
                "name='" + name + '\'' +
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

    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account already exists");
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
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        for (Transaction transaction : transactions) {
            if (accountsToTransactions.get(account).contains(transaction)) {
                throw new TransactionAlreadyExistException("Transaction already exists");
            }
        }
        accountsToTransactions.put(account, new ArrayList<>(transactions));

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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        if(!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist");
        }
        if(accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionAlreadyExistException("Transaction already exists");
        }
        accountsToTransactions.get(account).add(transaction);


    }
    /**
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
     }**/

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
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if(!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist");
        }
        if(!accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionDoesNotExistException("Transaction not found");
        }
        accountsToTransactions.get(account).remove(transaction);

    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if(accountsToTransactions.get(account).contains(transaction)) {
            return accountsToTransactions.get(account).contains(transaction);
        }
        return false;
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) {
        if(accountsToTransactions.containsKey(account)) {
            double balance = 0;
            for (Transaction transaction : accountsToTransactions.get(account)) {
                balance += transaction.calculate();
            }
            return balance;
        }
        return 0;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        if(accountsToTransactions.containsKey(account)) {
            return accountsToTransactions.get(account);
        }
        return List.of();
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
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        if(accountsToTransactions.containsKey(account)) {
            List<Transaction> transactions = new ArrayList<>(accountsToTransactions.get(account));
            transactions.sort(Comparator.comparingDouble(Transaction::calculate));
            if(!asc) {
                Collections.reverse(transactions);
            }
            return transactions;
        }
        return List.of();
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
        if(accountsToTransactions.containsKey(account)) {
            List<Transaction> transactions = new ArrayList<>();
            for (Transaction transaction : accountsToTransactions.get(account)) {
                if(positive && transaction.calculate() > 0) {
                    transactions.add(transaction);
                } else if(!positive && transaction.calculate() < 0) {
                    transactions.add(transaction);
                }
            }
            return transactions;
        }
        return List.of();
    }
}
