package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.api.GiftCertificateDao;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.TagService;
import com.epam.esm.constant.EntityFieldsName;
import com.epam.esm.constant.ErrorAttribute;
import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.creator.criteria.search.FullMatchSearchCertificateCriteria;
import com.epam.esm.creator.criteria.search.PartMatchSearchCertificateCriteria;
import com.epam.esm.creator.criteria.sort.FieldSortCertificateCriteria;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.validator.GiftCertificateValidator.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String ASC_SORT_ORDERING = "ASC";
    private static final String DESC_SORT_ORDERING = "DESC";
    private final GiftCertificateDao<GiftCertificate> dao;
    private final TagService tagService;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao<GiftCertificate> dao, TagService tagService) {
        this.dao = dao;
        this.tagService = tagService;
    }

    @Override
    public long insert(GiftCertificate giftCertificate) {
        long id;
        if (isGiftCertificateCreationFormValid(giftCertificate)) {
            giftCertificate.setCreateDate(LocalDateTime.now());
            if (!CollectionUtils.isEmpty(giftCertificate.getTags())) {
                Set<Tag> existingTags = SetUtils.intersection(new HashSet<>(tagService.findAll(0, 0)),
                        giftCertificate.getTags());
                Set<Tag> newTags = new HashSet<>(CollectionUtils.removeAll(giftCertificate.getTags(), existingTags));
                giftCertificate.setTags(newTags);
                id = dao.insert(giftCertificate);
                GiftCertificate certificateWithAllTags = dao.findById(id).get();
                certificateWithAllTags.setTags(SetUtils.union(newTags, existingTags));
                dao.update(certificateWithAllTags);
            } else {
                id = dao.insert(giftCertificate);
            }
        } else {
            throw new InvalidFieldException(ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE,
                    ErrorAttribute.INVALID_GIFT_CERTIFICATE_ERROR, giftCertificate.toString());
        }
        return id;
    }

    @Override
    public boolean delete(String id) {
        try {
            GiftCertificate giftCertificate = dao.findById(Long.parseLong(id)).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE,
                            ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, id));
            if (!CollectionUtils.isEmpty(giftCertificate.getTags())) {
                dao.disconnectAllTags(giftCertificate);
            }
            return dao.delete(giftCertificate.getId());
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE,
                    ErrorAttribute.INVALID_GIFT_CERTIFICATE_ID_ERROR, id);
        }
    }

    @Override
    public boolean update(String id, GiftCertificate newGiftCertificate) {
        try {
            GiftCertificate oldGiftCertificate = dao.findById(Long.parseLong(id)).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE,
                            ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, id));
            if (updateGiftCertificateFields(oldGiftCertificate, newGiftCertificate)) {
                oldGiftCertificate.setLastUpdateDate(LocalDateTime.now());
                if (!CollectionUtils.isEmpty(oldGiftCertificate.getTags())) {
                    List<Tag> newTags = (List<Tag>) CollectionUtils.removeAll(oldGiftCertificate.getTags(),
                            CollectionUtils.intersection(tagService.findAll(0, 0), oldGiftCertificate.getTags()));
                    newTags.forEach(tagService::insert);
                }
                dao.update(oldGiftCertificate);
            } else {
                throw new InvalidFieldException(ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE,
                        ErrorAttribute.INVALID_GIFT_CERTIFICATE_ERROR, newGiftCertificate.toString());
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE,
                    ErrorAttribute.INVALID_GIFT_CERTIFICATE_ID_ERROR, id);
        }
        return true;
    }

    @Override
    public GiftCertificate findById(String id) {
        try {
            return dao.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException(
                    ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, id));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorAttribute.GIFT_CERTIFICATE_ERROR_CODE,
                    ErrorAttribute.INVALID_GIFT_CERTIFICATE_ID_ERROR, id);
        }
    }

    @Override
    public List<GiftCertificate> findAll(int page, int elements) {
        return dao.findAll(page, elements);
    }

    @Override
    public void disconnectTagById(String tagId) {
        Tag tag = tagService.findById(tagId);
        List<GiftCertificate> certificatesWithSuchTag = findCertificatesWithTagsByCriteria(0, 0,
                Collections.singletonList(tag.getName()), null, null, null, null);
        if (!CollectionUtils.isEmpty(certificatesWithSuchTag)) {
            for (GiftCertificate certificate : certificatesWithSuchTag) {
                Set<Tag> updatedTags = certificate.getTags();
                updatedTags.remove(tag);
                certificate.setTags(updatedTags);
                dao.update(certificate);
            }
        }
    }

    @Override
    public List<GiftCertificate> findCertificatesWithTagsByCriteria(int page, int elements,List<String> tagsNames,
                                                                    String certificateName,
                                                                    String certificateDescription, String sortByName,
                                                                    String sortByDate) {
        List<Criteria<GiftCertificate>> criteriaList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(tagsNames) && tagsNames.stream().allMatch(TagValidator::isNameValid)) {
            List<Tag> tags = new ArrayList<>();
            tagsNames.forEach(t -> tags.add(tagService.findByName(t)));
            criteriaList.add(new FullMatchSearchCertificateCriteria(tags));
        }

        if (isNameValid(certificateName)) {
            criteriaList.add(new PartMatchSearchCertificateCriteria(EntityFieldsName.NAME, certificateName));
        }
        if (isDescriptionValid(certificateDescription)) {
            criteriaList.add(new PartMatchSearchCertificateCriteria(EntityFieldsName.DESCRIPTION, certificateDescription));
        }
        if (sortByName != null && !sortByName.isEmpty()) {
            String sortOrdering = sortByName.equalsIgnoreCase(ASC_SORT_ORDERING) ? ASC_SORT_ORDERING
                    : DESC_SORT_ORDERING;
            criteriaList.add(new FieldSortCertificateCriteria(EntityFieldsName.NAME, sortOrdering));
        }
        if (sortByDate != null && !sortByDate.isEmpty()) {
            String sortOrdering = sortByDate.equalsIgnoreCase(ASC_SORT_ORDERING) ? ASC_SORT_ORDERING
                    : DESC_SORT_ORDERING;
            criteriaList.add(new FieldSortCertificateCriteria(EntityFieldsName.CREATE_DATE, sortOrdering));
        }
        return dao.findWithTags(page, elements, criteriaList);
    }

    private boolean updateGiftCertificateFields(GiftCertificate oldGiftCertificate,
                                                GiftCertificate newGiftCertificate) {
        boolean result = false;
        if (isNameValid(newGiftCertificate.getName())) {
            oldGiftCertificate.setName(newGiftCertificate.getName());
            result = true;
        }
        if (GiftCertificateValidator.isDescriptionValid(newGiftCertificate.getDescription())) {
            oldGiftCertificate.setDescription(newGiftCertificate.getDescription());
            result = true;
        }
        if (GiftCertificateValidator.isPriceValid(newGiftCertificate.getPrice())) {
            oldGiftCertificate.setPrice(newGiftCertificate.getPrice());
            result = true;
        }
        if (GiftCertificateValidator.isDurationValid(newGiftCertificate.getDuration())) {
            oldGiftCertificate.setDuration(newGiftCertificate.getDuration());
            result = true;
        }
        if (areGiftCertificateTagsValid(newGiftCertificate.getTags())) {
            oldGiftCertificate.setTags(newGiftCertificate.getTags());
            result = true;
        }
        return result;
    }
}
