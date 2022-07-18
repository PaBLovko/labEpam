package com.epam.esm.api;

import com.epam.esm.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * The interface User service.
 *
 */
public interface UserService extends UserDetailsService {
    /**
     * Find by id User.
     *
     * @param id the id
     * @return the User
     */
    User findById(String id, String userName);

    /**
     * Find by email User
     * @param email the email
     * @return the User
     */
    User findByEmail(String email);

    /**
     * Find all list.
     *
     * @param page     the page
     * @param elements the elements
     * @return the list
     */
    List<User> findAll(int page, int elements);

    /**
     * Insert the User
     * @param user the user
     * @return the id this User
     */
    long insert(User user);
}
