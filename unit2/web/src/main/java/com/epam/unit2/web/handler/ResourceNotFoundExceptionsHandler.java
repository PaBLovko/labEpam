package com.epam.unit2.web.handler;

import com.epam.unit2.service.exception.ResourceNotFoundException;
import com.epam.unit2.web.exception.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResourceNotFoundExceptionsHandler {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ControllerException> handleRuntimeExceptions(ResourceNotFoundException e) {
        ControllerException controllerException = new ControllerException(e.getLocalizedMessage(), e.getCause());
        return new ResponseEntity<>(controllerException, status);
    }
}
