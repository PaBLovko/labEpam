package com.epam.esm.creator;

import com.epam.esm.GiftCertificate;
import com.epam.esm.constant.EntityFieldsName;
import com.epam.esm.creator.criteria.Criteria;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;


@Component
public class SqlGiftCertificateQueryCreator implements SqlQueryCreator<GiftCertificate> {
    @Override
    public void createCriteria(List<Criteria<GiftCertificate>> criteriaList, CriteriaQuery<GiftCertificate>
            criteriaQuery, CriteriaBuilder builder, Root<GiftCertificate> root) {
        criteriaQuery.where(builder.isNotEmpty(root.get(EntityFieldsName.TAGS)));
        if (!CollectionUtils.isEmpty(criteriaList)) {
            criteriaList.stream().filter(Objects::nonNull).forEach(c -> c.acceptCriteria(criteriaQuery, builder, root));
        }
    }
}
