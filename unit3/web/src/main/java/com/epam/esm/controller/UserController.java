package com.epam.esm.controller;

import com.epam.esm.Order;
import com.epam.esm.Tag;
import com.epam.esm.User;
import com.epam.esm.api.OrderService;
import com.epam.esm.api.TagService;
import com.epam.esm.api.UserService;
import com.epam.esm.attribute.ResponseAttribute;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;
    private final Hateoas<User> userHateoas;
    private final Hateoas<Order> orderHateoas;
    private final Hateoas<Tag> tagHateoas;
    private final Hateoas<OperationResponse> responseHateoas;

    @Autowired
    public UserController(UserService userService, OrderService orderService, TagService tagService,
                          Hateoas<User> userHateoas, Hateoas<Order> orderHateoas, Hateoas<Tag> tagHateoas,
                          @Qualifier("orderOperationResponseHateoas") Hateoas<OperationResponse> responseHateoas) {
        this.userService = userService;
        this.orderService = orderService;
        this.tagService = tagService;
        this.userHateoas = userHateoas;
        this.orderHateoas = orderHateoas;
        this.tagHateoas = tagHateoas;
        this.responseHateoas = responseHateoas;
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable String id) {
        User user = userService.findById(id);
        userHateoas.createHateoas(user);
        return user;
    }

    @GetMapping
    public List<User> findAllUsers(@RequestParam int page, @RequestParam int elements) {
        List<User> users = userService.findAll(page, elements);
        users.forEach(userHateoas::createHateoas);
        return users;
    }

    @PatchMapping("/{userId}/orders/new/{certificateId}")
    public OperationResponse createOrder(@PathVariable String userId, @PathVariable String certificateId) {
        OperationResponse response = new OperationResponse(OperationResponse.Operation.CREATION,
                ResponseAttribute.ORDER_CREATE_OPERATION, String.valueOf(orderService.createOrder(userId,
                certificateId)));
        responseHateoas.createHateoas(response);
        return response;
    }

    @GetMapping("/{userId}/orders")
    public List<Order> findUserOrders(@PathVariable String userId, @RequestParam int page, @RequestParam int elements) {
        List<Order> orders = orderService.findByUserId(page, elements, userId);
        orders.forEach(orderHateoas::createHateoas);
        return orders;
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public Order findUserOrder(@PathVariable String userId, @PathVariable String orderId) {
        Order order = orderService.findByUserIdAndOrderId(userId, orderId);
        orderHateoas.createHateoas(order);
        return order;
    }

    @GetMapping("/{userId}/orders/tags/popular")
    public Tag findMostUsedTagOfUserWithHighestCostOfAllOrders(@PathVariable String userId) {
        Tag tag = tagService.findMostUsedTagOfUserWithHighestCostOfAllOrders(userId);
        tagHateoas.createHateoas(tag);
        return tag;
    }
}
