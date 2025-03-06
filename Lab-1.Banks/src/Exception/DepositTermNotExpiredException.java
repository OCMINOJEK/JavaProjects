//попытка снять с депозитного счета раньше срока
package Exception;

public class DepositTermNotExpiredException extends RuntimeException {
    public DepositTermNotExpiredException(String message) {
        super(message);
    }
}