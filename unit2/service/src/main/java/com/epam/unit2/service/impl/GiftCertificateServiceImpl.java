package com.epam.unit2.service.impl;

import com.epam.unit2.dao.api.GiftCertificateDao;
import com.epam.unit2.dao.creator.criteria.Criteria;
import com.epam.unit2.dao.creator.criteria.search.FullMatchSearchCriteria;
import com.epam.unit2.dao.creator.criteria.search.PartMatchSearchCriteria;
import com.epam.unit2.dao.creator.criteria.sort.FieldSortCriteria;
import com.epam.unit2.dao.sql.SqlGiftCertificateName;
import com.epam.unit2.dao.sql.SqlTagName;
import com.epam.unit2.model.bean.GiftCertificate;
import com.epam.unit2.model.bean.Tag;
import com.epam.unit2.service.api.GiftCertificateService;
import com.epam.unit2.service.api.TagService;
import com.epam.unit2.service.exception.InvalidFieldException;
import com.epam.unit2.service.exception.ResourceNotFoundException;
import com.epam.unit2.service.validator.GiftCertificateValidator;
import com.epam.unit2.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {
    private static final String ASC_SORT_ORDERING = "ASC";
    private static final String DESC_SORT_ORDERING = "DESC";
    private final GiftCertificateDao<GiftCertificate> dao;
    private final TagService<Tag> tagService;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao<GiftCertificate> dao, TagService<Tag> tagService) {
        this.dao = dao;
        this.tagService = tagService;
    }

    @Transactional
    @Override
    public boolean insert(GiftCertificate giftCertificate) {
        if (GiftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate)) {
            giftCertificate.setCreateDate(LocalDateTime.now());
                if (giftCertificate.getTags() != null && saveNewTags(giftCertificate, tagService.findAll())) {
                    List<Tag> tagsWithId = new ArrayList<>();
                    giftCertificate.getTags().forEach(t -> tagsWithId.add(tagService.findByName(t.getName())));
                    giftCertificate.setTags(tagsWithId);
                }
                return dao.insert(giftCertificate);
        } else {
            throw new InvalidFieldException("Invalid gift certificate: " + giftCertificate);
        }
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        try {
            Optional<GiftCertificate> giftCertificateOptional = dao.findById(Long.parseLong(id));
            if (giftCertificateOptional.isPresent()) {
                GiftCertificate giftCertificate = giftCertificateOptional.get();
                if (giftCertificate.getTags() != null && !giftCertificate.getTags().isEmpty()) {
                    dao.disconnectAllTags(giftCertificate.getId());
                }
                return dao.delete(giftCertificate.getId());
            } else {
                throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldException("Invalid tag id (id = " + id + ")", e);
        }
    }

    @Transactional
    @Override
    public boolean update(String id, GiftCertificate newGiftCertificate) {
        try {
            GiftCertificate oldGiftCertificate = dao.findById(Long.parseLong(id)).orElseThrow(() ->
                    new ResourceNotFoundException("Requested resource not found (id = " + id + ")"));
            if (updateGiftCertificateFields(oldGiftCertificate, newGiftCertificate) &&
                    saveNewTags(oldGiftCertificate, tagService.findAll())) {
                oldGiftCertificate.setLastUpdateDate(LocalDateTime.now());
                List<Tag> connectedTags = tagService.findTagsConnectedToCertificate(id);
                List<Tag> notConnectedTags = tagService.findAll().stream()
                        .filter(t -> !connectedTags.contains(t) && oldGiftCertificate.getTags().contains(t))
                        .collect(Collectors.toList());
                dao.connectTags(notConnectedTags, oldGiftCertificate.getId());
                return dao.update(oldGiftCertificate);
            } else {
                throw new InvalidFieldException("Invalid gift certificate: " + newGiftCertificate);
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldException("Invalid tag id (id = " + id + ")", e);
        }
    }

    @Override
    public GiftCertificate findById(String id) {
        try {
            return dao.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException("Requested" +
                    " resource not found (id = " + id + ")"));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException("Invalid tag id (id = " + id + ")", e);
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return dao.findAll();
    }

    @Override
    public List<GiftCertificate> findCertificatesWithTagsByCriteria(String tagName, String certificateName,
                                                                    String certificateDescription, String sortByName,
                                                                    String sortByDate) {
        List<Criteria> criteriaList = new ArrayList<>();
        if (TagValidator.isNameValid(tagName)) {
            criteriaList.add(new FullMatchSearchCriteria(SqlTagName.TAG_NAME, tagName));
        }
        if (GiftCertificateValidator.isNameValid(certificateName)) {
            criteriaList.add(new PartMatchSearchCriteria(SqlGiftCertificateName.NAME, certificateName));
        }
        if (GiftCertificateValidator.isDescriptionValid(certificateDescription)) {
            criteriaList.add(new PartMatchSearchCriteria(SqlGiftCertificateName.DESCRIPTION, certificateDescription));
        }
        if (sortByName != null && !sortByName.isEmpty()) {
            String sortOrdering = sortByName.equalsIgnoreCase(ASC_SORT_ORDERING) ? ASC_SORT_ORDERING
                    : DESC_SORT_ORDERING;
            criteriaList.add(new FieldSortCriteria(SqlGiftCertificateName.NAME, sortOrdering));
        }
        if (sortByDate != null && !sortByDate.isEmpty()) {
            String sortOrdering = sortByDate.equalsIgnoreCase(ASC_SORT_ORDERING) ? ASC_SORT_ORDERING
                    : DESC_SORT_ORDERING;
            criteriaList.add(new FieldSortCriteria(SqlGiftCertificateName.CREATE_DATE, sortOrdering));
        }
        return dao.findWithTags(criteriaList);
    }

    private boolean saveNewTags(GiftCertificate giftCertificate, List<Tag> existingTags) {
        return giftCertificate.getTags().stream().filter(t -> !existingTags.contains(t)).allMatch(tagService::insert);
    }

    private boolean updateGiftCertificateFields(GiftCertificate oldGiftCertificate,
                                                GiftCertificate newGiftCertificate) {
        boolean result = false;
        if (GiftCertificateValidator.isNameValid(newGiftCertificate.getName())) {
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
        if (GiftCertificateValidator.areGiftCertificateTagsValid(newGiftCertificate.getTags())) {
            oldGiftCertificate.setTags(newGiftCertificate.getTags());
            result = true;
        }
        return result;
    }
}
