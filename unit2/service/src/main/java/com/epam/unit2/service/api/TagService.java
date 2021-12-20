package com.epam.unit2.service.api;

import com.epam.unit2.model.bean.Tag;

import java.util.List;

/**
 * The interface Tag service.
 *
 */
public interface TagService extends CRUDRepository<Tag> {

    /**
     * Find by name t.
     *
     * @param name the name
     * @return the t
     */
    Tag findByName(String name);

    /**
     * Find tags connected to certificate list.
     *
     * @param certificateId the certificate id
     * @return the list
     */
    List<Tag> findTagsConnectedToCertificate(String certificateId);

}
