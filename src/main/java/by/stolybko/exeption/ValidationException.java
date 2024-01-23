package by.stolybko.exeption;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public static ValidationException of(Class<?> clazz, Object object) {
        return new ValidationException("Object type of: " + clazz + " thrown by: " + object);
    }
}
