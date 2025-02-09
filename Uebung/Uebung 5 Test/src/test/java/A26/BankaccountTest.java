package A26;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankaccountTest {

    Bankaccount myAccount = new Bankaccount("A");
    Bankaccount targetAccount = new Bankaccount("B");


    public void setAccountsUp(){
        myAccount.deposit(100);
        targetAccount.deposit(100);

    }


    @Test


    public void checkTransfer() {
        setAccountsUp();
        assertNotNull(myAccount);
        assertNotNull(targetAccount);
        assertEquals(100, myAccount.getBalance());
        assertEquals(100, targetAccount.getBalance());
        float myAccountBalance = myAccount.getBalance();
        float targetAccountBalance = targetAccount.getBalance();
        int transferMoney = 10;
        myAccount.transfer(targetAccount,transferMoney);
        assertEquals(myAccountBalance - transferMoney, myAccount.getBalance());
        assertEquals(targetAccountBalance + transferMoney, targetAccount.getBalance());




    }
}

