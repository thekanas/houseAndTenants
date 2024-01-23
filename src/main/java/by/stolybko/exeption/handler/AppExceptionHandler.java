package by.stolybko.exeption.handler;

import by.stolybko.exeption.EntityNotCreatedException;
import by.stolybko.exeption.EntityNotFoundException;
import by.stolybko.exeption.ErrorMessage;
import by.stolybko.exeption.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException e) {

        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage(), "404"));
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleEntityNotCreatedException(EntityNotCreatedException e) {

        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage(), "400"));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> validationException(ValidationException e) {

        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage(), "400"));
    }

}
