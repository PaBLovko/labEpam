package com.epam.esm.handler;

import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidFieldExceptionHandler {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(InvalidFieldException.class)
    public final ResponseEntity<ControllerException> handleRuntimeExceptions(InvalidFieldException e) {
        ControllerException controllerException = new ControllerException(e.getLocalizedMessage(), e.getErrorCode());
        controllerException.setErrorCode(status.value() + e.getErrorCode());
        return new ResponseEntity<>(controllerException, status);
    }
}
