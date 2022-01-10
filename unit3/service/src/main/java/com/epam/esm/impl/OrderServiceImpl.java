package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderDao;
import com.epam.esm.api.OrderService;
import com.epam.esm.api.UserService;
import com.epam.esm.constant.ErrorAttribute;
import com.epam.esm.constant.Symbol;
import com.epam.esm.exception.DeleteCertificateInUseException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao<Order> dao;
    private final UserService userService;
    private final GiftCertificateService certificateService;

    @Autowired
    public OrderServiceImpl(OrderDao<Order> dao, UserService userService,
                            GiftCertificateService certificateService) {
        this.dao = dao;
        this.userService = userService;
        this.certificateService = certificateService;
    }

    @Override
    public List<Order> findByUserId(int page, int elements, String id) {
        return dao.findByUserId(page, elements, userService.findById(id));
    }

    @Override
    public long createOrder(String userId, String certificateId) {
        try {
            User user = userService.findById(userId);
            GiftCertificate certificate = certificateService.findById(certificateId);
            Order order = createOrder(certificate);
            order.setUser(user);
            return dao.insert(order);
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorAttribute.USER_ERROR_CODE, ErrorAttribute.INVALID_ID, userId +
                    Symbol.COMMA + Symbol.SPACE + certificateId);
        }
    }

    @Override
    public Order findByUserIdAndOrderId(String userId, String orderId) {
        return dao.findByUserIdAndOrderId(userService.findById(userId).getId(), findById(orderId).getId()).
                orElseThrow(() -> new ResourceNotFoundException(ErrorAttribute.USER_ERROR_CODE,
                        ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, userId + Symbol.COMMA + Symbol.SPACE + orderId));
    }

    @Override
    public Order findById(String id) {
        try {
            return dao.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException(
                    ErrorAttribute.ORDER_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, id));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorAttribute.ORDER_ERROR_CODE, ErrorAttribute.INVALID_ORDER_ID, id);
        }
    }

    @Override
    public boolean deleteByCertificateId(String certificateId) {
        GiftCertificate certificate = certificateService.findById(certificateId);
        if (LocalDateTime.now().isAfter(certificate.getCreateDate().plusDays(certificate.getDuration())) ||
                CollectionUtils.isEmpty(dao.findByCertificateId(certificate.getId()))) {
            return dao.deleteByCertificateId(certificate.getId());
        }
        throw new DeleteCertificateInUseException(ErrorAttribute.ORDER_ERROR_CODE,
                ErrorAttribute.CERTIFICATE_IN_USE_ERROR, certificateId);
    }

    private Order createOrder(GiftCertificate certificate) {
        Order order = new Order();
        order.setCost(certificate.getPrice());
        order.setTimestamp(LocalDateTime.now());
        order.setGiftCertificate(certificate);
        return order;
    }
}