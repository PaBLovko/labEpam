package com.epam.esm.api;

import com.epam.esm.Tag;

/**
 * The interface Tag service.
 *
 */
public interface TagService extends CRDRepository<Tag> {

    /**
     * Find by name t.
     *
     * @param name the name
     * @return the t
     */
    Tag findByName(String name);

//    /**
//     * Find tags connected to certificate list.
//     *
//     * @param certificateId the certificate id
//     * @return the list
//     */
//    List<Tag> findTagsConnectedToCertificate(String certificateId);

    /**
     * Find most used tag of user with highest cost of all orders t.
     *
     * @param userId the user id
     * @return the t
     */
    Tag findMostUsedTagOfUserWithHighestCostOfAllOrders(String userId);

}
