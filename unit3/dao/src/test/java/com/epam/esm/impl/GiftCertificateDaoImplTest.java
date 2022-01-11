package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.api.GiftCertificateDao;
import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.constant.EntityFieldsName;
import com.epam.esm.creator.SqlGiftCertificateQueryCreator;
import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.creator.criteria.search.FullMatchSearchCertificateCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.Lifecycle;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@ContextConfiguration(classes = {GiftCertificateDaoImpl.class, SqlGiftCertificateQueryCreator.class,
        DataSourceConfig.class}, loader = AnnotationConfigContextLoader.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GiftCertificateDaoImplTest {
    private GiftCertificate certificate;
    @Autowired
    private GiftCertificateDao<GiftCertificate> dao;

    @BeforeEach
    void init() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("New Test Certificate");
        certificate.setDescription("Description (test)");
        certificate.setPrice(BigDecimal.ONE);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setDuration(11);
        certificate.setLastUpdateDate(null);
        certificate.setTags(null);
        this.certificate = certificate;
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
    void insertTest() {
        long expected = 6;
        long actual = dao.insert(certificate);
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        boolean actual = dao.delete(6);
        assertTrue(actual);
    }

    @Test
    void findByIdTest() {
        Optional<GiftCertificate> expected = Optional.empty();
        Optional<GiftCertificate> actual = dao.findById(12345);
        assertEquals(expected, actual);
    }
}
