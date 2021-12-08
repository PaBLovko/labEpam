package com.epam.model.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class GiftCertificateDTO {
    private long id;
    private String name;
    private String description;
    private long price;
    private String duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDTO> tags;
}
