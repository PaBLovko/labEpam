package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.Tag;
import com.epam.esm.api.GiftCertificateDao;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.TagService;
import com.epam.esm.constant.entity.GiftCertificateFieldName;
import com.epam.esm.constant.error.ErrorCode;
import com.epam.esm.constant.error.ErrorName;
import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.creator.criteria.search.FullMatchSearchCertificateCriteria;
import com.epam.esm.creator.criteria.search.PartMatchSearchCertificateCriteria;
import com.epam.esm.creator.criteria.sort.FieldSortCertificateCriteria;
import com.epam.esm.exception.DeleteCertificateInUseException;
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

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String ASC_SORT_ORDERING = "ASC";
    private static final String DESC_SORT_ORDERING = "DESC";
    private final GiftCertificateDao<GiftCertificate> dao;
    private final TagService tagService;
    private final GiftCertificateValidator certificateValidator;
    private final TagValidator tagValidator;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao<GiftCertificate> dao, TagService tagService,
                                      GiftCertificateValidator certificateValidator, TagValidator tagValidator) {
        this.dao = dao;
        this.tagService = tagService;
        this.certificateValidator = certificateValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    public long insert(GiftCertificate giftCertificate) {
        long id;
        if (certificateValidator.isGiftCertificateCreationFormValid(giftCertificate)) {
            updateCertificateBeforeInsert(giftCertificate);
            if (CollectionUtils.isNotEmpty(giftCertificate.getTags())) {
                Set<Tag> allTags = new HashSet<>(tagService.findAll(0,0));
                Set<Tag> existingTags = SetUtils.intersection(allTags, giftCertificate.getTags());
                if (!existingTags.isEmpty()) {
                    existingTags.stream().filter(t -> !t.isAvailable()).peek(t -> t.setAvailable(true))
                            .forEach(t -> tagService.updateAvailability(String.valueOf(t.getId()), true));
                }
                Set<Tag> newTags = new HashSet<>(CollectionUtils.removeAll(giftCertificate.getTags(), existingTags));
                newTags.forEach(t -> t.setAvailable(true));
                giftCertificate.setTags(newTags);
                id = dao.insert(giftCertificate);
                GiftCertificate certificateWithAllTags = dao.findById(id).get();
                certificateWithAllTags.setTags(SetUtils.union(newTags, existingTags));
                dao.update(certificateWithAllTags);
            } else {
                id = dao.insert(giftCertificate);
            }
        } else {
            throw new InvalidFieldException(ErrorCode.GIFT_CERTIFICATE, ErrorName.INVALID_GIFT_CERTIFICATE,
                    giftCertificate.toString());
        }
        return id;
    }

    @Override
    public void delete(String id, List<Order> orders) {
        try {
            GiftCertificate certificate = dao.findById(Long.parseLong(id)).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorCode.GIFT_CERTIFICATE, ErrorName.RESOURCE_NOT_FOUND, id));
            if (CollectionUtils.isEmpty(orders)) {
                if (CollectionUtils.isNotEmpty(certificate.getTags())) {
                    dao.disconnectAllTags(certificate);
                }
                dao.delete(certificate.getId());
            } else {
                if (LocalDateTime.now().isAfter(certificate.getCreateDate().plusDays(certificate.getDuration()))) {
                    certificate.setAvailable(false);
                    dao.update(certificate);
                } else {
                    throw new DeleteCertificateInUseException(ErrorCode.ORDER, ErrorName.CERTIFICATE_IN_USE, id);
                }
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorCode.GIFT_CERTIFICATE, ErrorName.INVALID_GIFT_CERTIFICATE_ID, id);
        }
    }

    @Override
    public void update(String id, GiftCertificate newGiftCertificate) {
        try {
            GiftCertificate oldCertificate = dao.findById(Long.parseLong(id)).orElseThrow(() ->
                    new ResourceNotFoundException(ErrorCode.GIFT_CERTIFICATE,
                            ErrorName.RESOURCE_NOT_FOUND, id));

            if (!oldCertificate.isAvailable()) {
                throw new ResourceNotFoundException(ErrorCode.GIFT_CERTIFICATE,
                        ErrorName.RESOURCE_NOT_FOUND, id);
            }

            if (updateGiftCertificateFields(oldCertificate, newGiftCertificate)) {
                oldCertificate.setLastUpdateDate(LocalDateTime.now());
                if (CollectionUtils.isNotEmpty(oldCertificate.getTags())) {
                    List<Tag> existingTags = (List<Tag>) CollectionUtils.intersection(
                            tagService.findAll(0,0), oldCertificate.getTags());

                    existingTags.stream().filter(t -> !t.isAvailable()).peek(t -> t.setAvailable(true))
                            .forEach(t -> tagService.updateAvailability(String.valueOf(t.getId()), true));

                    List<Tag> newTags = (List<Tag>) CollectionUtils.removeAll(oldCertificate.getTags(), existingTags);
                    newTags.forEach(tagService::insert);

                    Set<Tag> certificateTags = new HashSet<>(existingTags);
                    certificateTags.addAll(newTags);
                    oldCertificate.setTags(certificateTags);
                }
                dao.update(oldCertificate);
            } else {
                throw new InvalidFieldException(ErrorCode.GIFT_CERTIFICATE, ErrorName.INVALID_GIFT_CERTIFICATE,
                        newGiftCertificate.toString());
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorCode.GIFT_CERTIFICATE, ErrorName.INVALID_GIFT_CERTIFICATE_ID, id);
        }
    }

    @Override
    public GiftCertificate findById(String id) {
        try {
            Optional<GiftCertificate> certificateOptional = dao.findById(Long.parseLong(id));
            if (!certificateOptional.isPresent() || !certificateOptional.get().isAvailable()) {
                throw new ResourceNotFoundException(ErrorCode.GIFT_CERTIFICATE, ErrorName.RESOURCE_NOT_FOUND, id);
            }
            return certificateOptional.get();
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorCode.GIFT_CERTIFICATE,
                    ErrorName.INVALID_GIFT_CERTIFICATE_ID, id);
        }
    }

    @Override
    public List<GiftCertificate> findAll(int page, int elements) {
        return dao.findAll(page, elements);
    }

    @Override
    public List<GiftCertificate> findCertificatesWithTagsByCriteria(int page, int elements,List<String> tagsNames,
                                                                    String certificateName,
                                                                    String certificateDescription, String sortByName,
                                                                    String sortByDate) {
        List<Criteria<GiftCertificate>> certificateCriteriaList = new ArrayList<>();
        addCriteriaForCertificateTagsNames(tagsNames, certificateCriteriaList);
        addCriteriaForCertificateName(certificateName, certificateCriteriaList);
        addCriteriaForCertificateDescription(certificateDescription, certificateCriteriaList);
        addCriteriaForCertificateNameSorting(sortByName, certificateCriteriaList);
        addCriteriaForCertificateDateSorting(sortByDate, certificateCriteriaList);
        return dao.findWithTags(page, elements, certificateCriteriaList);
    }

    private boolean updateGiftCertificateFields(GiftCertificate oldGiftCertificate,
                                                GiftCertificate newGiftCertificate) {
        boolean result = false;
        if (certificateValidator.isNameValid(newGiftCertificate.getName())) {
            oldGiftCertificate.setName(newGiftCertificate.getName());
            result = true;
        }
        if (certificateValidator.isDescriptionValid(newGiftCertificate.getDescription())) {
            oldGiftCertificate.setDescription(newGiftCertificate.getDescription());
            result = true;
        }
        if (certificateValidator.isPriceValid(newGiftCertificate.getPrice())) {
            oldGiftCertificate.setPrice(newGiftCertificate.getPrice());
            result = true;
        }
        if (certificateValidator.isDurationValid(newGiftCertificate.getDuration())) {
            oldGiftCertificate.setDuration(newGiftCertificate.getDuration());
            result = true;
        }
        if (certificateValidator.areGiftCertificateTagsValid(newGiftCertificate.getTags())) {
            oldGiftCertificate.setTags(newGiftCertificate.getTags());
            result = true;
        }
        return result;
    }

    private void updateCertificateBeforeInsert(GiftCertificate certificate) {
        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setCreateDate(currentTime);
        certificate.setLastUpdateDate(currentTime);
        certificate.setAvailable(true);
    }

    private void addCriteriaForCertificateTagsNames(List<String> tagsNames, List<Criteria<GiftCertificate>> certificateCriteriaList) {
        if (CollectionUtils.isNotEmpty(tagsNames) && tagsNames.stream().allMatch(tagValidator::isNameValid)) {
            List<Tag> tags = new ArrayList<>();
            tagsNames.forEach(t -> tags.add(tagService.findByName(t)));
            certificateCriteriaList.add(new FullMatchSearchCertificateCriteria(tags));
        }
    }

    private void addCriteriaForCertificateName(String certificateName, List<Criteria<GiftCertificate>> certificateCriteriaList) {
        if (certificateValidator.isNameValid(certificateName)) {
            certificateCriteriaList.add(new PartMatchSearchCertificateCriteria(GiftCertificateFieldName.NAME,
                    certificateName));
        }
    }

    private void addCriteriaForCertificateDescription(String certificateDescription, List<Criteria<GiftCertificate>> certificateCriteriaList) {
        if (certificateValidator.isDescriptionValid(certificateDescription)) {
            certificateCriteriaList.add(new PartMatchSearchCertificateCriteria(GiftCertificateFieldName.DESCRIPTION,
                    certificateDescription));
        }
    }

    private void addCriteriaForCertificateNameSorting(String sortByName, List<Criteria<GiftCertificate>> certificateCriteriaList) {
        if (sortByName != null && !sortByName.isEmpty()) {
            String sortOrdering = sortByName.equalsIgnoreCase(ASC_SORT_ORDERING) ? ASC_SORT_ORDERING
                    : DESC_SORT_ORDERING;
            certificateCriteriaList.add(new FieldSortCertificateCriteria(GiftCertificateFieldName.NAME, sortOrdering));
        }
    }

    private void addCriteriaForCertificateDateSorting(String sortByDate, List<Criteria<GiftCertificate>> certificateCriteriaList) {
        if (sortByDate != null && !sortByDate.isEmpty()) {
            String sortOrdering = sortByDate.equalsIgnoreCase(ASC_SORT_ORDERING) ? ASC_SORT_ORDERING
                    : DESC_SORT_ORDERING;
            certificateCriteriaList.add(new FieldSortCertificateCriteria(GiftCertificateFieldName.CREATE_DATE,
                    sortOrdering));
        }
    }
}
