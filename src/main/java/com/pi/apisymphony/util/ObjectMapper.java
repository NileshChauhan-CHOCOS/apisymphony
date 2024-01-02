package com.pi.apisymphony.util;

import com.pi.apisymphony.cart.dto.FakeStoreCartDto;
import com.pi.apisymphony.dto.FakeStoreProductDto;
import com.pi.apisymphony.cart.dto.GenericCartDto;
import com.pi.apisymphony.dto.GenericProductDto;
import com.pi.apisymphony.user.dto.FakestoreUserDto;
import com.pi.apisymphony.user.dto.GenericUserDto;

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
                    .products(Arrays.stream(fakeStoreCartDto.getProducts()).
                            map(ObjectMapper::mapToGenericCartItem).
                            toArray(GenericCartDto.CartItem[]::new))
                    .build();
        }
        return null;
    }
    public static FakeStoreCartDto mapToFakeStoreCartDto(GenericCartDto genericCartDto){
        if(genericCartDto != null){
            return FakeStoreCartDto.builder()
                    .id(genericCartDto.getId())
                    .userId(genericCartDto.getUserId())
                    .date(genericCartDto.getDate())
                    .products(Arrays.stream(genericCartDto.getProducts())
                            .map(ObjectMapper::mapToFakeStoreCartItem).
                            toArray(FakeStoreCartDto.CartItem[]::new))
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
    public static FakeStoreCartDto.CartItem mapToFakeStoreCartItem(GenericCartDto.CartItem genericCartItem){
        if(genericCartItem != null){
            return FakeStoreCartDto.CartItem.builder()
                    .productId(genericCartItem.getProductId())
                    .quantity(genericCartItem.getQuantity())
                    .build();
        }
        return null;
    }
    public static GenericUserDto mapToGenericUserDto(FakestoreUserDto fakestoreUserDto){
        if(fakestoreUserDto != null){
            return GenericUserDto.builder()
                    .id(fakestoreUserDto.getId())
                    .email(fakestoreUserDto.getEmail())
                    .username(fakestoreUserDto.getUsername())
                    .password(fakestoreUserDto.getPassword())
                    .name(fakestoreUserDto.getName())
                    .address(fakestoreUserDto.getAddress())
                    .phone(fakestoreUserDto.getPhone())
                    .build();
        }
        return GenericUserDto.builder().build();
    }
    public static FakestoreUserDto mapToFakeStoreUserDto(GenericUserDto user) {
        if(user != null){
            return FakestoreUserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .name(user.getName())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .build();
        }
        return FakestoreUserDto.builder().build();
    }
}
