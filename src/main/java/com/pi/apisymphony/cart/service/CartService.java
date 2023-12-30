package com.pi.apisymphony.cart.service;

import com.pi.apisymphony.dto.GenericCartDto;
import com.pi.apisymphony.exception.NoDataFoundException;
import com.pi.apisymphony.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CartService {
    List<GenericCartDto> allCarts();
    GenericCartDto getCartById(Long id) throws NotFoundException;
    List<GenericCartDto> getCarts(int limit);
    List<GenericCartDto> getSortedCarts(String sort);
    List<GenericCartDto> getUserCarts(Long userId) throws NoDataFoundException;
}
