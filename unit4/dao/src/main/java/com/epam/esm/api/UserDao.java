package com.epam.esm.api;


import com.epam.esm.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 *
 * @param <T> the type parameter
 */
public interface UserDao<T extends User> {

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(long id);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<T> findByEmail(String email);


    /**
     * Find all list.
     *
     * @param page     the page
     * @param elements the elements
     * @return the list
     */
    List<T> findAll(int page, int elements);

    /**
     * Insert boolean.
     *
     * @param t the t
     * @return the boolean
     */
    long insert(T t);
}
