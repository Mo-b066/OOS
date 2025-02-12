package bank;

import java.util.Objects;

/**
 * Repräsentiert eine Geldüberweisung.
 * Diese Klasse erweitert die {@link Transaction}-Klasse und implementiert das {@link CalculateBill}-Interface.
 */
public class Transfer extends Transaction  {

    private String sender;
    private String recipient;

    /**
     * Konstruktor zur Erstellung einer neuen Transfer-Instanz.
     *
     * @param date        Das Datum der Überweisung.
     * @param description Die Beschreibung der Überweisung.
     * @param amount      Der Betrag der Überweisung.
     * @param sender      Der Absender der Überweisung.
     * @param recipient   Der Empfänger der Überweisung.
     */
    public Transfer(String date, String description, double amount, String sender, String recipient) {
        super(date, description, amount);
        setSender(sender);
        setRecipient(recipient);
    }

    /**
     * Kopierkonstruktor zum Erstellen einer neuen Transfer-Instanz aus einer vorhandenen.
     *
     * @param transfer Die zu kopierende Transfer-Instanz.
     */
    public Transfer(Transfer transfer) {
        super(transfer.getDate(), transfer.getDescription(), transfer.getAmount());
        setSender(transfer.getSender());
        setRecipient(transfer.getRecipient());
    }

    /**
     * Gibt den Absender der Überweisung zurück.
     *
     * @return Der Absender der Überweisung.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Setzt den Absender der Überweisung.
     *
     * @param sender Der Absender der Überweisung.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gibt den Empfänger der Überweisung zurück.
     *
     * @return Der Empfänger der Überweisung.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Setzt den Empfänger der Überweisung.
     *
     * @param recipient Der Empfänger der ��berweisung.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setAmount(double amount) {
        if(amount < 0) {
            System.out.println("Der Betrag muss größer als 0 sein.");
            return;
        }
        this.amount = amount;
    }

    /**
     * Berechnet den Betrag der Überweisung.
     *
     * @return Der Betrag der Überweisung.
     */
    @Override
    public double calculate() {
        return amount;
    }

    /**
     * Gibt eine Zeichenketten-Darstellung der Überweisung zurück.
     *
     * @return Eine Zeichenkette mit den Details der Überweisung.
     */
    @Override
    public String toString() {
        return super.toString() + '\'' +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }

    /**
     * Vergleicht diese Überweisung mit einem anderen Objekt auf Gleichheit.
     *
     * @param o Das zu vergleichende Objekt.
     * @return true, wenn das gegebene Objekt gleich dieser Überweisung ist; false, wenn nicht.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transfer transfer = (Transfer) o;
        return super.equals(o) && Objects.equals(sender, transfer.sender) &&
                Objects.equals(recipient, transfer.recipient);
    }
}