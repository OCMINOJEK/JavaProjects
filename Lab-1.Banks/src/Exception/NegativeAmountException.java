//попытка пополнить или снять отрицательную сумму
package Exception;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(String message) {
        super(message);
    }
}
