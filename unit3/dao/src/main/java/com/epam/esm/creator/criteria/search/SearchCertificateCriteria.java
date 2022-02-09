package com.epam.esm.creator.criteria.search;


import com.epam.esm.Tag;
import com.epam.esm.creator.criteria.CertificateCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class SearchCertificateCriteria implements CertificateCriteria {
    private String columnName;
    private String value;
    private List<Tag> tags;

    SearchCertificateCriteria(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    SearchCertificateCriteria(List<Tag> tags) {
        this.tags = tags;
    }

}
