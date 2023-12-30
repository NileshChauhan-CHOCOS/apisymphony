package com.pi.apisymphony.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericProductDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
    public boolean validate(){
        return this.title == null || this.price == null || this.category == null;
    }
}
