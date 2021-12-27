package com.epam.esm.handler;

import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResourceDuplicateExceptionHandler {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(ResourceDuplicateException.class)
    public final ResponseEntity<ControllerException> handleRuntimeExceptions(ResourceDuplicateException e) {
        ControllerException controllerException = new ControllerException(e.getMessage(), e.getCause());
        return new ResponseEntity<>(controllerException, status);
    }
}
