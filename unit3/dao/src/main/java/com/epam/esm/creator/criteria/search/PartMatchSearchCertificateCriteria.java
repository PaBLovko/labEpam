package com.epam.esm.creator.criteria.search;

import com.epam.esm.GiftCertificate;
import com.epam.esm.constant.Symbol;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PartMatchSearchCertificateCriteria extends SearchCertificateCriteria {

    public PartMatchSearchCertificateCriteria(String columnName, String value) {
        super(columnName, value);
    }

    @Override
    public void acceptCriteria(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder,
                               Root<GiftCertificate> root) {
        criteriaQuery.where(builder.like(root.get(getColumnName()), Symbol.PERCENTAGE + getValue() +
                Symbol.PERCENTAGE));
    }
}
