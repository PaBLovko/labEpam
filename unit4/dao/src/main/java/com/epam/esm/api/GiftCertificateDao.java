package com.epam.esm.api;

import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.GiftCertificate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 *
 * @param <T> the type parameter
 */
public interface GiftCertificateDao<T extends GiftCertificate> {

    /**
     * Insert boolean.
     *
     * @param t the t
     * @return the boolean
     */
    long insert(T t);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(long id);

    /**
     * Disconnect all tags boolean.
     *
     * @param giftCertificate the gift certificate
     * @return the boolean
     */
    boolean disconnectAllTags(GiftCertificate giftCertificate);

    /**
     * Update boolean.
     *
     * @param t the t
     */
    void update(T t);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<GiftCertificate> findById(long id);

    /**
     * Find all list.
     *
     * @param page     the page
     * @param elements the elements
     * @return the list
     */
    List<T> findAll(int page, int elements);

    /**
     * Find with tags list.
     *
     * @param page                    the page
     * @param elements                the elements
     * @param certificateCriteriaList the certificate criteria list
     * @return the list
     */
    List<T> findWithTags(int page, int elements, List<Criteria<GiftCertificate>> certificateCriteriaList);
}
