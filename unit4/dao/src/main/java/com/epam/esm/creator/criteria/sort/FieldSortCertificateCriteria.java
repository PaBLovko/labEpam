package com.epam.esm.creator.criteria.sort;


import com.epam.esm.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class FieldSortCertificateCriteria extends SortCertificateCriteria {
    private static final String SORT_ASC = "ASC";

    public FieldSortCertificateCriteria(String columnName, String sortOrdering) {
        super(columnName, sortOrdering);
    }

    @Override
    public void acceptCriteria(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder,
                                 Root<GiftCertificate> root) {
        Order order = getSortOrdering().equalsIgnoreCase(SORT_ASC) ? builder.asc(root.get(getColumnName())) :
                builder.desc(root.get(getColumnName()));
        criteriaQuery.orderBy(order);
    }
}
