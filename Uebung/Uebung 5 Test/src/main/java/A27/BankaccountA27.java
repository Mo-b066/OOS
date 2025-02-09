package A27;

import java.util.concurrent.ThreadLocalRandom;

public class BankaccountA27 {
    private float balance;
    private String name;
    private final long bankAccId;
    public BankaccountA27(String name) {
        this.name = name;
        this.bankAccId = ThreadLocalRandom.current().nextLong(0,
                Long.MAX_VALUE);
    }
    public void deposit (float amount){
        balance += amount;
    }
    public void withdraw (float amount){
        balance -= amount;
    }
    public float getBalance (){
        return balance;
    }
    public long getBankAccId(){
        return bankAccId;
    }

    public void transfer(A26.Bankaccount targetAccount, float amount){
        withdraw(amount);
        targetAccount.deposit(amount);
    }
}