package com.epam.esm.exception;

public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException() {
        super();
    }

    public InvalidFieldException(String message) {
        super(message);
    }

    public InvalidFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFieldException(Throwable cause) {
        super(cause);
    }
}
