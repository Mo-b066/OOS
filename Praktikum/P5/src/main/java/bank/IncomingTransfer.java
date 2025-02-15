package bank;

public class IncomingTransfer extends Transfer {
    public IncomingTransfer(String date, String description, double amount, String sender, String recipient) {
        super(date, description, amount, sender, recipient);
    }

    @Override
    public double calculate() {
        // For incoming transfers, the amount is added to the account balance
        return Math.abs(getAmount());
    }
}