
package bank;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.TransactionDoesNotExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrivateBankTest {
    private PrivateBank bank;
    private static final String BANK_NAME = "TestBank";
    private static final String DIRECTORY = "test_accounts";
    private static final double INTEREST = 0.1;
    private static final String ACCOUNT = "TestAccount";
    private Payment payment;
    private Transfer transfer;

    @BeforeEach
    void init() throws IOException {
        bank = new PrivateBank(BANK_NAME, INTEREST, INTEREST, DIRECTORY);
        payment = new Payment("01.01.2023",100.0, "Test Payment",  INTEREST, INTEREST);
        transfer = new Transfer("01.01.2023", "Test Transfer", 100.0, "Sender", "Recipient");
    }

    @AfterEach
    void cleanup() {
        File directory = new File(DIRECTORY);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            directory.delete();
        }
    }

    @Test
    void testConstructor() {
        // assertAll() is used to group multiple assertions together
        assertAll(
                () -> assertEquals(BANK_NAME, bank.getName()),
                () -> assertEquals(INTEREST, bank.getIncomingInterest()),
                () -> assertEquals(INTEREST, bank.getOutgoingInterest())
        );
    }

    @Test
    void testCreateAccount() {
        assertDoesNotThrow(() -> bank.createAccount(ACCOUNT));
        assertThrows(AccountAlreadyExistsException.class, () -> bank.createAccount(ACCOUNT));
    }

    @Test
    void testCreateAccountWithTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(payment);
        transactions.add(transfer);

        assertDoesNotThrow(() -> bank.createAccount(ACCOUNT, transactions));
        assertTrue(bank.containsTransaction(ACCOUNT, payment));
        assertTrue(bank.containsTransaction(ACCOUNT, transfer));
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.0, -50.0})
    void testAddTransaction(double amount) {
        assertDoesNotThrow(() -> {
            bank.createAccount(ACCOUNT);
            Payment testPayment = new Payment("01.01.2023", amount,"Test",  INTEREST, INTEREST);
            bank.addTransaction(ACCOUNT, testPayment);
        });
    }

    @Test
    void testRemoveTransaction() {
        assertDoesNotThrow(() -> {
            bank.createAccount(ACCOUNT);
            bank.addTransaction(ACCOUNT, payment);
            bank.removeTransaction(ACCOUNT, payment);
        });
        assertThrows(TransactionDoesNotExistException.class,
                () -> bank.removeTransaction(ACCOUNT, payment));
    }

    @Test
    void testGetAccountBalance() {
        assertDoesNotThrow(() -> {
            bank.createAccount(ACCOUNT);
            bank.addTransaction(ACCOUNT, payment);
            bank.addTransaction(ACCOUNT, transfer);
            double balance = bank.getAccountBalance(ACCOUNT);
            assertTrue(balance > 0);
        });
    }

    @Test
    void testGetTransactionsSorted() {
        assertDoesNotThrow(() -> {
            bank.createAccount(ACCOUNT);
            bank.addTransaction(ACCOUNT, payment);
            bank.addTransaction(ACCOUNT, transfer);
            List<Transaction> sorted = bank.getTransactionsSorted(ACCOUNT, true);
            assertEquals(2, sorted.size());
        });
    }

    @Test
    void testEquals() throws IOException {
        PrivateBank sameBank = new PrivateBank(BANK_NAME, INTEREST, INTEREST, DIRECTORY);
        PrivateBank differentBank = new PrivateBank("OtherBank", INTEREST, INTEREST, DIRECTORY);

        assertEquals(bank, sameBank);
        assertNotEquals(bank, differentBank);
    }
}