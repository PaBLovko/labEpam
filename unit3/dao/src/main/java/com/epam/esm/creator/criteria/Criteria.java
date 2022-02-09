package com.epam.esm.creator.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * The interface Criteria.
 */
public interface Criteria<T> {
    /**
     * Accept criteria.
     *
     * @param criteriaQuery the criteria query
     * @param builder       the builder
     * @param root          the root
     */
    void acceptCriteria(CriteriaQuery<T> criteriaQuery, CriteriaBuilder builder, Root<T> root);

}
