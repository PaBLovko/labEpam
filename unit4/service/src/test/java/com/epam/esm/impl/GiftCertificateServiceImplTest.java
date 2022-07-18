package com.epam.esm.impl;

import com.epam.esm.*;
import com.epam.esm.api.GiftCertificateDao;
import com.epam.esm.api.TagService;
import com.epam.esm.constant.entity.GiftCertificateFieldName;
import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.creator.criteria.search.FullMatchSearchCertificateCriteria;
import com.epam.esm.creator.criteria.search.PartMatchSearchCertificateCriteria;
import com.epam.esm.creator.criteria.sort.FieldSortCertificateCriteria;
import com.epam.esm.exception.DeleteCertificateInUseException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {
    private static GiftCertificate giftCertificate;
    @Mock
    private GiftCertificateDao<GiftCertificate> dao;
    @Mock
    private TagService tagService;
    @Mock
    private GiftCertificateValidator giftCertificateValidator;
    @Mock
    private TagValidator tagValidator;
    @InjectMocks
    private GiftCertificateServiceImpl service;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        service = new GiftCertificateServiceImpl(dao, tagService,giftCertificateValidator, tagValidator);
        giftCertificate = new GiftCertificate(2, true, "Sand", "Yellow sand",
                new BigDecimal("2"), 24, null, null, null);
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
        Mockito.when(giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate)).thenReturn(true);
        Mockito.when(dao.insert(giftCertificate)).thenReturn(expected);
        long actual = service.insert(giftCertificate);
        assertEquals(expected, actual);
    }


    @Test
    void findWithTagsTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        List<Criteria<GiftCertificate>> criteriaList = new ArrayList<>();
        criteriaList.add(new FullMatchSearchCertificateCriteria("name", "#longverylongtagname"));
        List<GiftCertificate> actual = dao.findWithTags(0, 0, criteriaList);
        assertEquals(expected, actual);
    }

    @Test
    void findCertificatesWithTagsByCriteriaTest() {
        List<Criteria<GiftCertificate>> criteriaList = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        tags.add("#funny");
        tags.add("#cool");
        Tag tagFunny = new Tag(1,"#funny", true);
        Tag tagCool = new Tag(2,"#cool", true);

        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagFunny);
        tagList.add(tagCool);

        criteriaList.add(new FullMatchSearchCertificateCriteria(tagList));
        criteriaList.add(new PartMatchSearchCertificateCriteria(GiftCertificateFieldName.NAME, "Car"));
        criteriaList.add(new PartMatchSearchCertificateCriteria(GiftCertificateFieldName.DESCRIPTION, "Fast car"));
        criteriaList.add(new FieldSortCertificateCriteria(GiftCertificateFieldName.NAME, "ASC"));
        criteriaList.add(new FieldSortCertificateCriteria(GiftCertificateFieldName.CREATE_DATE, "ASC"));
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(giftCertificate);
        Mockito.when(tagValidator.isNameValid("#cool")).thenReturn(true);
        Mockito.when(tagValidator.isNameValid("#funny")).thenReturn(true);
        Mockito.when(giftCertificateValidator.isNameValid("Car")).thenReturn(true);
        Mockito.when(giftCertificateValidator.isDescriptionValid("Fast car")).thenReturn(true);
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

    @Test
    void updateTest() {
        Set<Tag> tags = new HashSet<>();
        Tag tag = new Tag(1, "name", false);
        tags.add(tag);
        String expected = giftCertificate.getName();
        giftCertificate.setTags(tags);
        GiftCertificate newGift = new GiftCertificate(2, true, "newGift", "Yellow sand",
                new BigDecimal("2"), 24, null, null, null);

        Mockito.when(dao.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        Mockito.when(tagService.findAll(0,0)).thenReturn(new ArrayList<>(tags));
        Mockito.when(giftCertificateValidator.isNameValid(giftCertificate.getName())).thenReturn(true);
        Mockito.when(giftCertificateValidator.isDescriptionValid(giftCertificate.getDescription())).thenReturn(true);
        Mockito.when(giftCertificateValidator.isPriceValid(giftCertificate.getPrice())).thenReturn(true);
        Mockito.when(giftCertificateValidator.isDurationValid(giftCertificate.getDuration())).thenReturn(true);
        Mockito.when(giftCertificateValidator.areGiftCertificateTagsValid(giftCertificate.getTags())).thenReturn(true);
        giftCertificate.setName("new");
        service.update(Long.toString(giftCertificate.getId()), giftCertificate);
        assertNotEquals(expected, giftCertificate.getName());
    }

    @Test()
    void updateInvalidFieldThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.update("", giftCertificate));
    }

    @Test()
    void deleteInvalidFieldThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.delete("", new ArrayList<>()));
    }

    @Test()
    void deleteCertificateInUseExceptionThrowTest() {
        Order order = new Order();
        order.setPrice(BigDecimal.valueOf(0.99));
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER, true);

        order.setUser(user);
        order.setTimestamp(LocalDateTime.of(2012, 10, 10, 11, 10, 11,
                111000000));
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(2, "#cool", true));
        tags.add(new Tag(1, "#funny", true));
        order.setGiftCertificate(new GiftCertificate(1, true, "Car", "Fast car",
                BigDecimal.valueOf(99.99), 4,
                LocalDateTime.of(2011, 11, 19, 11, 10, 11),
                null, tags));
        List orders = new ArrayList();
        orders.add(order);
        Mockito.when(dao.findById(order.getGiftCertificate().getId())).thenReturn(Optional.of(order.getGiftCertificate()));

        service.delete("1", orders);
        assertFalse(order.getGiftCertificate().isAvailable());
    }

    @Test()
    void deleteResourceNotFoundThrowTest() {
        Mockito.when(dao.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete("1", new ArrayList<>()));
    }

    @Test()
    void findByIdThrowTest() {
        assertThrows(InvalidFieldException.class, () -> service.findById("k"));
    }

    @Test()
    void insertThrowTest() {
        Mockito.when(giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate)).thenReturn(false);
        assertThrows(InvalidFieldException.class, () -> service.insert(giftCertificate));
    }

}
