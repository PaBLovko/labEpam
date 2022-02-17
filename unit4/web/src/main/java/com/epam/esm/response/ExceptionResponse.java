package com.epam.esm.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse extends Response{
    private String errorCode;

    public ExceptionResponse(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
