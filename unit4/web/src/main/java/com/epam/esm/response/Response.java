package com.epam.esm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@Getter
@Setter
public abstract class Response extends RepresentationModel<EntityOperationResponse> {
    private String message;
}
