package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.Tag;
import com.epam.esm.User;
import com.epam.esm.api.OrderDao;
import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.creator.SqlGiftCertificateQueryCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DirtiesContext
@ContextConfiguration(classes = {OrderDaoImpl.class, SqlGiftCertificateQueryCreator.class,
        DataSourceConfig.class}, loader = AnnotationConfigContextLoader.class)
@SpringBootTest
class OrderDaoImplTest {

    @Autowired
    private OrderDao<Order> dao;

    @Test
    void insertTest() {
        Order order = new Order();
        order.setPrice(BigDecimal.TEN);
        order.setUser(new User(1, "Alice", "Green", "alice@gmail.com"));
        order.setTimestamp(LocalDateTime.now());

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(3, "#warm"));

        order.setGiftCertificate(new GiftCertificate(5, "Ferry", "Ferryman", BigDecimal.valueOf(0.99), 14,
                LocalDateTime.of(2019, 11, 19, 11, 10, 11, 111000000), null, tags));

        long expected = 6;
        long actual = dao.insert(order);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Optional<Order> expected = Optional.empty();
        Optional<Order> actual = dao.findById(12345);
        assertEquals(expected, actual);
    }

    @Test
    void deleteByCertificateIdTest() {
        boolean actual = dao.deleteByCertificateId(1);
        assertTrue(actual);
    }

    @Test
    void findByUserIdAndOrderIdTest() {
        Order expected = new Order();
        expected.setPrice(BigDecimal.valueOf(0.99));
        expected.setUser(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));
        expected.setTimestamp(LocalDateTime.of(2012, 10, 10, 11, 10, 11, 111000000));

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(2, "#cool"));
        tags.add(new Tag(1, "#funny"));
        expected.setGiftCertificate(new GiftCertificate(1, "Car", "Fast car", BigDecimal.valueOf(99.99), 4,
                LocalDateTime.of(2011, 11, 19, 11, 10, 11
                ), null, tags));

        Order actual = dao.findByUserIdAndOrderId(1L,1L).get();
        assertEquals(expected, actual);
    }
}
