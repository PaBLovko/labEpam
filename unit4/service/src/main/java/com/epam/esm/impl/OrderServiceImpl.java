package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderDao;
import com.epam.esm.api.OrderService;
import com.epam.esm.api.UserService;
import com.epam.esm.constant.error.ErrorCode;
import com.epam.esm.constant.error.ErrorName;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;

import org.apache.commons.lang3.StringUtils;
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
    public List<Order> findByUserId(int page, int elements, String id, String userName) {
        return dao.findByUserId(page, elements, userService.findById(id, userName));
    }

    @Override
    public long createOrder(String userId, String certificateId, String userName) {
        try {
            User user = userService.findById(userId, userName);
            if (!user.getEmail().equals(userName)) {
                throw new InvalidFieldException(ErrorCode.ORDER, ErrorName.INVALID_ORDER_RECIPIENT, userId);
            }
            GiftCertificate certificate = certificateService.findById(certificateId);
            Order order = createOrder(certificate);
            order.setUser(user);
            return dao.insert(order);
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorCode.ORDER, ErrorName.INVALID_ID, userId + "," +
                    StringUtils.SPACE + certificateId);
        }
    }

    @Override
    public Order findByUserIdAndOrderId(String userId, String orderId, String userName) {
        return dao.findByUserIdAndOrderId(userService.findById(userId, userName).getId(), findById(orderId).getId()).
                orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ORDER,
                        ErrorName.RESOURCE_NOT_FOUND, userId + "," + StringUtils.SPACE + orderId));
    }

    @Override
    public Order findById(String id) {
        try {
            return dao.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ORDER,
                    ErrorName.RESOURCE_NOT_FOUND, id));
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(ErrorCode.ORDER, ErrorName.INVALID_ORDER_ID, id);
        }
    }


    @Override
    public List<Order> findWithCurrentCertificate(String certificateId) {
        return dao.findByCertificateId(certificateService.findById(certificateId).getId());
    }

    private Order createOrder(GiftCertificate certificate) {
        Order order = new Order();
        order.setPrice(certificate.getPrice());
        order.setTimestamp(LocalDateTime.now());
        order.setGiftCertificate(certificate);
        return order;
    }
}