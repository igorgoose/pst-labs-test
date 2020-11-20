package by.pst.schepov.test.rest.error;

import by.pst.schepov.test.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<Error> resourceNotFound(ResourceNotFoundException exception) {
        return new ResponseEntity<>(new Error(44, exception.getMessage()), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(ResourceConflictServiceException.class)
//    private ResponseEntity<Error> resourceConflict(ResourceConflictServiceException exception) {
//        return new ResponseEntity<>(new Error(41, exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
//    }
//
//    @ExceptionHandler(InvalidEntityDataServiceException.class)
//    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
//    private ResponseEntity<Error> invalidEntityData(InvalidEntityDataServiceException exception) {
//        return new ResponseEntity<>(new Error(42, exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
//    }
//
//    @ExceptionHandler(InvalidRequestDataServiceException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    private ResponseEntity<Error> invalidRequestData(InvalidRequestDataServiceException exception) {
//        return new ResponseEntity<>(new Error(40, exception.getMessage()), HttpStatus.BAD_REQUEST);
//    }



}
