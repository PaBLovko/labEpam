package com.epam.esm.mapper;


import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.sql.SqlGiftCertificateQuery;
import com.epam.esm.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GiftCertificateMapperTest {
    @Test
    void extractDataTest() throws SQLException {
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(new GiftCertificate(2, "Sand", "Yellow sand", new BigDecimal("2.35"), 24,
                LocalDateTime.of(2020, 5, 5, 23, 42, 12),
                null, new ArrayList<>()));

        PreparedStatement preparedStatement = DataSourceConfig.dataSource.getConnection()
                .prepareStatement(SqlGiftCertificateQuery.SQL_SELECT_CERTIFICATE_BY_ID);
        preparedStatement.setLong(1, 2);
        ResultSet rs = preparedStatement.executeQuery();

        List<GiftCertificate> actual = new GiftCertificateMapper().extractData(rs);
        assertEquals(expected, actual);
    }
}
