package Exception;
//попытка найи не существубщие поле
public class FieldNotFound extends RuntimeException{
    public FieldNotFound(String message){ super(message);}

}
