package com.epam.esm.exception;

import com.epam.esm.constant.PropertyFileName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.ResourceBundle;

@AllArgsConstructor
@Getter
@Setter
public class InvalidUserCredentialException extends RuntimeException {
    private String errorCode;
    private String messageKey;
    private String detail;

    public String getLocalizedMessage(Locale locale) {
        return ResourceBundle.getBundle(PropertyFileName.ERROR_MESSAGES, locale).getString(messageKey);
    }
}
