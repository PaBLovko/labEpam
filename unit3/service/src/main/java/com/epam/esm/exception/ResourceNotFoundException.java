package com.epam.esm.exception;

import com.epam.esm.constant.ErrorAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;
import java.util.ResourceBundle;


@Data
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private String errorCode;
    private String message;
    private String detail;
    public String getLocalizedMessage(Locale locale) {
        return ResourceBundle.getBundle(ErrorAttribute.PROPERTY_FILE_NAME, locale).getString(message);
    }
}
