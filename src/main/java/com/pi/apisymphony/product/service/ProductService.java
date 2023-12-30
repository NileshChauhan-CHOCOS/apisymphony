package com.pi.apisymphony.product.service;

import com.pi.apisymphony.dto.GenericProductDto;
import com.pi.apisymphony.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    GenericProductDto getProductById(Long productId) throws NotFoundException;
    List<GenericProductDto> getAllProducts();
    List<GenericProductDto> getProducts(Integer limit);
    List<String> getAllCategories();
    List<GenericProductDto> getProductsInACategory(String category);
    GenericProductDto addProduct(GenericProductDto genericProductDto);
    GenericProductDto updateProduct(Long productId,GenericProductDto genericProductDto);
    GenericProductDto deleteProduct(Long productId) throws NotFoundException;
}
