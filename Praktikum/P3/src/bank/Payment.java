package bank;

import bank.exceptions.InterestException;

import java.util.Objects;

public class Payment extends Transaction implements CalculateBill {

    private double incomingInterest;
    private double outgoingInterest;

    public Payment (String date, double amount, String description) {
     super(date, amount, description);
    }
    public Payment(String name, double amount, String description, double incomingInterest, double outgoingInterest) {
        super(name, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }
    public Payment(Payment payment) {
        super(payment.getDate(), payment.getAmount(), payment.getDescription());
        setIncomingInterest(payment.getIncomingInterest());
        setOutgoingInterest(payment.getOutgoingInterest());
    }



    public double getIncomingInterest() throws InterestException {
        if(incomingInterest < 0 || incomingInterest > 1) {
            throw new InterestException("Invalid interest rate");
        }
        return incomingInterest;
    }
    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest= incomingInterest;
    }
    public double getOutgoingInterest() throws InterestException {
        if(outgoingInterest < 0 || outgoingInterest > 1) {
            throw new InterestException("Invalid interest rate");
        }
        return outgoingInterest;
    }
    public void setOutgoingInterest(double outgoingInterest) {
        this.outgoingInterest= outgoingInterest;
    }

@Override
    public String toString() {
        return super.toString() + ", Incoming Interest: " + incomingInterest + ", Outgoing Interest: " + outgoingInterest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return super.equals(o) && Double.compare(incomingInterest, payment.incomingInterest) == 0 && Double.compare(outgoingInterest, payment.outgoingInterest) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), incomingInterest, outgoingInterest);
    }

    @Override
    public double calculate() {
        if(incomingInterest>0 && amount>0){
            return amount-(amount*incomingInterest);
        }
        else if(outgoingInterest>0 && amount<0){
            return (amount*outgoingInterest)+amount;
        }
        return 0.0;
    }
}
