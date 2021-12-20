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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {
    private static GiftCertificate giftCertificate;
    private GiftCertificateService service;

    @Mock
    private GiftCertificateDao<GiftCertificate> dao;
    @Mock
    private TagService tagService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        service = new GiftCertificateServiceImpl(dao, tagService);
        giftCertificate = new GiftCertificate(2, "Sand", "Yellow sand", new BigDecimal("2"),
                24, null, null, null);
    }

    @Test
    void findAllTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(giftCertificate);
        Mockito.when(dao.findAll()).thenReturn(expected);
        List<GiftCertificate> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void insertTest() {
        giftCertificate.setCreateDate(null);
        giftCertificate.setLastUpdateDate(null);
        Mockito.when(dao.insert(giftCertificate)).thenReturn(true);
        boolean actual = service.insert(giftCertificate);
        assertTrue(actual);
    }

    @Test
    void updateTest() {
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag(1, "name");
        tags.add(tag);
        giftCertificate.setTags(tags);
        Mockito.when(dao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        Mockito.when(tagService.insert(tag)).thenReturn(true);
        Mockito.when(dao.connectTags(new ArrayList<>(), 2)).thenReturn(true);
        Mockito.when(dao.update(giftCertificate)).thenReturn(true);
        giftCertificate.setName("new");
        boolean actual = service.update(Long.toString(giftCertificate.getId()), giftCertificate);
        assertTrue(actual);
    }

    @Test
    void deleteTest() {
        Mockito.when(dao.findById(2)).thenReturn(Optional.of(giftCertificate));
        Mockito.when(dao.delete(2)).thenReturn(true);
        boolean actual = service.delete(Long.toString(giftCertificate.getId()));
        assertTrue(actual);
    }

    @Test
    void findCertificatesWithTagsByCriteriaTest() {
        List<Criteria> criteriaList = new ArrayList<Criteria>();
        criteriaList.add(new FullMatchSearchCriteria(SqlTagName.TAG_NAME, "1"));
        criteriaList.add(new PartMatchSearchCriteria(SqlGiftCertificateName.NAME, "Car"));
        criteriaList.add(new PartMatchSearchCriteria(SqlGiftCertificateName.DESCRIPTION, "Fast car"));
        criteriaList.add(new FieldSortCriteria(SqlGiftCertificateName.NAME, "ASC"));
        criteriaList.add(new FieldSortCriteria(SqlGiftCertificateName.CREATE_DATE, "ASC"));
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(giftCertificate);
        Mockito.when(dao.findWithTags(criteriaList)).thenReturn(expected);
        List<GiftCertificate> actual = service.findCertificatesWithTagsByCriteria("1", "Car",
                "Fast car", "ASC", "ASC");
        assertEquals(expected, actual);
    }

    @Test
    void findCertificatesWithTagsByCriteriaThrowTest() {
        List<Criteria> criteriaList = new ArrayList<Criteria>();
        criteriaList.add(new FullMatchSearchCriteria(SqlTagName.TAG_NAME, null));
        criteriaList.add(new PartMatchSearchCriteria(SqlGiftCertificateName.NAME, null));
        criteriaList.add(new PartMatchSearchCriteria(SqlGiftCertificateName.DESCRIPTION, null));
        criteriaList.add(new FieldSortCriteria(SqlGiftCertificateName.NAME, null));
        criteriaList.add(new FieldSortCriteria(SqlGiftCertificateName.CREATE_DATE, null));
        List<GiftCertificate> expected = new ArrayList<>();
        Mockito.when(dao.findWithTags(criteriaList)).thenReturn(expected);
        List<GiftCertificate> actual = service.findCertificatesWithTagsByCriteria(null, null,
                null, null, null);
        assertEquals(expected, actual);
    }


    @Test
    void findByIdTest() {
        GiftCertificate expected = giftCertificate;
        Mockito.when(dao.findById(2)).thenReturn(Optional.of(expected));
        GiftCertificate actual = service.findById("2");
        assertEquals(expected, actual);
    }

    @Test()
    void updateThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.update("",giftCertificate));
    }

    @Test()
    void deleteInvalidThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.delete(""));
    }

    @Test()
    void deleteThrowTest() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete("1"));
    }

    @Test()
    void findByIdThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.findById("k"));
    }

    @Test()
    void insertThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.insert(giftCertificate));
    }
}
