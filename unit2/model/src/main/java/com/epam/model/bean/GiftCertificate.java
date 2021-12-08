package com.epam.model.bean;

import com.epam.model.dto.TagDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.Period;
import java.util.List;

@Data
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private long price;
    private Period duration;
    private Instant createDate;
    private Instant lastUpdateDate;
    private List<Tag> tags;

}
