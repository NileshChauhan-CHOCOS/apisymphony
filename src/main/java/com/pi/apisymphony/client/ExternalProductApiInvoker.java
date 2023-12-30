package com.pi.apisymphony.client;

import com.pi.apisymphony.dto.FakeStoreProductDto;
import com.pi.apisymphony.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ExternalProductApiInvoker<T> {
    T getProductById(Long id) throws NotFoundException;
    T addProduct(FakeStoreProductDto product);
    List<T> getAllProducts();
    T deleteProduct(Long id);
    List<T> getProducts(Integer limit);
    List<String> getAllCategories();
    List<T> getProductsInACategory(String category);
    T updateProduct(Long productId, FakeStoreProductDto fakeStoreProductDto);
}
