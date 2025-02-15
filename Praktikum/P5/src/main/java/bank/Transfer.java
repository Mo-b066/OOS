package bank;
import bank.exceptions.*;
/**
 * The Transfer class represents a financial transaction that involves transferring money
 * from one party (sender) to another (recipient).
 */
public class Transfer extends Transaction implements CalculateBill {
    private String sender;
    private String recipient;

    /**
     * Constructs a new Transfer with the specified details.
     *
     * @param date        the date of the transaction
     * @param description the description of the transaction
     * @param amount      the amount of the transaction
     * @param sender      the sender of the transfer
     * @param recipient   the recipient of the transfer
     * @throws IllegalArgumentException if sender or recipient is null or empty
     */
    public Transfer(String date, String description, double amount, String sender, String recipient) throws InvalidTransactionException {
        super(date, description, amount);

        if (amount <= 0) {
            throw new InvalidTransactionException("Amount must be positive.");
        }

        setSender(sender);
        setRecipient(recipient);
    }
    /**
     * Constructs a new Transfer by copying another Transfer.
     *
     * @param transfer the Transfer to copy
     * @throws NullPointerException if the transfer is null
     */
    public Transfer(Transfer transfer) {
        super(transfer.getDate(), transfer.getDescription(), transfer.getAmount());
        setSender(transfer.getSender());
        setRecipient(transfer.getRecipient());
    }

    /**
     * Returns the recipient of the transfer.
     *
     * @return the recipient of the transfer
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets the recipient of the transfer.
     *
     * @param recipient the recipient to set
     * @throws IllegalArgumentException if the recipient is null or empty
     */
    public void setRecipient(String recipient) {
        if (recipient == null || recipient.isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be null or empty");
        }
        this.recipient = recipient;
    }

    /**
     * Returns the sender of the transfer.
     *
     * @return the sender of the transfer
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender of the transfer.
     *
     * @param sender the sender to set
     * @throws IllegalArgumentException if the sender is null or empty
     */
    public void setSender(String sender) {
        if (sender == null || sender.isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty");
        }
        this.sender = sender;
    }

    /**
     * Calculates the amount of the transfer.
     *
     * @return the amount of the transfer
     */
    @Override
    public double calculate() {
        return getAmount();
    }

    /**
     * Returns a string representation of the Transfer.
     *
     * @return a string representation of the Transfer
     */
    @Override
    public String toString() {
        return super.toString() + "{" +
                " sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }

    /**
     * Compares this Transfer to another object for equality.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transfer transfer = (Transfer) o;
        return sender.equals(transfer.sender) && recipient.equals(transfer.recipient);
    }
}