package com.epam.esm.api;

import com.epam.esm.Tag;

/**
 * The interface Tag service.
 *
 */
public interface TagService extends CRRepository<Tag> {

    /**
     * Find by name t.
     *
     * @param name the name
     * @return the t
     */
    Tag findByName(String name);

    /**
     * Find most used tag of user with highest cost of all orders t.
     *
     * @param userId the user id
     * @return the t
     */
    Tag findMostUsedTagOfUserWithHighestCostOfAllOrders(String userId, String userName);

    /**
     * Update availability
     * @param id the tag id
     * @param isAvailable the state
     */
    void updateAvailability(String id, boolean isAvailable);

    /**
     * Delete boolean.
     *
     * @param id the id
     */
    void delete(String id, boolean isTagInUse);
}
