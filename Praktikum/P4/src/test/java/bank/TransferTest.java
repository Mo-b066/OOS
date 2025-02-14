package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Testklasse für die Klasse Transfer.
 * Enthält Unit-Tests zur Überprüfung der Funktionalität der Methoden der Klasse Transfer.
 */
class TransferTest {
    // Instanz der Klasse Transfer
    private Transfer t;

    /**
     * Richtet die Testumgebung vor jedem Test ein.
     * Initialisiert eine Transfer-Instanz mit Testdaten.
     */
    @BeforeEach
    void set() {
        t = new Transfer("23.01.2002",  "Testüberweisung",500.0, "Konto A", "Konto B");
    }

    /**
     * Testet den Konstruktor der Klasse Transfer, um sicherzustellen, dass die Attribute korrekt initialisiert werden.
     */
    @Test
    void testKonstruktor() {
        assertEquals("23.01.2002", t.getDate());
        assertEquals(500.0, t.getAmount());
        assertEquals("Testüberweisung", t.getDescription());
        assertEquals("Konto A", t.getSender());
        assertEquals("Konto B", t.getRecipient());
    }

    /**
     * Testet den Kopierkonstruktor der Klasse Transfer, um sicherzustellen, dass eine identische Kopie einer bestehenden Transfer-Instanz erstellt wird.
     */
    @Test
    void testCpyKonstruktor() {
        Transfer copy = new Transfer(t);
        assertEquals(t, copy);
    }


    /**
     * Testet die Methode calculate, um sicherzustellen, dass der korrekte Betrag berechnet wird.
     *
     * @param amount Der Betrag, der berechnet werden soll.
     */
    @ParameterizedTest
    @ValueSource(doubles = {500.0, 1000.0, 0.0})
    void testCalculate(double amount) {
        t.setAmount(amount);
        assertEquals(amount, t.calculate());
    }

    /**
     * Testet die Methode setAmount, um sicherzustellen, dass ein positiver Betrag für den IncomingTransfer korrekt gesetzt wird.
     */
    @Test
    void testSetAmountPositiveForIncomingTransfer() {
        t.setAmount(1000.0);
        assertEquals(1000.0, t.getAmount());
    }

    /**
     * Testet die Methode setAmount, um sicherzustellen, dass ein negativer Betrag für den OutgoiningTransfer nicht gesetzt wird.
     * Stattdessen sollte der ursprüngliche Betrag unverändert bleiben.
     */
    @Test
    void testSetAmountNegativeForOutgoingTransfer() {
        t.setAmount(-500.0);
        assertNotEquals(-500.0, t.getAmount());
        assertEquals(500.0, t.getAmount(), "Amount sollte bei ungültiger Eingabe unverändert bleiben.");
    }

    /**
     * Testet die Methoden equals, um sicherzustellen, dass zwei Transfer-Instanzen mit denselben Attributen als gleich angesehen werden.
     */
    @Test
    void testEquals() {
        Transfer sameTransfer = new Transfer("23.01.2002", "Testüberweisung",500.0,  "Konto A", "Konto B");
        Transfer differentSender = new Transfer("23.01.2002","Testüberweisung", 500.0,  "Konto C", "Konto B");
        Transfer differentRecipient = new Transfer("23.01.2002", "Testüberweisung", 500.0, "Konto A", "Konto D");

        assertEquals(t, sameTransfer);
        assertNotEquals(t, differentSender);
        assertNotEquals(t, differentRecipient);
    }

    /**
     * Testet die Methode toString, um sicherzustellen, dass sie eine korrekt formatierte String-Darstellung der Transfer-Instanz zurückgibt.
     */
    @Test
    void testToString() {
        String expected = "Date: 23.01.2002\n" +
                "Amount: 500.0\n" +
                "Description: Testüberweisung\n" +
                "calculate: 500.0\n" +
                "Typ:Transfer\n" +
                "Sender: Konto A\n" +
                "Recipient: Konto B\n";

        assertEquals(expected, t.toString());
    }
}
