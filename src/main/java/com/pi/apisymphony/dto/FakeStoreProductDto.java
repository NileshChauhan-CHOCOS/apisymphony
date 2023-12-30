package com.pi.apisymphony.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
@Builder
public class FakeStoreProductDto implements Type {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}

