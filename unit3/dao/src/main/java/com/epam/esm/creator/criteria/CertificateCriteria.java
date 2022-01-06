package com.epam.esm.creator.criteria;


import com.epam.esm.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * The interface Certificate criteria.
 */
public interface CertificateCriteria extends Criteria<GiftCertificate> {
    void acceptCriteria(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder,
                        Root<GiftCertificate> root);
}
