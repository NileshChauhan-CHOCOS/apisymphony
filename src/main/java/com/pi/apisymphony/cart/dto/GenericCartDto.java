package com.pi.apisymphony.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class GenericCartDto{
    @JsonProperty("id")
    private Long id;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("products")
    private CartItem[] products;
    @Data
    @Builder
    public static final class CartItem {
        @JsonProperty("productId")
        private Long productId;
        @JsonProperty("quantity")
        private int quantity;
    }
    public List<String> validate(){
        List<String> errors = new ArrayList<>();
        if(this.userId == null){
            errors.add("userid cannot be null");
        }
        if(this.date == null){
            errors.add("date cannot be null");
        }
        if(this.products == null){
            errors.add("Products cannot be null");
        }
        if(this.products != null && this.products.length == 0){
            errors.add("Products cannot be empty");
        }
        return errors;
    }
}
