package com.epam.esm.api;

import com.epam.esm.GiftCertificate;

import java.util.List;

/**
 * The interface Gift certificate service.
 *
 */
public interface GiftCertificateService extends CRDRepository<GiftCertificate> {

    /**
     * Update boolean.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the boolean
     */
    boolean update(String id, GiftCertificate giftCertificate) ;

    /**
     * Find certificates with tags by criteria list.
     *
     * @param page                   the page
     * @param elements               the elements
     * @param tagName                the tag name
     * @param certificateName        the certificate name
     * @param certificateDescription the certificate description
     * @param sortByName             the sort by name
     * @param sortByDate             the sort by date
     * @return the list
     */
    List<GiftCertificate> findCertificatesWithTagsByCriteria(int page, int elements, List<String> tagName,
                                                             String certificateName,
                                                             String certificateDescription, String sortByName,
                                                             String sortByDate);

//    /**
//     * Disconnect tag by id.
//     *
//     * @param tagId the tag id
//     */
//    void disconnectTagById(String tagId);
}
