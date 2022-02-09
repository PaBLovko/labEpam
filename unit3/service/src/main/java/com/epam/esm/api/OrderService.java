package com.epam.esm.api;


import com.epam.esm.Order;

import java.util.List;

/**
 * The interface Order service.
 *
 */
public interface OrderService {
    /**
     * Find by user id list.
     *
     * @param page     the page
     * @param elements the elements
     * @param id       the id
     * @return the list
     */
    List<Order> findByUserId(int page, int elements, String id);

    /**
     * Create order long.
     *
     * @param userId        the user id
     * @param certificateId the certificate id
     * @return the long
     */
    long createOrder(String userId, String certificateId);

    /**
     * Find by user id and order id t.
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the t
     */
    Order findByUserIdAndOrderId(String userId, String orderId);

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    Order findById(String id);

    /**
     * Delete by certificate id boolean.
     *
     * @param certificateId the certificate id
     * @return the boolean
     */
    boolean deleteByCertificateId(String certificateId);
}
