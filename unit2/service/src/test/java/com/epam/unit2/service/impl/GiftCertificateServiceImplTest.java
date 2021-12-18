package com.epam.unit2.service.impl;

import com.epam.unit2.dao.api.GiftCertificateDao;
import com.epam.unit2.model.bean.GiftCertificate;
import com.epam.unit2.service.api.GiftCertificateService;
import com.epam.unit2.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {
    private static GiftCertificate giftCertificate;
    private GiftCertificateService<GiftCertificate> service;
    @Mock
    private GiftCertificateDao<GiftCertificate> dao;


    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        service = new GiftCertificateServiceImpl(dao, null);
        giftCertificate = new GiftCertificate(2, "Sand", "Yellow sand", new BigDecimal("2"), 24,
                LocalDateTime.of(2020, 5, 5, 23, 42, 12, 112000000),
                null, new ArrayList<>());
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
    void findByIdTest() throws ServiceException {
        GiftCertificate expected = giftCertificate;
        Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
        GiftCertificate actual = service.findById("1");
        assertEquals(expected, actual);
    }

    @Test()
    void insertTest() {
        assertThrows(ExceptionInInitializerError.class, () -> service.insert(giftCertificate));
    }
}
