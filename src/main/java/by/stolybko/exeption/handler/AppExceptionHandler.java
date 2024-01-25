package by.stolybko.exeption.handler;

import by.stolybko.database.dto.ValidationErrorResponse;
import by.stolybko.database.dto.Violation;
import by.stolybko.exeption.EntityNotCreatedException;
import by.stolybko.exeption.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Violation> handleEntityNotFoundException(EntityNotFoundException e) {
        Violation violation = new Violation("uuid", e.getMessage());
        return ResponseEntity.badRequest().body(violation);
    }

    @ExceptionHandler(EntityNotCreatedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Violation> handleEntityNotCreatedException(EntityNotCreatedException e) {
        Violation violation = new Violation("n/a", e.getMessage());
        return ResponseEntity.badRequest().body(violation);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(new ValidationErrorResponse(violations));
    }

}
