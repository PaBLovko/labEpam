package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.Tag;
import com.epam.esm.User;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderDao;
import com.epam.esm.api.UserService;
import com.epam.esm.constant.ErrorAttribute;
import com.epam.esm.constant.Symbol;
import com.epam.esm.exception.DeleteCertificateInUseException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

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
        Order order = new Order();
        order.setPrice(BigDecimal.valueOf(0.99));
        order.setUser(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));
        order.setTimestamp(LocalDateTime.of(2012, 10, 10, 11, 10, 11,
                111000000));
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(2, "#cool"));
        tags.add(new Tag(1, "#funny"));
        order.setGiftCertificate(new GiftCertificate(1, "Car", "Fast car",
                BigDecimal.valueOf(99.99), 4,
                LocalDateTime.of(2011, 11, 19, 11, 10, 11),
                null, tags));
        long expected = order.getId();
        when(dao.insert(order)).thenReturn(order.getId());
        when(userService.findById(String.valueOf(order.getUser().getId()))).thenReturn(order.getUser());
        when(certificateService.findById(String.valueOf(order.getGiftCertificate().getId()))).thenReturn(
                order.getGiftCertificate());

        long actual = orderService.createOrder("1", "1");
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Order expected = new Order();
        expected.setPrice(BigDecimal.TEN);
        expected.setUser(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));
        expected.setTimestamp(LocalDateTime.now());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(3, "#warm"));
        expected.setGiftCertificate(new GiftCertificate(5, "Ferry", "Ferryman",
                BigDecimal.valueOf(0.99), 14, LocalDateTime.of(
                        2019, 11, 19, 11, 10, 11, 111000000),
                null, tags));
        when(dao.findById(1L)).thenReturn(Optional.of(expected));
        Order actual = orderService.findById("1");
        assertEquals(expected, actual);
    }

    @Test
    void deleteByCertificateIdTest() {
        Order order = new Order();
        order.setPrice(BigDecimal.TEN);
        order.setUser(new User(1, "Pablo", "Escobar", "pablo@gmail.com"));
        order.setTimestamp(LocalDateTime.now());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(3, "#warm"));
        GiftCertificate certificate = new GiftCertificate(5, "Ferry", "Ferryman",
                BigDecimal.valueOf(0.99), 26, LocalDateTime.now(), null, tags);
        order.setGiftCertificate(certificate);
        when(certificateService.findById("1")).thenReturn(certificate);
        when(dao.findByCertificateId(5)).thenReturn(Collections.singletonList(order));
        assertThrows(DeleteCertificateInUseException.class, () -> orderService.deleteByCertificateId("1"));
    }

    @Test
    void findByUserIdAndOrderIdTest() {
        User user = new User(1, "Pablo", "Escobar", "pablo@gmail.com");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(2, "#cool"));
        tags.add(new Tag(1, "#funny"));
        GiftCertificate giftCertificate = new GiftCertificate(1, "Car", "Fast car",
                BigDecimal.valueOf(99.99), 4,
                LocalDateTime.of(2011, 11, 19, 11, 10, 11),
                null, tags);
        LocalDateTime localDateTime = LocalDateTime.of(2012, 10, 10, 11, 10, 11,
                111000000);
        Order expected = new Order(1L, BigDecimal.valueOf(0.99), localDateTime, giftCertificate, user);

        when(dao.findByUserIdAndOrderId(1, 1)).thenReturn(Optional.of(expected));

        when(userService.findById("1")).thenReturn(expected.getUser());
        when(dao.findById(1)).thenReturn(Optional.of(expected));
        Order actual = orderService.findByUserIdAndOrderId("1","1");
        assertEquals(expected, actual);
    }

    @Test()
    void findByIdThrowTest() {
        assertThrows(InvalidFieldException.class, () -> orderService.findById("k"));
    }

    @Test()
    void findByUserIdAndOrderIdThrowTest() {
        when(dao.findByUserIdAndOrderId(10,10)).thenThrow(new RuntimeException());
        when(userService.findById("10")).thenThrow(new ResourceNotFoundException(
                ErrorAttribute.USER_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, "10"));
        assertThrows(ResourceNotFoundException.class, () -> orderService.findByUserIdAndOrderId("10", "10"));
    }

    @Test()
    void createOrderThrowTest() {
        when(userService.findById("k")).thenThrow(new NumberFormatException());
        assertThrows(InvalidFieldException.class, () -> orderService.createOrder("k", "k"));
    }
}
