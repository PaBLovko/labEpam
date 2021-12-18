package com.epam.unit2.dao.creator.criteria.search;


import com.epam.unit2.dao.creator.criteria.Criteria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class SearchCriteria implements Criteria {
    private String columnName;
    private String value;

}
