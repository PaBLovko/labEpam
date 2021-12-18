package com.epam.unit2.dao.creator;


import com.epam.unit2.dao.creator.criteria.Criteria;

import java.util.List;


public interface SqlQueryCreator {

    String createQuery(List<Criteria> criteriaList);
}
