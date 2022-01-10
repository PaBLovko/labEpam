package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResourceDuplicateException extends RuntimeException {
    private String errorCode;
    private String message;
    private String detail;

}