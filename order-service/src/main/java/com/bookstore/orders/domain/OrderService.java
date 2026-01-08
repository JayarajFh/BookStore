package com.bookstore.orders.domain;

import com.bookstore.orders.domain.models.CreateOrderRequest;
import com.bookstore.orders.domain.models.CreateOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository repository;
    private final OrderValidator validator;

    public OrderService(OrderRepository repository, OrderValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        validator.validate(request);
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);
        OrderEntity savedOrder = repository.save(newOrder);
        log.info("Created Order with orderNumber={}", savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
