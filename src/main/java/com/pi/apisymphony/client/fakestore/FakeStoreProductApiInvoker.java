package com.pi.apisymphony.client.fakestore;

import com.pi.apisymphony.client.ExternalProductApiInvoker;
import com.pi.apisymphony.dto.FakeStoreProductDto;
import com.pi.apisymphony.exception.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Getter
@Setter
public class FakeStoreProductApiInvoker implements ExternalProductApiInvoker<FakeStoreProductDto> {
    private final RestTemplateBuilder restTemplateBuilder;
    
    @Value("${fakestore.api.uri}")
    private String fakeStoreUri ;
    
    @Value("${fakestore.api.paths.product}")
    private String fakeStoreApiPath;
    
    @Value("${fakestore.api.paths.pathVariable.id}")
    private String pathVariableId;
    public FakeStoreProductApiInvoker(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }
    
    @Override
    public FakeStoreProductDto getProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String uri = fakeStoreUri + fakeStoreApiPath + pathVariableId;
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(uri,FakeStoreProductDto.class, id);
        if(response.getBody() == null){
            throw new NotFoundException("Product with id : " + id + " not found ");
        }
        return response.getBody();
    }
    
    @Override
    public FakeStoreProductDto addProduct(FakeStoreProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String baseUri = fakeStoreUri + fakeStoreApiPath;
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(
                baseUri, product, FakeStoreProductDto.class);
        return response.getBody();
    }
    @Override
    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String uri = fakeStoreUri + fakeStoreApiPath;
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(uri, FakeStoreProductDto[].class);
        Assert.notNull(response, "Object should not be null");
        Objects.requireNonNull(response.getBody());
        return Arrays.stream(response.getBody()).toList();
    }
    
    @Override
    public FakeStoreProductDto deleteProduct(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        String uri = fakeStoreUri + fakeStoreApiPath + pathVariableId;
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(uri, HttpMethod.DELETE, requestCallback, responseExtractor, id);
        Assert.notNull(response, "response should not be null");
        return response.getBody();
    }
    @Override
    public List<FakeStoreProductDto> getProducts(Integer limit){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String specificUrl = fakeStoreUri + fakeStoreApiPath + "?limit={limit}";
        Map<String, Integer> requestParam = new HashMap<>();
        requestParam.put("limit", limit);
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(specificUrl, FakeStoreProductDto[].class, requestParam);
        Objects.requireNonNull(response.getBody());
        return Arrays.stream(response.getBody()).toList();
    }
    @Override
    public List<String> getAllCategories(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = fakeStoreUri + fakeStoreApiPath + "/categories";
        ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
        Objects.requireNonNull(response.getBody());
        return Arrays.stream(response.getBody()).toList();
    }
    @Override
    public List<FakeStoreProductDto> getProductsInACategory(String category){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = fakeStoreUri + fakeStoreApiPath +"/category/{category}";
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(url, FakeStoreProductDto[].class, category);
        Objects.requireNonNull(response.getBody());
        return Arrays.stream(response.getBody()).toList();
    }
    @Override
    public FakeStoreProductDto updateProduct(Long productId, FakeStoreProductDto fakeStoreProductDto) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String uri = fakeStoreUri + fakeStoreApiPath + pathVariableId;
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto,FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(uri,HttpMethod.PUT,requestCallback,responseExtractor, productId);
        Assert.notNull(response, "response should not be null");
        return response.getBody();
    }
}

