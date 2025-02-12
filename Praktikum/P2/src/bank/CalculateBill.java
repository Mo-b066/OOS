package bank;

/**
 * Schnittstelle zur Berechnung eines Betrags.
 * Diese Schnittstelle definiert eine Methode zur Berechnung eines Werts,
 * wie z.B. den berechneten Betrag nach Abzug von Zinsen oder Gebühren.
 */
public interface CalculateBill {

    /**
     * Berechnet einen Betrag.
     *
     * @return Der berechnete Betrag als double-Wert.
     */
    public double calculate();
}
