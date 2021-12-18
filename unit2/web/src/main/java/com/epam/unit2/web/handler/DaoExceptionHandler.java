//package com.epam.web.handler;
//
//import com.epam.web.exception.ControllerException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class DaoExceptionHandler {
//    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//    @ExceptionHandler(DaoException.class)
//    public final ResponseEntity<ControllerException> handleRuntimeExceptions(DaoException e) {
//        ControllerException controllerException = new ControllerException(e.getLocalizedMessage(), e.getErrorCode());
//        controllerException.setErrorCode(status.value() + e.handleRuntimeExceptions());
//        return new ResponseEntity<>(exceptionResponse, status);
//    }
//}
