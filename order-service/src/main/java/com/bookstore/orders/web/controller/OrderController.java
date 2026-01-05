package com.bookstore.orders.web.controller;

import com.bookstore.orders.domain.OrderService;
import com.bookstore.orders.domain.SecurityService;
import com.bookstore.orders.domain.models.CreateOrderRequest;
import com.bookstore.orders.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrders(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoginName();
        log.info("logged in user name: " + userName);
        return orderService.createOrder(userName, request);
    }
}
