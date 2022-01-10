package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.Tag;
import com.epam.esm.User;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderDao;
import com.epam.esm.api.UserService;
import com.epam.esm.exception.DeleteCertificateInUseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    private OrderDao<Order> dao;

    @Mock
    private UserService userService;

    @Mock
    private GiftCertificateService certificateService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createOrderTest() {
        long expected = 11;
        Mockito.when(dao.insert(Mockito.any()))
                .thenReturn(expected);

        Mockito.when(userService.findById(Mockito.anyString()))
                .thenReturn(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));

        Mockito.when(certificateService.findById(Mockito.anyString()))
                .thenReturn(new GiftCertificate(2, "Sand", "Yellow sand", new BigDecimal("2"),
                        24, LocalDateTime.of(2020, 5, 5, 23, 42, 12,
                        112000000), null, new HashSet<>()));

        long actual = orderService.createOrder("1", "1");
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Order expected = new Order();
        expected.setCost(BigDecimal.TEN);
        expected.setUser(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));
        expected.setTimestamp(LocalDateTime.now());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(3, "#warm"));
        expected.setGiftCertificate(new GiftCertificate(5, "Ferry", "Ferryman",
                BigDecimal.valueOf(0.99), 14, LocalDateTime.of(
                        2019, 11, 19, 11, 10, 11, 111000000),
                null, tags));
        Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
        Order actual = orderService.findById("11");
        assertEquals(expected, actual);
    }

    @Test
    void deleteByCertificateIdTest() {
        Order order = new Order();
        order.setCost(BigDecimal.TEN);
        order.setUser(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));
        order.setTimestamp(LocalDateTime.now());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(3, "#warm"));
        GiftCertificate certificate = new GiftCertificate(5, "Ferry", "Ferryman",
                BigDecimal.valueOf(0.99), 26, LocalDateTime.now(), null, tags);
        order.setGiftCertificate(certificate);
        Mockito.when(certificateService.findById(Mockito.anyString())).thenReturn(certificate);
        Mockito.when(dao.findByCertificateId(Mockito.anyLong())).thenReturn(Collections.singletonList(order));
        assertThrows(DeleteCertificateInUseException.class, () -> orderService.deleteByCertificateId("1"));
    }
}
