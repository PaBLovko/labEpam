package com.epam.esm.creator;


import com.epam.esm.creator.criteria.Criteria;

import java.util.List;

/**
 * The interface Sql query creator.
 */
public interface SqlQueryCreator {

    /**
     * Create query string.
     *
     * @param criteriaList the criteria list
     * @return the string
     */
    String createQuery(List<Criteria> criteriaList);
}
