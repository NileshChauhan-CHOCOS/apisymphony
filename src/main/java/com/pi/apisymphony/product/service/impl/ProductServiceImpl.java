package com.pi.apisymphony.product.service.impl;

import com.pi.apisymphony.client.fakestore.FakeStoreProductApiInvoker;
import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.dto.FakeStoreProductDto;
import com.pi.apisymphony.dto.GenericProductDto;
import com.pi.apisymphony.exception.InvalidArgumentException;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.product.service.ProductService;
import com.pi.apisymphony.util.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;


@Service
@Getter
@Setter
public class ProductServiceImpl implements ProductService {
    private final FakeStoreProductApiInvoker fakeStoreProductApiInvoker;
    private final Random random = new Random();
    private final Logger logger;
    public ProductServiceImpl(FakeStoreProductApiInvoker fakeStoreProductApiInvoker,
                              Logger logger){
        this.fakeStoreProductApiInvoker = fakeStoreProductApiInvoker;
        this.logger = logger;
    }
    @Override
    public GenericProductDto getProductById(Long productId) throws NotFoundException {
        FakeStoreProductDto fakeStoreProductDto =  fakeStoreProductApiInvoker.getProductById(productId);
        return ObjectMapper.mapToGenericProductDto(fakeStoreProductDto);
    }
    @Override
    public List<GenericProductDto> getAllProducts(){
        List<FakeStoreProductDto> response = fakeStoreProductApiInvoker.getAllProducts();
        return response.stream().map(ObjectMapper::mapToGenericProductDto).toList();
    }
    @Override
    public List<GenericProductDto> getProducts(Integer limit){
        List<FakeStoreProductDto> fakeStoreProducts = fakeStoreProductApiInvoker.getProducts(limit);
        return fakeStoreProducts.stream()
                .map(ObjectMapper::mapToGenericProductDto)
                .toList();
    }
    @Override
    public List<String> getAllCategories(){
        return fakeStoreProductApiInvoker.getAllCategories();
    }
    @Override
    public List<GenericProductDto> getProductsInACategory(String category){
        List<FakeStoreProductDto> fakeStoreProducts = fakeStoreProductApiInvoker.getProductsInACategory(category);
        return fakeStoreProducts.stream()
                .map(ObjectMapper::mapToGenericProductDto)
                .toList();
    }
    @Override
    public GenericProductDto addProduct(GenericProductDto genericProductDto){
        try{
            if(genericProductDto == null){
                throw new InvalidArgumentException(ConstantsUtil.PRODUCT_NULL_MESSAGE);
            }
            genericProductDto.setId(random.nextLong());
            FakeStoreProductDto fakeStoreProductDto = ObjectMapper.mapToFakeStoreProductDto(genericProductDto);
            FakeStoreProductDto productResponse = fakeStoreProductApiInvoker.addProduct(fakeStoreProductDto);
            return ObjectMapper.mapToGenericProductDto(productResponse);
        }
        catch (Exception  e){
            logger.error("");
        }
        return GenericProductDto.builder().build();
    }
    @Override
    public GenericProductDto updateProduct(Long productId, GenericProductDto genericProductDto){
        try{
            if(genericProductDto == null){
                throw new InvalidArgumentException(ConstantsUtil.PRODUCT_NULL_MESSAGE);
            }
            FakeStoreProductDto fakeStoreProductDto = ObjectMapper.mapToFakeStoreProductDto(genericProductDto);
            FakeStoreProductDto response = fakeStoreProductApiInvoker.updateProduct(productId,fakeStoreProductDto);
            return ObjectMapper.mapToGenericProductDto(response);
        }
        catch (Exception e){
            logger.error(e.getLocalizedMessage());
            return GenericProductDto.builder().build();
        }
    }
    @Override
    public GenericProductDto deleteProduct(Long productId) throws NotFoundException{
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductApiInvoker.deleteProduct(productId);
        if(fakeStoreProductDto == null){
            throw new NotFoundException(String.format(ConstantsUtil.PRODUCT_NOT_FOUND,productId));
        }
        return ObjectMapper.mapToGenericProductDto(fakeStoreProductDto);
    }
}

