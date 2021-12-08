package com.epam.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TagDTO {
    private long id;
    private String name;
}
