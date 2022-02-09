package com.epam.esm.api;

import java.util.List;

/**
 * CRUD operations
 *
 * @param <T> the type parameter
 */
public interface CRRepository<T> {

    /**
     * Insert boolean.
     *
     * @param t the t
     * @return the boolean
     */
    long insert(T t);

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(String id);

    /**
     * Find all list.
     *
     * @param page     the page
     * @param elements the elements
     * @return the list
     */
    List<T> findAll(int page, int elements);
}
