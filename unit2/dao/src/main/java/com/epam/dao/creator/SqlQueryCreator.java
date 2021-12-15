package com.epam.dao.creator;


import com.epam.dao.creator.criteria.Criteria;

import java.util.List;


public interface SqlQueryCreator {

    String createQuery(List<Criteria> criteriaList);
}
