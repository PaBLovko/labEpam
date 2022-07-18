package com.epam.esm.creator.criteria.search;


import com.epam.esm.Tag;
import com.epam.esm.creator.criteria.CertificateCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class SearchCertificateCriteria implements CertificateCriteria {
    private String fieldName;
    private String value;
    private List<Tag> tags;

    SearchCertificateCriteria(String fieldName, String value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    SearchCertificateCriteria(List<Tag> tags) {
        this.tags = tags;
    }

}
