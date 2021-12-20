package com.epam.unit2.service.impl;

import com.epam.unit2.dao.api.TagDao;
import com.epam.unit2.model.bean.Tag;
import com.epam.unit2.service.api.TagService;
import com.epam.unit2.service.exception.InvalidFieldException;
import com.epam.unit2.service.exception.ResourceDuplicateException;
import com.epam.unit2.service.exception.ResourceNotFoundException;
import com.epam.unit2.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao<Tag> dao;

    @Autowired
    public TagServiceImpl(TagDao<Tag> dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(Tag tag) {
        if (!TagValidator.isNameValid(tag.getName())) {
            throw new InvalidFieldException("Invalid tag name (name = " + tag.getName() + ")");
        }
        if (dao.findByName(tag.getName()).isPresent()) {
            throw new ResourceDuplicateException("Tag already exists (name = " + tag.getName() + ")");
        }
        return dao.insert(tag);
    }

    @Override
    public Tag findById(String id){
        try {
            return dao.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException("Requested" +
                    " resource not found (id = " + id + ")"));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException("Invalid tag id (id = " + id + ")", e);
        }
    }

    @Override
    public Tag findByName(String name) {
        if (TagValidator.isNameValid(name)) {
            return dao.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Requested" +
                    " resource not found (name = " + name + ")"));
        } else {
            throw new InvalidFieldException("Invalid tag name (name = " + name + ")");
        }
    }

    @Override
    public List<Tag> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Tag> findTagsConnectedToCertificate(String certificateId) {
        try {
            return dao.findTagsConnectedToCertificate(Long.parseLong(certificateId));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException("Invalid certificate id (id = " + certificateId + ")", e);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            return dao.delete(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException("Invalid tag id (id = " + id + ")", e);
        }
    }
}
