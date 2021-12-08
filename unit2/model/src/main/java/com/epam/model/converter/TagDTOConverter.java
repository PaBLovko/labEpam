package com.epam.model.converter;

import com.epam.model.bean.Tag;
import com.epam.model.dto.TagDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TagDTOConverter implements Function<TagDTO, Tag> {
    //TODO:add
    @Override
    public Tag apply(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tag.getId());
        tag.setName(tag.getName());
        return tag;
    }
}
