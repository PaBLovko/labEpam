package com.epam.esm.exception;

import com.epam.esm.constant.PropertyFileName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;
import java.util.ResourceBundle;


@Data
@AllArgsConstructor
public class InvalidFieldException extends RuntimeException {
    private String errorCode;
    private String message;
    private String detail;
    public String getLocalizedMessage(Locale locale) {
        return ResourceBundle.getBundle(PropertyFileName.ERROR_MESSAGES, locale).getString(message);
    }
}
