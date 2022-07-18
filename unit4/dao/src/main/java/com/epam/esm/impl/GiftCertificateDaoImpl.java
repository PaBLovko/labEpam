package com.epam.esm.impl;

import com.epam.esm.api.GiftCertificateDao;
import com.epam.esm.constant.entity.GiftCertificateFieldName;
import com.epam.esm.creator.SqlQueryCreator;
import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao<GiftCertificate> {
    private final SqlQueryCreator<GiftCertificate> criteriaCreator;
    private EntityManager manager;

    @Autowired
    public GiftCertificateDaoImpl(SqlQueryCreator<GiftCertificate> criteriaCreator, EntityManagerFactory factory) {
        this.criteriaCreator = criteriaCreator;
    }

    @PersistenceContext
    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    @Override
    public long insert(GiftCertificate giftCertificate) {
        manager.persist(giftCertificate);
        return giftCertificate.getId();
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaDelete<GiftCertificate> criteria = builder.createCriteriaDelete(GiftCertificate.class);
        Root<GiftCertificate> root = criteria.from(GiftCertificate.class);
        criteria.where(builder.equal(root.get(GiftCertificateFieldName.ID), id));
        return manager.createQuery(criteria).executeUpdate() == 1;
    }

    @Transactional
    @Override
    public boolean disconnectAllTags(GiftCertificate giftCertificate) {
        giftCertificate.setTags(null);
        manager.merge(giftCertificate);
        return CollectionUtils.isEmpty(giftCertificate.getTags());
    }

    @Transactional
    @Override
    public void update(GiftCertificate giftCertificate) {
        manager.clear();
        manager.merge(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(manager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll(int page, int elements) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteria.from(GiftCertificate.class);
        criteria.select(root);

        return (page > 0 && elements > 0) ? manager.createQuery(criteria)
                .setMaxResults(elements).setFirstResult(elements * (page - 1)).getResultList() :
                manager.createQuery(criteria).getResultList();
    }

    @Override
    public List<GiftCertificate> findWithTags(int page, int elements,
                                              List<Criteria<GiftCertificate>> certificateCriteriaList) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteria = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteria.from(GiftCertificate.class);

        criteriaCreator.createCriteria(certificateCriteriaList, criteria, builder, root);

        return  (page > 0 && elements > 0) ? manager.createQuery(criteria)
                .setMaxResults(elements).setFirstResult(elements * (page - 1)).getResultList() :
                manager.createQuery(criteria).getResultList();
    }
}
