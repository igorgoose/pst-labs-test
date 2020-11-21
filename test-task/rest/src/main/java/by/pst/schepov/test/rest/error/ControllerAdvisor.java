package by.pst.schepov.test.rest.error;

import by.pst.schepov.test.service.exception.InvalidRequestDataException;
import by.pst.schepov.test.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<Error> resourceNotFound(ResourceNotFoundException exception) {
        return new ResponseEntity<>(new Error(44, exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<Error> invalidRequestData(InvalidRequestDataException exception) {
        return new ResponseEntity<>(new Error(40, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }



}
