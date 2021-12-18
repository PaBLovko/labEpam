package com.epam.unit2.service.api;

import com.epam.unit2.model.bean.Tag;
import com.epam.unit2.service.exception.ServiceException;

import java.util.List;

public interface TagService <T extends Tag>{
    boolean insert(Tag tag);

    T findById(String id) throws ServiceException;

    T findByName(String name);

    List<T> findAll();

    List<T> findTagsConnectedToCertificate(String certificateId) throws ServiceException;

    boolean delete(String id) throws ServiceException;
}
