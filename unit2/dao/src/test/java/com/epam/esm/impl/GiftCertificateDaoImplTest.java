package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.api.GiftCertificateDao;
import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.creator.SqlGiftCertificateQueryCreator;
import com.epam.esm.creator.criteria.Criteria;
import com.epam.esm.creator.criteria.search.FullMatchSearchCriteria;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.sql.SqlTagName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GiftCertificateDaoImplTest {
    private static final GiftCertificateDao<GiftCertificate> dao = new GiftCertificateDaoImpl
            (DataSourceConfig.dataSource, new GiftCertificateMapper(), new SqlGiftCertificateQueryCreator());

    @Test
    void findWithTagsTest() {
        List<GiftCertificate> expected = new ArrayList<>();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(new FullMatchSearchCriteria(SqlTagName.TAG_NAME, "#longverylongtagname"));
        List<GiftCertificate> actual = dao.findWithTags(criteriaList);
        assertEquals(expected, actual);
    }

    @Test
    void deleteTest() {
        boolean actual = dao.delete(12345);
        assertFalse(actual);
    }

    @Test
    void findByIdTest() {
        Optional<GiftCertificate> expected = Optional.of(new GiftCertificate(2, "Sand", "Yellow sand", new BigDecimal("2.35"), 24,
                LocalDateTime.of(2020, 5, 5, 23, 42, 12),
                null, new ArrayList<>()));
        Optional<GiftCertificate> actual = dao.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void disconnectAllTagsTest() {
        boolean actual = dao.disconnectAllTags(12345);
        assertFalse(actual);
    }
}
