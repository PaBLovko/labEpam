package com.epam.esm.response;

import com.epam.esm.attribute.ResponseAttribute;
import com.epam.esm.constant.Symbol;
import com.epam.esm.util.MessageLocale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ResourceBundle;

@NoArgsConstructor
@Getter
@Setter
public class OperationResponse extends RepresentationModel<OperationResponse> {
    private String operation;
    private String message;

    public enum Operation {
        CREATION(ResponseAttribute.CREATION_OPERATION),
        DELETION(ResponseAttribute.DELETION_OPERATION),
        UPDATE(ResponseAttribute.UPDATE_OPERATION),
        OTHER(ResponseAttribute.OTHER_OPERATION);

        private final String nameKey;

        Operation(String nameKey) {
            this.nameKey = nameKey;
        }

        public String getLocalizedOperationName() {
            return ResourceBundle.getBundle(ResponseAttribute.PROPERTY_FILE_NAME,
                    MessageLocale.getCurrent()).getString(nameKey);
        }
    }

    public OperationResponse(Operation operation, String messageKey, String detail) {
        this.operation = operation.getLocalizedOperationName();
        this.message = ResourceBundle.getBundle(ResponseAttribute.PROPERTY_FILE_NAME,
                MessageLocale.getCurrent()).getString(messageKey) + Symbol.SPACE + detail;
    }
}
