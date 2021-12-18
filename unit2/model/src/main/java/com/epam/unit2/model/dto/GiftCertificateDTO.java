package com.epam.unit2.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class GiftCertificateDTO {
    private long id;
    private String name;
    private String description;
    private long price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDTO> tags;
}
