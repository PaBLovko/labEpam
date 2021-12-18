package com.epam.unit2.dao.creator.criteria.sort;


import com.epam.unit2.dao.creator.criteria.Criteria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class SortCriteria implements Criteria {
    private String columnName;
    private String sortOrdering;

}
