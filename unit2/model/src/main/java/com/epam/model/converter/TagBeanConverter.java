package com.epam.model.converter;

import com.epam.model.bean.Tag;
import com.epam.model.dto.TagDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TagBeanConverter implements Function<Tag, TagDTO> {

    @Override
    public TagDTO apply(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }
}
