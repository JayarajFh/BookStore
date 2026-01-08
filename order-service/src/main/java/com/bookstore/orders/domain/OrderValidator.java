package com.bookstore.orders.domain;

import com.bookstore.orders.clients.catalog.Product;
import com.bookstore.orders.clients.catalog.ProductServiceClient;
import com.bookstore.orders.domain.models.CreateOrderRequest;
import com.bookstore.orders.domain.models.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);
    private final ProductServiceClient client;

    public OrderValidator(ProductServiceClient client) {
        this.client = client;
    }

    public void validate(CreateOrderRequest request){
        Set<OrderItem> items = request.items();
        for(OrderItem item : items){
           Product product= client.getProductByCode(item.code()).orElseThrow(
                   () -> new InvalidOrderException("Invalid Product code:" + item.code()));
           if(item.price().compareTo(product.price()) != 0){
               log.error(
                       "Product price not matching. Actual price:{}, received price:{}",
                       product.price(),
                       item.price());
               throw new InvalidOrderException("Product price not matching");
           }
        }
    }
}
