package com.epam.esm.impl;

import com.epam.esm.*;
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
        order.setUser(new User(1, "Pablo", "Escobar", "pablo@gmail.com",
                "password", UserRole.ADMIN, true));
        order.setTimestamp(LocalDateTime.now());

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(3, "#warm", true));

        order.setGiftCertificate(new GiftCertificate(5,true, "Ferry", "Ferryman",
                BigDecimal.valueOf(0.99), 14,
                LocalDateTime.of(2019, 11, 19, 11, 10, 11,
                        111000000), null, tags));

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

}
