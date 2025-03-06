package Exception;
//представляет исключение, которое выбрасывается при попытке выполнить операцию, запрещенную для сомнительного счета.
public class RestrictionOperation extends RuntimeException {
    public RestrictionOperation(String message) {
        super(message);
    }
}
