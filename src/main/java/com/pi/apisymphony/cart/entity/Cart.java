package com.pi.apisymphony.cart.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Cart {
    private Long id;
    private Long userId;
    private Date date;
    private CartItem[] products;
    @Data
    @Builder
    private static final class CartItem{
        private Long productId;
        private int quantity;
    }
}
