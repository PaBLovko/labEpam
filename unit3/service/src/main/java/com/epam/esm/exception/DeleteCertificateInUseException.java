package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DeleteCertificateInUseException extends RuntimeException {
    private String errorCode;
    private String messageKey;
    private String detail;

}
