package com.epam.esm.creator.criteria.search;


import com.epam.esm.creator.criteria.Criteria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class SearchCriteria implements Criteria {
    private String columnName;
    private String value;

}
