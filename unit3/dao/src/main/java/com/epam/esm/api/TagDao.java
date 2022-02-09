package com.epam.esm.api;


import com.epam.esm.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 *
 * @param <T> the type parameter
 */
public interface TagDao<T extends Tag> {

    /**
     * Insert boolean.
     *
     * @param t the t
     * @return the boolean
     */
    long insert(T t);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(long id);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<T> findByName(String name);

    /**
     * Find all list.
     *
     * @param page     the page
     * @param elements the elements
     * @return the list
     */
    List<T> findAll(int page, int elements);

    /**
     * Find most used tag of user with highest cost of all orders optional.
     *
     * @param userId the user id
     * @return the optional
     */
    Optional<T> findMostUsedTagOfUserWithHighestCostOfAllOrders(long userId);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(long id);
}
