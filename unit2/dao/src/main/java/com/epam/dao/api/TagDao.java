package com.epam.dao.api;


import com.epam.model.bean.Tag;

import java.util.List;
import java.util.Optional;


public interface TagDao<T extends Tag> {
    boolean insert(T t);

    Optional<T> findById(long id);

    Optional<T> findByName(String name);

    List<T> findAll();

    List<T> findTagsConnectedToCertificate(long id);

    boolean delete(long id);
}
