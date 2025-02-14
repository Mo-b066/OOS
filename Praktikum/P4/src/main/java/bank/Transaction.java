package bank;

import java.util.Objects;

public abstract class Transaction implements CalculateBill {
    protected String date;
    protected double amount= 0;
    protected String description;

    Transaction(String date, double amount, String description) {
        this.date= date;
        this.amount= amount;
        this.description= description;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date= date;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount= amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description= description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(date, that.date) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, description);
    }
}
