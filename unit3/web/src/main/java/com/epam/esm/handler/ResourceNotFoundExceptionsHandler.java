package com.epam.esm.handler;

import com.epam.esm.constant.Symbol;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.response.ExceptionResponse;
import com.epam.esm.util.MessageLocale;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResourceNotFoundExceptionsHandler {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleRuntimeExceptions(ResourceNotFoundException e) {
        ExceptionResponse controllerException = new ExceptionResponse(e.getLocalizedMessage(
                MessageLocale.getCurrent()) + Symbol.SPACE + e.getDetail(), e.getErrorCode());
        controllerException.setErrorCode(status.value() + e.getErrorCode());
        return new ResponseEntity<>(controllerException, status);
    }
}
