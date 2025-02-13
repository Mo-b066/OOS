package bank;

/**
 * Die Klasse IncomingTransfer stellt eine eingehende Überweisung dar und erweitert die Klasse Transfer.
 */
public class IncomingTransfer extends Transfer {

    /**
     * Konstruktor für IncomingTransfer.
     *
     * @param date        Das Datum der Überweisung.
     * @param amount      Der Betrag der Überweisung (sollte für eingehende Überweisungen positiv sein).
     * @param description Die Beschreibung der Überweisung.
     * @param sender      Der Absender der Überweisung.
     * @param recipient   Der Empfänger der Überweisung.
     */
    public IncomingTransfer(String date, String description, String sender, String recipient, double amount) {
        super(date, amount > 0 ? amount : -amount, description, sender, recipient); // Amount positiv
    }

    /**
     * Kopierkonstruktor für IncomingTransfer.
     *
     * @param transfer Das IncomingTransfer-Objekt, das kopiert wird.
     */
    public IncomingTransfer(IncomingTransfer transfer) {
        super(transfer);
    }

    @Override
    public double calculate() {
        // Eingehender Transfer, daher positiver Betrag
        return Math.abs(getAmount());
    }
    /**
     * Gibt eine textuelle Darstellung des IncomingTransfer-Objekts zurück.
     *
     * @return Eine Zeichenkette, die das IncomingTransfer-Objekt beschreibt.
     */
    @Override
    public String toString() {
        return super.toString() + " [Incoming Transfer]";
    }
}