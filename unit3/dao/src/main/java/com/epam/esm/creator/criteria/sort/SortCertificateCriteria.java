package com.epam.esm.creator.criteria.sort;


import com.epam.esm.creator.criteria.CertificateCriteria;
import com.epam.esm.creator.criteria.Criteria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class SortCertificateCriteria implements CertificateCriteria {
    private String columnName;
    private String sortOrdering;

}
