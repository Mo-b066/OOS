package bank;

import bank.exceptions.AccountException;

import java.util.Objects;

public class Transfer extends Transaction implements CalculateBill {


    private String sender;
    private String recipient;

    public Transfer (String date, double amount, String description) {
        super(date, amount, description);

    }
    public Transfer (String date, double amount, String description, String sender, String recipient) {
        super(date, amount, description);
        setSender(sender);
        setRecipient(recipient);
    }
    public Transfer(Transfer transfer) {
        super(transfer.getDate(), transfer.getAmount(), transfer.getDescription());
        setSender(transfer.getSender());
        setRecipient(transfer.getRecipient());
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) throws AccountException {
        if (amount < 0) {
            throw new AccountException("Amount must be positive");
        }
        this.amount= amount;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender= sender;
    }
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient= recipient;
    }

    @Override
    public double calculate() {
        return getAmount();
    }

    @Override
    public String toString() {
        return super.toString() + ", Sender: " + sender + ", Recipient: " + recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transfer transfer = (Transfer) o;
        return super.equals(o) && Objects.equals(sender, transfer.sender) && Objects.equals(recipient, transfer.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sender, recipient);
    }
}
