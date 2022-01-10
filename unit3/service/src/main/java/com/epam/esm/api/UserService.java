package com.epam.esm.api;

import com.epam.esm.User;

import java.util.List;

/**
 * The interface User service.
 *
 */
public interface UserService {
    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    User findById(String id);

    /**
     * Find all list.
     *
     * @param page     the page
     * @param elements the elements
     * @return the list
     */
    List<User> findAll(int page, int elements);
}
