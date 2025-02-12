package bank;

import java.util.Objects;

/**
 * Repräsentiert eine allgemeine Transaktion.
 */
public abstract class Transaction implements CalculateBill {
    protected String date;
    protected String description;
    protected double amount;

    /**
     * Konstruktor zur Erstellung einer neuen Transaktionsinstanz.
     *
     * @param date        Das Datum der Transaktion.
     * @param description Die Beschreibung der Transaktion.
     * @param amount      Der Betrag der Transaktion.
     */
    public Transaction(String date, String description, double amount) {
        this.date = date;
        this.description = description;
        setAmount(amount);
    }

    /**
     * Gibt das Datum der Transaktion zurück.
     *
     * @return Das Datum der Transaktion.
     */
    public String getDate() {
        return date;
    }

    /**
     * Setzt das Datum der Transaktion.
     *
     * @param date Das Datum der Transaktion.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gibt die Beschreibung der Transaktion zurück.
     *
     * @return Die Beschreibung der Transaktion.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung der Transaktion.
     *
     * @param description Die Beschreibung der Transaktion.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt den Betrag der Transaktion zurück.
     *
     * @return Der Betrag der Transaktion.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setzt den Betrag der Transaktion.
     *
     * @param amount Der Betrag der Transaktion.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gibt eine Zeichenketten-Darstellung der Transaktion zurück.
     *
     * @return Eine Zeichenkette mit den Details der Transaktion.
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + calculate() +
                '}';
    }

    /**
     * Vergleicht diese Transaktion mit einem anderen Objekt auf Gleichheit.
     *
     * @param o Das zu vergleichende Objekt.
     * @return true, wenn das gegebene Objekt gleich dieser Transaktion ist; false, wenn nicht.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description);
    }
}