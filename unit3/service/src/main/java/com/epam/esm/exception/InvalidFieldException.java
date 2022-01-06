package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidFieldException extends RuntimeException {
    private String errorCode;
    private String message;
}
