package com.pi.apisymphony.cart.service.impl;

import com.pi.apisymphony.cart.service.CartService;
import com.pi.apisymphony.client.fakestore.FakeStoreCartApiInvoker;
import com.pi.apisymphony.cart.dto.FakeStoreCartDto;
import com.pi.apisymphony.cart.dto.GenericCartDto;
import com.pi.apisymphony.exception.NoDataFoundException;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.util.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final FakeStoreCartApiInvoker fakeStoreCartApiInvoker;
    
    @Override
    public List<GenericCartDto> allCarts(){
        List<FakeStoreCartDto> fakeStoreCarts = fakeStoreCartApiInvoker.allCarts();
        return fakeStoreCarts.stream()
                .map(ObjectMapper::mapToGenericCartDto)
                .toList();
    }
    @Override
    public GenericCartDto getCartById(Long id) throws NotFoundException {
        FakeStoreCartDto fakeStoreCartDto = fakeStoreCartApiInvoker.getCartById(id);
        return ObjectMapper.mapToGenericCartDto(fakeStoreCartDto);
    }
    @Override
    public List<GenericCartDto> getCarts(int limit){
        if(limit <= 0){
            limit = 5;
        }
        List<FakeStoreCartDto> fakeStoreCarts = fakeStoreCartApiInvoker.getCarts(limit);
        return fakeStoreCarts.stream().map(ObjectMapper::mapToGenericCartDto).toList();
    }
    @Override
    public List<GenericCartDto> getSortedCarts(String sort){
       List<FakeStoreCartDto> fakeStoreCartSorted = fakeStoreCartApiInvoker.getSortedCarts(sort);
       return fakeStoreCartSorted.stream().map(ObjectMapper::mapToGenericCartDto).toList();
    }
    @Override
    public List<GenericCartDto> getUserCarts(Long userId) throws NoDataFoundException {
        List<FakeStoreCartDto> userCarts = fakeStoreCartApiInvoker.getUserCarts(userId);
        return userCarts.stream().map(ObjectMapper::mapToGenericCartDto).toList();
    }
    @Override
    public GenericCartDto addCart(GenericCartDto genericCartDto){
        FakeStoreCartDto fakeStoreCartDto = ObjectMapper.mapToFakeStoreCartDto(genericCartDto);
        FakeStoreCartDto fakeStoreResponse = fakeStoreCartApiInvoker.addCart(fakeStoreCartDto);
        return ObjectMapper.mapToGenericCartDto(fakeStoreResponse);
    }
    @Override
    public GenericCartDto updateCart(Long cartId, GenericCartDto genericCartDto){
        FakeStoreCartDto fakeStoreCartDto = ObjectMapper.mapToFakeStoreCartDto(genericCartDto);
        FakeStoreCartDto fakeStoreCartResponse = fakeStoreCartApiInvoker.updateCart(cartId,fakeStoreCartDto);
        return ObjectMapper.mapToGenericCartDto(fakeStoreCartResponse);
    }
    @Override
    public GenericCartDto deleteCart(Long cartId){
        FakeStoreCartDto fakeStoreResponse = fakeStoreCartApiInvoker.deleteCart(cartId);
        return ObjectMapper.mapToGenericCartDto(fakeStoreResponse);
    }
    @Override
    public List<GenericCartDto> getCartsInDateRange(String startDate, String endDate){
        List<FakeStoreCartDto> fakeStoreCartResponse = fakeStoreCartApiInvoker.getCartsInDateRange(startDate,endDate);
        return fakeStoreCartResponse.stream().map(ObjectMapper::mapToGenericCartDto).toList();
    }
}

