package com.epam.esm.impl;

import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.api.OrderDao;
import com.epam.esm.constant.EntityFieldsName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;


@Repository
public class OrderDaoImpl implements OrderDao<Order> {
    private final EntityManagerFactory factory;

    @Autowired
    public OrderDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public long insert(Order order) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
        em.close();
        return order.getId();
    }

    @Override
    public List<Order> findByUserId(int page, int elements, User user) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.where(builder.equal(root.get(EntityFieldsName.USER), user));
        List<Order> orders = (page > 0 && elements > 0) ? em.createQuery(criteria).setMaxResults(elements)
                .setFirstResult(elements * (page - 1)).getResultList() : em.createQuery(criteria).getResultList();
        em.close();
        return orders;
    }

    @Override
    public boolean deleteByCertificateId(long certificateId) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaDelete<Order> criteria = builder.createCriteriaDelete(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.where(builder.equal(root.get(EntityFieldsName.GIFT_CERTIFICATE).get(EntityFieldsName.ID),
                certificateId));
        em.getTransaction().begin();
        boolean result = em.createQuery(criteria).executeUpdate() > 0;
        em.getTransaction().commit();
        em.close();
        return result;
    }

    @Override
    public List<Order> findByCertificateId(long certificateId) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.where(builder.equal(root.get(EntityFieldsName.GIFT_CERTIFICATE).get(EntityFieldsName.ID),
                certificateId));
        List<Order> orders = em.createQuery(criteria).getResultList();
        em.close();
        return orders;
    }

    @Override
    public Optional<Order> findById(long id) {
        EntityManager em = factory.createEntityManager();
        Optional<Order> order = Optional.ofNullable(em.find(Order.class, id));
        em.close();
        return order;
    }

    @Override
    public Optional<Order> findByUserIdAndOrderId(long userId, long orderId) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        Predicate userPredicate = builder.equal(root.get(EntityFieldsName.USER).get(EntityFieldsName.ID), userId);
        Predicate orderPredicate = builder.equal(root.get(EntityFieldsName.ID), orderId);
        criteria.where(userPredicate, orderPredicate);
        Optional<Order> result = em.createQuery(criteria).getResultStream().findAny();
        em.close();
        return result;
    }
}
