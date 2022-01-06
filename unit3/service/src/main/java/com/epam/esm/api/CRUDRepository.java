package com.epam.esm.api;

import java.util.List;

/**
 * CRUD operations
 *
 * @param <T> the type parameter
 */
public interface CRUDRepository<T> {

    /**
     * Insert boolean.
     *
     * @param t the t
     * @return the boolean
     */
    boolean insert(T t);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(String id);

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
     * @return the list
     */
    List<T> findAll();
}
