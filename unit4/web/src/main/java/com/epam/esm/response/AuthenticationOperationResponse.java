package com.epam.esm.response;

import com.epam.esm.constant.PropertyFileName;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

@Getter
public class AuthenticationOperationResponse extends Response {
    private final String operation;
    private final long objectId;
    private final Locale responseLocale;
    private final String token;

    public AuthenticationOperationResponse(EntityOperationResponse.Operation operation, String messageKey, long objectId, Locale locale, String token) {
        super(ResourceBundle.getBundle(PropertyFileName.OPERATION_RESPONSE_MESSAGES,
                locale).getString(messageKey) + StringUtils.SPACE + objectId);
        this.responseLocale = locale;
        this.operation = operation.getLocalizedOperationName(locale);
        this.objectId = objectId;
        this.token = token;
    }
}
