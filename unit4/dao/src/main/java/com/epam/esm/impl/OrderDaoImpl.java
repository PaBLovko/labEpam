package com.epam.esm.impl;

import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.api.OrderDao;
import com.epam.esm.constant.entity.GiftCertificateFieldName;
import com.epam.esm.constant.entity.OrderFieldName;
import com.epam.esm.constant.entity.UserFieldName;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;


@Repository
public class OrderDaoImpl implements OrderDao<Order> {
    private EntityManager manager;

    @PersistenceContext
    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    @Override
    public long insert(Order order) {
        manager.persist(order);
        return order.getId();
    }

    @Override
    public List<Order> findByUserId(int page, int elements, User user) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.where(builder.equal(root.get(OrderFieldName.USER), user));
        return (page > 0 && elements > 0) ? manager.createQuery(criteria).setMaxResults(elements)
                .setFirstResult(elements * (page - 1)).getResultList() : manager.createQuery(criteria).getResultList();
    }


    @Override
    public List<Order> findByCertificateId(long certificateId) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.where(builder.equal(root.get(OrderFieldName.GIFT_CERTIFICATE).get(GiftCertificateFieldName.ID),
                certificateId));
        return manager.createQuery(criteria).getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(manager.find(Order.class, id));
    }

    @Transactional
    @Override
    public Optional<Order> findByUserIdAndOrderId(long userId, long orderId) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        Predicate userPredicate = builder.equal(root.get(OrderFieldName.USER).get(UserFieldName.ID), userId);
        Predicate orderPredicate = builder.equal(root.get(OrderFieldName.ID), orderId);
        criteria.where(userPredicate, orderPredicate);
        return manager.createQuery(criteria).getResultStream().findFirst();
    }
}
