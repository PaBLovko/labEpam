package com.epam.esm.api;

import com.epam.esm.Order;
import com.epam.esm.User;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

/**
 * The interface Order dao.
 *
 * @param <T> the type parameter
 */
public interface OrderDao<T extends Order> {
    /**
     * Insert long.
     *
     * @param order the order
     * @return the long
     */
    long insert(T order);

    /**
     * Find by user id list.
     *
     * @param page     the page
     * @param elements the elements
     * @param user     the user
     * @return the list
     */
    List<T> findByUserId(int page, int elements, User user);

    /**
     * Find by certificate id list.
     *
     * @param certificateId the certificate id
     * @return the list
     */
    List<T> findByCertificateId(long certificateId);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(long id);

    /**
     * Find by user id and order id optional.
     *
     * @param userId the certificate id
     * @param orderId       the order id
     * @return the optional
     */
    Optional<T> findByUserIdAndOrderId(long userId, long orderId);
}
