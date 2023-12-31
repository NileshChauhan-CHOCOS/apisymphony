package com.pi.apisymphony.client;

import com.pi.apisymphony.exception.NoDataFoundException;
import com.pi.apisymphony.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ExternalCartApiInvoker<T> {
    List<T> allCarts();
    T getCartById(Long id) throws NotFoundException;
    List<T> getCarts(int limit);
    List<T> getSortedCarts(String sort);
    List<T> getUserCarts(Long userId) throws NoDataFoundException;
    T addCart(T object);
    T updateCart(Long cartId, T object);
    T deleteCart(Long cartId);
    List<T> getCartsInDateRange(String startDate, String endDate);
}
