package com.pi.apisymphony.product.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Product {
    @NonNull
    private String id;
    private String title;
    private String price;
    private String category;
    private String description;
    
}

