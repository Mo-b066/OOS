package bank;

import bank.exceptions.*;

import java.util.*;

public class PrivateBankAlt implements Bank{

    private Map<String, List<Transaction>> accountsToTransactions= new HashMap<>();



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
        if (accountsToTransactions.containsKey(account)) {
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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException{
        if(!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist");
        }
        if(accountsToTransactions.get(account).contains(transaction)) {
            throw new TransactionAlreadyExistException("Transaction already exists");
        }
        accountsToTransactions.get(account).add(transaction);

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
            double balance = 0.0;
            List<Transaction> transactions = accountsToTransactions.get(account);
            for (Transaction t : transactions) {
                // Prüfen, ob es sich um einen Transfer handelt
                if (t instanceof Transfer) {
                    Transfer transfer = (Transfer) t;

                    // Wenn das aktuelle Konto der Sender ist (ausgehender Transfer)
                    if (transfer.getSender().equals(account)) {
                        balance -= Math.abs(transfer.getAmount());
                    }
                    // Wenn das aktuelle Konto der Empfänger ist (eingehender Transfer)
                    else if (transfer.getRecipient().equals(account)) {
                        balance += Math.abs(transfer.getAmount());
                    }
                } else {
                    // Für alle anderen Transaktionstypen (z. B. Payment)
                    balance += t.getAmount();
                }
            }

            return balance;
        }
        return 0.0;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PrivateBankAlt that)) return false;
        return this.accountsToTransactions.equals(that.accountsToTransactions);
    }

}
