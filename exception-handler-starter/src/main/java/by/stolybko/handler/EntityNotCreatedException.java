package by.stolybko.handler;

public class EntityNotCreatedException extends RuntimeException{
    public EntityNotCreatedException(String message) {
        super(message);
    }
}
