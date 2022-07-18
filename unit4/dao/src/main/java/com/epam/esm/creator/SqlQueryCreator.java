package com.epam.esm.creator;


import com.epam.esm.creator.criteria.Criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The interface Sql query creator.
 */
public interface SqlQueryCreator<T> {
    /**
     * Create criteria.
     *
     * @param criteriaList  the criteria list
     * @param criteriaQuery the criteria query
     * @param builder       the builder
     * @param root          the root
     */
    void createCriteria(List<Criteria<T>> criteriaList, CriteriaQuery<T> criteriaQuery, CriteriaBuilder builder, Root<T>
            root);
}
