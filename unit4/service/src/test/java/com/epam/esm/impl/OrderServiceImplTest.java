package com.epam.esm.impl;

import com.epam.esm.*;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderDao;
import com.epam.esm.api.UserService;
import com.epam.esm.constant.error.ErrorCode;
import com.epam.esm.constant.error.ErrorName;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        long expected = order.getId();
        when(dao.insert(order)).thenReturn(order.getId());
        when(userService.findById(String.valueOf(order.getUser().getId()), order.getUser().getEmail())).thenReturn(order.getUser());
        when(certificateService.findById(String.valueOf(order.getGiftCertificate().getId()))).thenReturn(
                order.getGiftCertificate());

        long actual = orderService.createOrder("2", "1", "pavel@gmail.com");
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        Order expected = new Order();
        expected.setPrice(BigDecimal.TEN);
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER, true);
        expected.setUser(user);
        expected.setTimestamp(LocalDateTime.now());
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(3, "#warm", true));
        expected.setGiftCertificate(new GiftCertificate(5, true, "Ferry", "Ferryman",
                BigDecimal.valueOf(0.99), 14, LocalDateTime.of(
                2019, 11, 19, 11, 10, 11, 111000000),
                null, tags));
        when(dao.findById(1L)).thenReturn(Optional.of(expected));
        Order actual = orderService.findById("1");
        assertEquals(expected, actual);
    }

    @Test
    void findByUserIdAndOrderIdTest() {
        User user = new User(2, "Pavel", "Kazhamiakin", "pavel@gmail.com",
                "$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W", UserRole.USER, true);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(2, "#cool", true));
        tags.add(new Tag(1, "#funny", true));
        GiftCertificate giftCertificate = new GiftCertificate(1, true, "Car", "Fast car",
                BigDecimal.valueOf(99.99), 4,
                LocalDateTime.of(2011, 11, 19, 11, 10, 11),
                null, tags);
        LocalDateTime localDateTime = LocalDateTime.of(2012, 10, 10, 11, 10, 11,
                111000000);
        Order expected = new Order(1L, BigDecimal.valueOf(0.99), localDateTime, giftCertificate, user);

        when(dao.findByUserIdAndOrderId(2, 1)).thenReturn(Optional.of(expected));

        when(userService.findById("2", "pavel@gmail.com")).thenReturn(expected.getUser());
        when(dao.findById(1)).thenReturn(Optional.of(expected));
        Order actual = orderService.findByUserIdAndOrderId("2", "1", "pavel@gmail.com");
        assertEquals(expected, actual);
    }

    @Test()
    void findByIdThrowTest() {
        assertThrows(InvalidFieldException.class, () -> orderService.findById("k"));
    }

    @Test()
    void findByUserIdAndOrderIdThrowTest() {
        when(dao.findByUserIdAndOrderId(10, 10)).thenThrow(new RuntimeException());
        when(userService.findById("10", "pave@gmail.com")).thenThrow(new ResourceNotFoundException(
                ErrorCode.USER, ErrorName.RESOURCE_NOT_FOUND, "10"));
        assertThrows(ResourceNotFoundException.class, () -> orderService.findByUserIdAndOrderId(
                "10", "10", "pave@gmail.com"));
    }

    @Test()
    void createOrderThrowTest() {
        when(userService.findById("k", "k")).thenThrow(new NumberFormatException());
        assertThrows(InvalidFieldException.class, () -> orderService.createOrder("k", "k", "k"));
    }
}
