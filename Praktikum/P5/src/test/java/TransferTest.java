package bank;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bank.exceptions.InvalidTransactionException;

class TransferTest {
    private Transfer transfer;
    private static final String DATE = "01.01.2023";
    private static final String DESCRIPTION = "Test Transfer";
    private static final double AMOUNT = 100.0;
    private static final String SENDER = "John Doe";
    private static final String RECIPIENT = "Jane Doe";

    @BeforeEach
    void init() {
        transfer = new Transfer(DATE, DESCRIPTION, AMOUNT, SENDER, RECIPIENT);
    }

    @Test
    void testConstructor() {
        assertAll(
                () -> assertEquals(DATE, transfer.getDate()),
                () -> assertEquals(DESCRIPTION, transfer.getDescription()),
                () -> assertEquals(AMOUNT, transfer.getAmount()),
                () -> assertEquals(SENDER, transfer.getSender()),
                () -> assertEquals(RECIPIENT, transfer.getRecipient())
        );
    }

    @Test
    void testCopyConstructor() {
        Transfer copy = new Transfer(transfer);
        assertEquals(transfer, copy);
    }

    @Test
    void testCalculate() {
        assertEquals(AMOUNT, transfer.calculate());

        IncomingTransfer incomingTransfer = new IncomingTransfer(DATE, DESCRIPTION, AMOUNT, SENDER, RECIPIENT);
        assertEquals(AMOUNT, incomingTransfer.calculate());

        OutgoingTransfer outgoingTransfer = new OutgoingTransfer(DATE, DESCRIPTION, AMOUNT, SENDER, RECIPIENT);
        assertEquals(-AMOUNT, outgoingTransfer.calculate());
    }

    @Test
    void testInvalidAmount() {
        assertThrows(InvalidTransactionException.class,
                () -> new Transfer(DATE, DESCRIPTION, -100.0, SENDER, RECIPIENT));
    }

    @Test
    void testEquals() {
        Transfer sameTransfer = new Transfer(DATE, DESCRIPTION, AMOUNT, SENDER, RECIPIENT);
        Transfer differentTransfer = new Transfer(DATE, DESCRIPTION, AMOUNT, "Different", RECIPIENT);

        assertTrue(transfer.equals(sameTransfer));
        assertFalse(transfer.equals(differentTransfer));
    }

    @Test
    void testToString() {
        String expected = "Transaction{date='" + DATE + "', description='" + DESCRIPTION +
                "', amount=" + AMOUNT + "}{" + " sender='" + SENDER +
                "', recipient='" + RECIPIENT + "'}";
        assertEquals(expected, transfer.toString());
    }
}