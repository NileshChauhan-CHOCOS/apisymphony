package com.pi.apisymphony.util;

import com.pi.apisymphony.dto.FakeStoreProductDto;
import com.pi.apisymphony.dto.GenericProductDto;

public class ObjectMapper {
    public static GenericProductDto mapToGenericProductDto(FakeStoreProductDto fakeStoreProductDto){
        if(fakeStoreProductDto != null){
            return GenericProductDto.builder()
                    .id(fakeStoreProductDto.getId())
                    .title(fakeStoreProductDto.getTitle())
                    .price(fakeStoreProductDto.getPrice())
                    .category(fakeStoreProductDto.getCategory())
                    .description(fakeStoreProductDto.getDescription())
                    .image(fakeStoreProductDto.getImage())
                    .build();
        }
        return GenericProductDto.builder().build();
    }
    public static FakeStoreProductDto mapToFakeStoreProductDto(GenericProductDto genericProductDto){
        if(genericProductDto != null){
            return FakeStoreProductDto.builder()
                    .id(genericProductDto.getId())
                    .title(genericProductDto.getTitle())
                    .price(genericProductDto.getPrice())
                    .category(genericProductDto.getCategory())
                    .description(genericProductDto.getDescription())
                    .image(genericProductDto.getImage()).build();
        }
        return FakeStoreProductDto.builder().build();
    }
}
