package com.epam.unit2.service.api;

import com.epam.unit2.model.bean.GiftCertificate;
import com.epam.unit2.service.exception.ServiceException;

import java.util.List;

public interface GiftCertificateService <T extends GiftCertificate> {

    boolean insert(T t) throws ServiceException;

    boolean delete(String id) throws ServiceException;

    boolean update(String id, GiftCertificate giftCertificate) throws ServiceException;

    T findById(String id) throws ServiceException;

    List<GiftCertificate> findAll();

    List<GiftCertificate> findCertificatesWithTagsByCriteria(String tagName, String certificateName,
                                                             String certificateDescription, String sortByName,
                                                             String sortByDate);
}
