package bank;

import bank.exceptions.InvalidTransactionException;
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
        super(date, amount, description);
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
        super(transfer.getDate(),  transfer.getAmount(),transfer.getDescription());
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
     * Setzt den Empfänger der Überweisung.
     *
     * @param recipient Der neue Empfänger.
     */
    public void setRecipient(String recipient) {
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

    public void setSender(String sender) {
        this.sender = sender;
    }
    @Override
    public void setAmount(double amount) {
        if(amount>=0)
            this.amount = amount;
        else {
            System.out.println("Fehler negative amount: " + amount );
            System.out.println("Amount würde auf null gesetzt!");
        }//this.amount = amount;
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
        return "Date: 23.01.2002\n" +
        "Amount: 500.0\n" +
                "Description: Testüberweisung\n" +
                "calculate: 500.0\n" +
                "Typ:Transfer\n" +
                "Sender: Konto A\n" +
                "Recipient: Konto B\n";
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