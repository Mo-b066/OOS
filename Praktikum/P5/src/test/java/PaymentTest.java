
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import bank.Payment;
import bank.exceptions.InvalidTransactionException;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Payment payment;
    private static final String DATE = "01.01.2024";
    private static final String DESCRIPTION = "Test Payment";
    private static final double AMOUNT = 1000.0;
    private static final double INCOMING_INTEREST = 0.05;
    private static final double OUTGOING_INTEREST = 0.1;

    @BeforeEach
    void setUp() {
        payment = new Payment(DATE, DESCRIPTION, AMOUNT, INCOMING_INTEREST, OUTGOING_INTEREST);
    }

    @Test
    void testConstructor() {
        assertAll(
                () -> assertEquals(DATE, payment.getDate()),
                () -> assertEquals(DESCRIPTION, payment.getDescription()),
                () -> assertEquals(AMOUNT, payment.getAmount()),
                () -> assertEquals(INCOMING_INTEREST, payment.getIncomingInterest()),
                () -> assertEquals(OUTGOING_INTEREST, payment.getOutgoingInterest())
        );
    }

    @Test
    void testCopyConstructor() {
        Payment copy = new Payment(payment);
        assertEquals(payment, copy);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1000.0, 500.0, 100.0})
    void testCalculatePositiveAmount(double amount) {
        Payment p = new Payment(DATE, DESCRIPTION, amount, INCOMING_INTEREST, OUTGOING_INTEREST);
        double expected = amount - (amount * INCOMING_INTEREST);
        assertEquals(expected, p.calculate());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1000.0, -500.0, -100.0})
    void testCalculateNegativeAmount(double amount) {
        assertThrows(InvalidTransactionException.class, () -> {
            new Payment(DATE, DESCRIPTION, amount, INCOMING_INTEREST, OUTGOING_INTEREST);
        });
    }

    @Test
    void testInvalidInterestRates() {
        assertAll(
                () -> assertThrows(InvalidTransactionException.class,
                        () -> new Payment(DATE, DESCRIPTION, AMOUNT, -0.1, OUTGOING_INTEREST)),
                () -> assertThrows(InvalidTransactionException.class,
                        () -> new Payment(DATE, DESCRIPTION, AMOUNT, 1.1, OUTGOING_INTEREST)),
                () -> assertThrows(InvalidTransactionException.class,
                        () -> new Payment(DATE, DESCRIPTION, AMOUNT, INCOMING_INTEREST, -0.1)),
                () -> assertThrows(InvalidTransactionException.class,
                        () -> new Payment(DATE, DESCRIPTION, AMOUNT, INCOMING_INTEREST, 1.1))
        );
    }

    @Test
    void testEquals() {
        Payment same = new Payment(DATE, DESCRIPTION, AMOUNT, INCOMING_INTEREST, OUTGOING_INTEREST);
        Payment different = new Payment(DATE, "Different", AMOUNT, INCOMING_INTEREST, OUTGOING_INTEREST);

        assertAll(
                () -> assertEquals(payment, same),
                () -> assertNotEquals(payment, different),
                () -> assertNotEquals(null, payment),
                () -> assertNotEquals(new Object(), payment)
        );
    }

    @Test
    void testToString() {
        String expected = String.format("Transaction{date='%s', description='%s', amount=%f}" +
                        "{incoming interest='%f', outgoing interest='%f'}",
                DATE, DESCRIPTION, AMOUNT, INCOMING_INTEREST, OUTGOING_INTEREST);
        assertEquals(expected, payment.toString());
    }
}