package com.pi.apisymphony.util;

import com.pi.apisymphony.dto.FakeStoreCartDto;
import com.pi.apisymphony.dto.FakeStoreProductDto;
import com.pi.apisymphony.dto.GenericCartDto;
import com.pi.apisymphony.dto.GenericProductDto;

import java.util.Arrays;

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
    public static GenericCartDto mapToGenericCartDto(FakeStoreCartDto fakeStoreCartDto){
        if(fakeStoreCartDto != null){
            return GenericCartDto.builder()
                    .id(fakeStoreCartDto.getId())
                    .userId(fakeStoreCartDto.getUserId())
                    .date(fakeStoreCartDto.getDate())
                    .products(Arrays.stream(fakeStoreCartDto.getProducts()).map(ObjectMapper::mapToGenericCartItem).toArray(GenericCartDto.CartItem[]::new))
                    .build();
        }
        return null;
    }
    public static GenericCartDto.CartItem mapToGenericCartItem(FakeStoreCartDto.CartItem fakeStoreCartItem){
        if(fakeStoreCartItem != null){
            return GenericCartDto.CartItem.builder()
                    .productId(fakeStoreCartItem.getProductId())
                    .quantity(fakeStoreCartItem.getQuantity())
                    .build();
        }
        return null;
    }
}
