package com.epam.unit2.web.handler;

import com.epam.unit2.service.exception.ResourceDuplicateException;
import com.epam.unit2.web.exception.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResourceDuplicateExceptionHandler {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(ResourceDuplicateException.class)
    public final ResponseEntity<ControllerException> handleRuntimeExceptions(ResourceDuplicateException e) {
        ControllerException controllerException = new ControllerException(e.getLocalizedMessage(), e.getCause());
        return new ResponseEntity<>(controllerException, status);
    }
}
