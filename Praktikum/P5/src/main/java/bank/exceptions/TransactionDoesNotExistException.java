package bank.exceptions;

public class TransactionDoesNotExistException extends RuntimeException {
    public TransactionDoesNotExistException(String message) {
        super(message);
    }
}
