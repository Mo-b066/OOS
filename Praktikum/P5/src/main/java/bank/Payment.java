package bank;
import bank.exceptions.InvalidTransactionException;
/**
 * The Payment class represents a financial transaction that includes interest calculations
 * for both incoming (deposit) and outgoing (withdrawal) payments.
 */
public class Payment extends Transaction implements CalculateBill {
    private double incomingInterest;
    private double outgoingInterest;

    /**
     * Constructs a new Payment with the specified details.
     *
     * @param date             the date of the transaction
     * @param description      the description of the transaction
     * @param amount           the amount of the transaction
     * @param incomingInterest the interest rate for incoming payments (deposits)
     * @param outgoingInterest the interest rate for outgoing payments (withdrawals)
     * @throws IllegalArgumentException if the interest rates are not between 0 and 1
     */
    public Payment(String date, String description, double amount, double incomingInterest, double outgoingInterest) 
            throws InvalidTransactionException {
        super(date, description, amount);
        
        if (amount <= 0) {
            throw new InvalidTransactionException("Amount must be positive");
        }
        
        if (incomingInterest <= 0 || incomingInterest >= 1) {
            throw new InvalidTransactionException("Incoming interest rate must be between 0 and 1");
        }
        
        if (outgoingInterest <= 0 || outgoingInterest >= 1) {
            throw new InvalidTransactionException("Outgoing interest rate must be between 0 and 1");
        }

        this.incomingInterest = incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Constructs a new Payment by copying another Payment.
     *
     * @param payment the Payment to copy
     * @throws NullPointerException if the payment is null
     */
    public Payment(Payment payment) {
        super(payment.getDate(), payment.getDescription(), payment.getAmount());
        this.incomingInterest = payment.getIncomingInterest();
        this.outgoingInterest = payment.getOutgoingInterest();
    }

    /**
     * Returns the incoming interest rate.
     *
     * @return the incoming interest rate
     */
    public double getIncomingInterest() {
        return this.incomingInterest;
    }

    /**
     * Returns the outgoing interest rate.
     *
     * @return the outgoing interest rate
     */
    public double getOutgoingInterest() {
        return this.outgoingInterest;
    }

    /**
     * Sets the incoming interest rate.
     *
     * @param incomingInterest the incoming interest rate to set
     * @throws IllegalArgumentException if the interest rate is not between 0 and 1
     */
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest > 0 && incomingInterest < 1) {
            this.incomingInterest = incomingInterest;
        } else {
            throw new IllegalArgumentException("Incoming interest must be between 0 and 1");
        }
    }

    /**
     * Sets the outgoing interest rate.
     *
     * @param outgoingInterest the outgoing interest rate to set
     * @throws IllegalArgumentException if the interest rate is not between 0 and 1
     */
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest > 0 && outgoingInterest < 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            throw new IllegalArgumentException("Outgoing interest must be between 0 and 1");
        }
    }

    /**
     * Calculates the final amount after applying the appropriate interest rate.
     * If the amount is positive (deposit), the incoming interest is subtracted.
     * If the amount is negative (withdrawal), the outgoing interest is added.
     *
     * @return the final amount after interest
     */
    @Override
    public double calculate() {
        if (getAmount() > 0) {
            return getAmount() - (getAmount() * getIncomingInterest());
        } else {
            return getAmount() + (getAmount() * getOutgoingInterest());
        }
    }

    /**
     * Returns a string representation of the Payment.
     *
     * @return a string representation of the Payment
     */
    @Override
    public String toString() {
        return String.format("Transaction{date='%s', description='%s', amount=%f}{incoming interest='%f', outgoing interest='%f'}",
                getDate(),
                getDescription(),
                getAmount(),
                getIncomingInterest(),
                getOutgoingInterest());
    }

    /**
     * Compares this Payment to another object for equality.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return Double.compare(getIncomingInterest(), payment.getIncomingInterest()) == 0 && Double.compare(getOutgoingInterest(), payment.getOutgoingInterest()) == 0;
    }
}