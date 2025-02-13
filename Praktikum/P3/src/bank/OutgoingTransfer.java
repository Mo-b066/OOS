package bank;

public class OutgoingTransfer extends Transfer {

    /**
     * Constructor for OutgoingTransfer.
     *
     * @param date        The date of the transfer
     * @param amount      The amount of the transfer (should be negative for outgoing)
     * @param description The description of the transfer
     * @param sender      The sender of the transfer
     * @param recipient   The recipient of the transfer
     */
    public OutgoingTransfer(String date, String description, String sender, String recipient, double amount) {
        super(date, amount < 0 ? amount : -amount, description, sender, recipient);
    }

    @Override
    public double calculate() {
        // Outgoing transfer, therefore negative amount
        return -Math.abs(getAmount());
    }




    /**
     * Kopie konstruktor
     *
     * @param transfer Outgoing ist kopiert
     */
    public OutgoingTransfer(OutgoingTransfer transfer) {
        super(transfer);
    }

    @Override
    public String toString() {
        return super.toString() + " [Outgoing Transfer]";
    }
}