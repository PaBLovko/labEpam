package com.epam.unit2.service.exception;


public class ResourceDuplicateException extends RuntimeException {

    public ResourceDuplicateException() {
        super();
    }

    public ResourceDuplicateException(String message) {
        super(message);
    }

    public ResourceDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceDuplicateException(Throwable cause) {
        super(cause);
    }
}