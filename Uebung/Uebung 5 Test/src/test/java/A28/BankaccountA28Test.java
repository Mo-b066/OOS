package A28;

import A27.BankaccountA27;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankaccountA28Test {
    BankaccountA28 myAccount = new BankaccountA28("A");
    BankaccountA28 targetAccount = new BankaccountA28("B");

    public void assertAccounts(){
        myAccount.deposit(100);
        targetAccount.deposit(100);
    }

    @Test
    public void exceptionTest(){
        assertAccounts();
       float myAccountBalance = myAccount.getBalance();
       float targetAccountBalance = targetAccount.getBalance();
       int transfer = 10;
       myAccount.transfer(targetAccount, transfer );
       assertEquals(myAccountBalance - transfer, myAccount.getBalance());
       assertEquals(targetAccountBalance + transfer, targetAccount.getBalance());

    }

    @Test
    public void shouldThrowNegativeDepositException(){
        Exception negativeDepositException = assertThrows(DepositNegativeException.class, ()->myAccount.transfer(targetAccount,1000));
    }


}