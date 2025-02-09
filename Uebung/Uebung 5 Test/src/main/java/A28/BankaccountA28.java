package A28;

import java.util.concurrent.ThreadLocalRandom;

public class BankaccountA28 {
    private float balance;
    private String name;
    private final long bankAccId;
    public BankaccountA28(String name) {
        this.name = name;
        this.bankAccId = ThreadLocalRandom.current().nextLong(0,
                Long.MAX_VALUE);
    }
    public void deposit (float amount){

        balance += amount;
    }
    public void withdraw (float amount) {

        balance -= amount;
    }
    public float getBalance (){
        return balance;
    }
    public long getBankAccId(){
        return bankAccId;
    }

    public void transfer(BankaccountA28 targetAccount, float amount) throws DepositNegativeException{
        if (balance-amount<0){
            throw new DepositNegativeException("Leider ist nicht genügend Kontodeckung vorhanden.");
        }
        withdraw(amount);
        targetAccount.deposit(amount);
    }
}