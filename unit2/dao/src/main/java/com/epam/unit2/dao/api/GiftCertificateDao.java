package com.epam.unit2.dao.api;

import com.epam.unit2.dao.creator.criteria.Criteria;
import com.epam.unit2.dao.exception.DaoException;
import com.epam.unit2.model.bean.GiftCertificate;
import com.epam.unit2.model.bean.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao<T extends GiftCertificate> {
    boolean insert(T t) throws DaoException;

    boolean delete(long id);

    boolean disconnectAllTags(long id);

    boolean update(T t);

    Optional<GiftCertificate> findById(long id);

    List<T> findAll();

    List<T> findWithTags(List<Criteria> criteriaList);

    boolean connectTags(List<Tag> tags, long certificateId);
}
