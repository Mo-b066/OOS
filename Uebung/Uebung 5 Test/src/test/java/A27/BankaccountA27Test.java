package A27;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class BankaccountA27Test {

    BankaccountA27 myAccount = new BankaccountA27("A");
    BankaccountA27 targetAccount = new BankaccountA27("B");


    @BeforeEach
    public void transferTest1(){
        myAccount.deposit(100);
        targetAccount.deposit(100);
        long transfer = ThreadLocalRandom.current().nextInt(0,50);
    }
    /*
    @RepeatedTest(10)
    public void transferTest2(){
        float myAccountBalance = myAccount.getBalance();
        float targetAccountBalance = targetAccount.getBalance();
        myAccount.transfer(targetAccount, transfer );
        assertEquals(myAccountBalance - transfer, myAccount.getBalance());
        assertEquals(targetAccountBalance + transfer, targetAccount.getBalance());

    }*/


}