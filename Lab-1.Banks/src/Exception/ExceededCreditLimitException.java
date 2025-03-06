//попытка уйти в минус кредитного лимита
package Exception;

public class ExceededCreditLimitException extends RuntimeException {
    public ExceededCreditLimitException(String message) {
        super(message);
    }
}