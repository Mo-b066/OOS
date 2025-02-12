package bank;

import java.util.Objects;

/**
 * Repräsentiert eine Zahlungstransaktion.
 * Diese Klasse erweitert die {@link Transaction}-Klasse und implementiert das {@link CalculateBill}-Interface.
 */
public class Payment extends Transaction  {

    private double incomingInterest = 0;  // Zinsen bei Einzahlung
    private double outgoingInterest = 0;  // Zinsen bei Auszahlung

    /**
     * Konstruktor zur Erstellung einer neuen Payment-Instanz.
     *
     * @param date             Das Datum der Zahlung.
     * @param amount           Der Betrag der Zahlung.
     * @param description      Die Beschreibung der Zahlung.
     * @param incomingInterest Der Zinssatz bei Einzahlung (zwischen 0 und 1).
     * @param outgoingInterest Der Zinssatz bei Auszahlung (zwischen 0 und 1).
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        super(date, description, amount);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     * Kopierkonstruktor zum Erstellen einer neuen Payment-Instanz aus einer vorhandenen.
     *
     * @param payment Die zu kopierende Payment-Instanz.
     */
    public Payment(Payment payment) {
        super(payment.getDate(), payment.getDescription(), payment.getAmount());
        setIncomingInterest(payment.getIncomingInterest());
        setOutgoingInterest(payment.getOutgoingInterest());
    }


    /**
     * Gibt den Zinssatz bei Einzahlung zurück.
     *
     * @return Der Zinssatz bei Einzahlung.
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Setzt den Zinssatz bei Einzahlung.
     * Der Wert muss zwischen 0 und 1 liegen. Bei fehlerhaften Eingaben wird der Wert auf 0 gesetzt.
     *
     * @param incomingInterest Der Zinssatz bei Einzahlung.
     */
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            System.out.println("Eine fehlerhafte Eingabe: Prozentwert muss zwischen 0 und 1 sein (IncomingInterest)");

        }
    }

    /**
     * Gibt den Zinssatz bei Auszahlung zurück.
     *
     * @return Der Zinssatz bei Auszahlung.
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Setzt den Zinssatz bei Auszahlung.
     * Der Wert muss zwischen 0 und 1 liegen. Bei fehlerhaften Eingaben wird der Wert auf 0 gesetzt.
     *
     * @param outgoingInterest Der Zinssatz bei Auszahlung.
     */
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            System.out.println("Eine fehlerhafte Eingabe: Prozentwert muss zwischen 0 und 1 sein (OutgoingInterest)");

        }
    }


    /**
     * Berechnet den endgültigen Betrag nach Abzug der entsprechenden Zinsen.
     *
     * @return Der berechnete Betrag nach Abzug der Zinsen.
     */
    @Override
    public double calculate() {
        if (amount > 0) {
            return getAmount() * (1 - getIncomingInterest());
        } else if (amount < 0) {
            return getAmount() * (1 + getOutgoingInterest());
        }
        return 0.0;
    }

    /**
     * Gibt eine Zeichenketten-Darstellung der Zahlung zurück.
     *
     * @return Eine Zeichenkette mit den Details der Zahlung.
     */
    @Override
    public String toString() {
        return super.toString() + '\'' +
                "incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                '}';
    }

    /**
     * Vergleicht diese Zahlung mit einem anderen Objekt auf Gleichheit.
     *
     * @param o Das zu vergleichende Objekt.
     * @return true, wenn das gegebene Objekt gleich dieser Zahlung ist; false, wenn nicht.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return super.equals(o) && Double.compare(incomingInterest, payment.incomingInterest) == 0 && Double.compare(outgoingInterest, payment.outgoingInterest) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomingInterest, outgoingInterest);
    }
}
