package com.epam.esm.impl;

import com.epam.esm.Tag;
import com.epam.esm.api.TagDao;
import com.epam.esm.api.TagService;
import com.epam.esm.constant.EntityFieldsName;
import com.epam.esm.creator.criteria.search.PartMatchSearchCertificateCriteria;
import com.epam.esm.creator.criteria.sort.FieldSortCertificateCriteria;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.api.GiftCertificateDao;
import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.creator.criteria.search.FullMatchSearchCertificateCriteria;
import com.epam.esm.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {
    private static GiftCertificate giftCertificate;
    @Mock
    private GiftCertificateDao<GiftCertificate> dao;
    @Mock
    private TagService tagService;
    @InjectMocks
    private GiftCertificateServiceImpl service;

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
        Mockito.when(dao.findAll(0, 0)).thenReturn(expected);
        List<GiftCertificate> actual = service.findAll(0, 0);
        assertEquals(expected, actual);
    }

    @Test
    void insertTest() {
        long expected = giftCertificate.getId();
        giftCertificate.setCreateDate(null);
        giftCertificate.setLastUpdateDate(null);
        Mockito.when(dao.insert(giftCertificate)).thenReturn(expected);
        long actual = service.insert(giftCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void updateTest() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag(1, "name");
        tags.add(tag);
        giftCertificate.setTags(tags);
        Mockito.when(dao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        Mockito.when(tagService.findAll(0,0)).thenReturn(new ArrayList<>(tags));
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
    void findWithTagsTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        List<Criteria<GiftCertificate>> criteriaList = new ArrayList<>();
        criteriaList.add(new FullMatchSearchCertificateCriteria(EntityFieldsName.NAME, "#longverylongtagname"));
        List<GiftCertificate> actual = dao.findWithTags(0, 0, criteriaList);
        assertEquals(expected, actual);
    }

    @Test
    void findCertificatesWithTagsByCriteriaTest() {
        List<Criteria<GiftCertificate>> criteriaList = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        tags.add("#funny");
        tags.add("#cool");
        Tag tagFunny = new Tag(1,"#funny");
        Tag tagCool = new Tag(2,"#cool");

        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagFunny);
        tagList.add(tagCool);

        criteriaList.add(new FullMatchSearchCertificateCriteria(tagList));
        criteriaList.add(new PartMatchSearchCertificateCriteria(EntityFieldsName.NAME, "Car"));
        criteriaList.add(new PartMatchSearchCertificateCriteria(EntityFieldsName.DESCRIPTION, "Fast car"));
        criteriaList.add(new FieldSortCertificateCriteria(EntityFieldsName.NAME, "ASC"));
        criteriaList.add(new FieldSortCertificateCriteria(EntityFieldsName.CREATE_DATE, "ASC"));
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(giftCertificate);
        Mockito.when(tagService.findByName("#cool")).thenReturn(tagCool);
        Mockito.when(tagService.findByName("#funny")).thenReturn(tagFunny);
        Mockito.when(dao.findWithTags(0,0, criteriaList)).thenReturn(expected);
        List<GiftCertificate> actual = service.findCertificatesWithTagsByCriteria(0,0, tags,
                "Car", "Fast car", "ASC", "ASC");
        assertEquals(expected, actual);
    }

    @Test
    void findCertificatesWithTagsByCriteriaThrowTest() {
        List<Criteria<GiftCertificate>> criteriaList = new ArrayList<Criteria<GiftCertificate>>();
        List<GiftCertificate> expected = new ArrayList<>();
        Mockito.when(dao.findWithTags(0,0, criteriaList)).thenReturn(expected);
        List<GiftCertificate> actual = service.findCertificatesWithTagsByCriteria(0,0,null,
                null, null, null, null);
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
        assertThrows(InvalidFieldException.class, () -> service.update("", giftCertificate));
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
