package com.epam.unit2.service.impl;

import com.epam.unit2.dao.api.TagDao;
import com.epam.unit2.model.bean.Tag;
import com.epam.unit2.service.api.TagService;
import com.epam.unit2.service.exception.ServiceException;
import com.epam.unit2.service.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TagServiceImpl implements TagService<Tag> {
    private final TagDao<Tag> dao;

    @Autowired
    public TagServiceImpl(TagDao<Tag> dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(Tag tag) {
        if (!GiftCertificateValidator.isNameValid(tag.getName())) {
            throw new RuntimeException("Invalid tag name (name = " + tag.getName() + ")");
        }
        if (dao.findByName(tag.getName()).isPresent()) {
            throw new RuntimeException("Tag already exists (name = " + tag.getName() + ")");
        }
        return dao.insert(tag);
    }

    @Override
    public Tag findById(String id) throws ServiceException {
        try {
            return dao.findById(Long.parseLong(id)).orElseThrow(() -> new ServiceException("Requested" +
                    " resource not found (id = " + id + ")"));
        } catch (ServiceException e) {
            throw new ServiceException("Invalid tag id (id = " + id + ")", e);
        }
    }

    @Override
    public Tag findByName(String name) {
        if (GiftCertificateValidator.isNameValid(name)) {
            return dao.findByName(name).orElseThrow(() -> new RuntimeException("Requested" +
                    " resource not found (name = " + name + ")"));
        } else {
            throw new RuntimeException("Invalid tag name (name = " + name + ")");
        }
    }

    @Override
    public List<Tag> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Tag> findTagsConnectedToCertificate(String certificateId) throws ServiceException {
        try {
            return dao.findTagsConnectedToCertificate(Long.parseLong(certificateId));
        } catch (NumberFormatException e) {
            throw new ServiceException("Invalid certificate id (id = " + certificateId + ")", e);
        }
    }

    @Override
    public boolean delete(String id) throws ServiceException {
        try {
            return dao.delete(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new ServiceException("Invalid tag id (id = " + id + ")", e);
        }
    }
}
