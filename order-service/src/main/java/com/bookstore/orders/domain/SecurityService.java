package com.bookstore.orders.domain;

import org.springframework.stereotype.Component;

@Component
public class SecurityService {
    public String getLoginName() {
        return "user";
    }
}
