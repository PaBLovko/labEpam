package com.epam.esm.creator.criteria.sort;


public class FieldSortCriteria extends SortCriteria {
    private static final String SPACE_SYMBOL = " ";

    public FieldSortCriteria(String columnName, String sortOrdering) {
        super(columnName, sortOrdering);
    }

    @Override
    public String acceptCriteria() {
        return getColumnName() + SPACE_SYMBOL + getSortOrdering();
    }
}
