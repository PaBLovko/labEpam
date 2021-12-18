package com.epam.unit2.web.handler;

import com.epam.unit2.service.exception.ServiceException;
import com.epam.unit2.web.exception.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ControllerException> handleRuntimeExceptions(ServiceException e) {
        ControllerException controllerException = new ControllerException(e.getLocalizedMessage(), e.getCause());
        return new ResponseEntity<>(controllerException, status);
    }
}
