package com.epam.esm.creator.criteria.search;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.constant.EntityFieldsName;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FullMatchSearchCertificateCriteria extends SearchCertificateCriteria {

    public FullMatchSearchCertificateCriteria(String columnName, String value) {
        super(columnName, value);
    }
    public FullMatchSearchCertificateCriteria(List<Tag> tags) {
        super(tags);
    }

    @Override
    public void acceptCriteria(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder,
                               Root<GiftCertificate> root) {
        List<Tag> searchedTags = getTags();
        if (!CollectionUtils.isEmpty(searchedTags)) {
            Expression<Collection<Tag>> tags = root.get(EntityFieldsName.TAGS);
            List<Predicate> containsTags = new ArrayList<>();
            searchedTags.forEach(t -> containsTags.add(builder.isMember(t, tags)));
            Predicate finalPredicate = builder.and(containsTags.toArray(new Predicate[0]));
            criteriaQuery.where(finalPredicate);
        } else {
            criteriaQuery.where(builder.equal(root.get(getColumnName()), getValue()));
        }
    }
}
