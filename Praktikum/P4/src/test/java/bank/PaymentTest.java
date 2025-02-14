
package bank;

import bank.exceptions.InvalidTransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Payment payment;
    private static final String DATE = "01.01.2023";
    private static final String DESCRIPTION = "Test Payment";
    private static final double AMOUNT = 100.0;
    private static final double INCOMING_INTEREST = 0.1;
    private static final double OUTGOING_INTEREST = 0.2;

    //vor jedem Test aufgerufen
    @BeforeEach
    void init() {
        payment = new Payment(DATE,  AMOUNT,DESCRIPTION, INCOMING_INTEREST, OUTGOING_INTEREST);
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
    @ValueSource(doubles = {-1.0, 1.5})
    void testInvalidInterestRates(double interest) {
        assertThrows(InvalidTransactionException.class,
                () -> new Payment(DATE, AMOUNT, DESCRIPTION,interest, OUTGOING_INTEREST));
        assertThrows(InvalidTransactionException.class,
                () -> new Payment(DATE,  AMOUNT,DESCRIPTION, INCOMING_INTEREST, interest));
    }

    @Test
    void testCalculatePositiveAmount() {
        payment.setAmount(100.0);
        assertEquals(90.0, payment.calculate()); // 100 - (100 * 0.1)
    }



    @Test
    void testEquals() {
        Payment samePayment = new Payment(DATE,AMOUNT, DESCRIPTION,  INCOMING_INTEREST, OUTGOING_INTEREST);
        Payment differentPayment = new Payment(DATE,  AMOUNT,"Different", INCOMING_INTEREST, OUTGOING_INTEREST);

        assertEquals(payment, samePayment);
        assertNotEquals(payment, differentPayment);
    }


}