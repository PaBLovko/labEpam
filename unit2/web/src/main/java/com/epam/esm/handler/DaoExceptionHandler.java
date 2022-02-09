package com.epam.esm.handler;

import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.DaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DaoExceptionHandler {
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(DaoException.class)
    public final ResponseEntity<ControllerException> handleRuntimeExceptions(DaoException e) {
        ControllerException controllerException = new ControllerException(e.getLocalizedMessage(), e.getErrorCode());
        controllerException.setErrorCode(status.value() + e.getErrorCode());
        return new ResponseEntity<>(controllerException, status);
    }
}
