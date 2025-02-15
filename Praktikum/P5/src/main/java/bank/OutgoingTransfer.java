package bank;

public class OutgoingTransfer extends Transfer {
    public OutgoingTransfer(String date, String description, double amount, String sender, String recipient) {
        super(date, description, amount, sender, recipient);
    }

    @Override
    public double calculate() {
        // For outgoing transfers, the amount is subtracted from the account balance
        return -Math.abs(getAmount());
    }
}