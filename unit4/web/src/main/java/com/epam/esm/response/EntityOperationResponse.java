package com.epam.esm.response;

import com.epam.esm.constant.PropertyFileName;
import com.epam.esm.constant.ResponseMessageName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

@Getter
public class EntityOperationResponse extends Response {
    private final String operation;
    private final long objectId;
    private final Locale responseLocale;

    @AllArgsConstructor
    public enum Operation {
        CREATION(ResponseMessageName.CREATION_OPERATION),
        DELETION(ResponseMessageName.DELETION_OPERATION),
        UPDATE(ResponseMessageName.UPDATE_OPERATION),
        AUTHORIZATION(ResponseMessageName.AUTHORIZATION_OPERATION),
        LOGOUT(ResponseMessageName.LOGOUT_OPERATION);

        private final String nameKey;

        public String getLocalizedOperationName(Locale locale) {
            return ResourceBundle.getBundle(PropertyFileName.OPERATION_RESPONSE_MESSAGES, locale).getString(nameKey);
        }
    }

    public EntityOperationResponse(Operation operation, String messageKey, long objectId, Locale locale) {
        super(ResourceBundle.getBundle(PropertyFileName.OPERATION_RESPONSE_MESSAGES,
                locale).getString(messageKey) + StringUtils.SPACE + objectId);
        this.responseLocale = locale;
        this.operation = operation.getLocalizedOperationName(locale);
        this.objectId = objectId;
    }
}
