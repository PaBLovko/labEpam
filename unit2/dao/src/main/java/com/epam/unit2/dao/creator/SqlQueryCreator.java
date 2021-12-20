package com.epam.unit2.dao.creator;


import com.epam.unit2.dao.creator.criteria.Criteria;

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
