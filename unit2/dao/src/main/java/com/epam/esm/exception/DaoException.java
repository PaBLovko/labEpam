package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DaoException extends RuntimeException{
    private String errorCode;
    private String message;
}
