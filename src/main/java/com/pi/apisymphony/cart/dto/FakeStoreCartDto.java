package com.pi.apisymphony.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class FakeStoreCartDto {
    private Long id;
    private Long userId;
    private Date date;
    private CartItem[] products;
    @Data
    @Builder
    public static final class CartItem{
        private Long productId;
        private int quantity;
    }
}
