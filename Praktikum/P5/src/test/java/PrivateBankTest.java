import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import bank.Payment;
import bank.PrivateBank;
import bank.Transaction;
import bank.TransactionSerializer;
import bank.Transfer;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.TransactionDoesNotExistException;

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
        payment = new Payment("01.01.2023", "Test Payment", 100.0, INTEREST, INTEREST);
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
    @ValueSource(doubles = {100.0, 50.0})
    void testAddTransaction(double amount) {
        assertDoesNotThrow(() -> {
            bank.createAccount(ACCOUNT);
            Payment testPayment = new Payment("01.01.2023", "Test", amount, INTEREST, INTEREST);
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

    @Test
    void testCreateAccountWritesToFile() throws IOException {
        String testAccount = "TestAccountFile";
        
        // Create account
        bank.createAccount(testAccount);
        
        // Verify file exists
        File accountFile = new File(DIRECTORY, testAccount + ".json");
        assertTrue(accountFile.exists(), "Account file should be created");
        
        // Read file content to verify
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Transaction.class, new TransactionSerializer())
            .setPrettyPrinting()
            .create();
            
        try (Reader reader = new FileReader(accountFile)) {
            Type listType = new TypeToken<List<Transaction>>(){}.getType();
            List<Transaction> transactions = gson.fromJson(reader, listType);
            assertNotNull(transactions);
            assertTrue(transactions.isEmpty());
        }
    }
@Test
void testAddTransactionWritesToFile() throws IOException {
    String testAccount = "TestAccountWithTransaction";
    Payment testPayment = new Payment("01.01.2023", "Test Payment", 100.0, INTEREST, INTEREST);
    
    // Create account and add transaction
    bank.createAccount(testAccount);
    bank.addTransaction(testAccount, testPayment);
    
    // Verify file exists
    File accountFile = new File(DIRECTORY, testAccount + ".json");
    assertTrue(accountFile.exists(), "File should exist");
    
    // Read and verify content
    try (Reader reader = new FileReader(accountFile)) {
        JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
        
        // Verify array structure
        assertEquals(1, array.size(), "Should have one transaction");
        
        // Verify transaction object structure
        JsonObject transactionObj = array.get(0).getAsJsonObject();
        assertTrue(transactionObj.has("CLASSNAME"), "Should have CLASSNAME field");
        assertEquals("Payment", transactionObj.get("CLASSNAME").getAsString());
        
        // Verify INSTANCE structure
        assertTrue(transactionObj.has("INSTANCE"), "Should have INSTANCE field");
        JsonObject instance = transactionObj.get("INSTANCE").getAsJsonObject();
        assertEquals("01.01.2023", instance.get("date").getAsString());
        assertEquals("Test Payment", instance.get("description").getAsString());
        assertEquals(100.0, instance.get("amount").getAsDouble());
        assertEquals(INTEREST, instance.get("incomingInterest").getAsDouble());
        assertEquals(INTEREST, instance.get("outgoingInterest").getAsDouble());
    }
}



}