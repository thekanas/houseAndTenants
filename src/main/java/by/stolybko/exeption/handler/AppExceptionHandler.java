package by.stolybko.exeption.handler;

import by.stolybko.exeption.EntityNotCreatedException;
import by.stolybko.exeption.EntityNotFoundException;
import by.stolybko.exeption.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleEntityNotFoundException(EntityNotFoundException e) {

        return new ErrorMessage(e.getMessage(), "404");
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleEntityNotCreatedException(EntityNotCreatedException e) {

        return new ErrorMessage(e.getMessage(), "400");
    }

}
