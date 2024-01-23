package by.stolybko.exeption;

public class EntityNotFoundException extends RuntimeException {

    private EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException of(Class<?> clazz, Object field) {
        String message = String.format("%s with %s not found", clazz.getSimpleName(), field);
        return new EntityNotFoundException(message);
    }

}
