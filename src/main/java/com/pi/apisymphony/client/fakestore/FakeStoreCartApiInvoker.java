package com.pi.apisymphony.client.fakestore;

import com.pi.apisymphony.client.ExternalCartApiInvoker;
import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.cart.dto.FakeStoreCartDto;
import com.pi.apisymphony.exception.NoDataFoundException;
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
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Getter
@Setter
public class FakeStoreCartApiInvoker implements ExternalCartApiInvoker<FakeStoreCartDto> {
    
    private final RestTemplateBuilder restTemplateBuilder;
    
    private final RestTemplate restTemplate;
    
    @Value("${fakestore.api.uri}")
    private String fakeStoreUri ;
    
    @Value("${fakestore.api.paths.cart}")
    private String fakeStoreCartApiEndPoint;
    
    @Value("${fakestore.api.paths.pathVariable.id}")
    private String pathVariableId;
    public FakeStoreCartApiInvoker(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = this.restTemplateBuilder.build();
    }
    @Override
    public List<FakeStoreCartDto> allCarts(){
        String uri = fakeStoreUri + fakeStoreCartApiEndPoint;
        ResponseEntity<FakeStoreCartDto[]> response = restTemplate.getForEntity(uri, FakeStoreCartDto[].class);
        Assert.notNull(response, "Object should not be null");
        Objects.requireNonNull(response.getBody());
        return Arrays.stream(response.getBody()).toList();
    }
    @Override
    public FakeStoreCartDto getCartById(Long id) throws NotFoundException{
        String uri = fakeStoreUri + fakeStoreCartApiEndPoint + pathVariableId;
        ResponseEntity<FakeStoreCartDto> response = restTemplate.getForEntity(uri,FakeStoreCartDto.class, id);
        if(response.getBody() == null){
            throw new NotFoundException(String.format(ConstantsUtil.CART_NOT_FOUND,id));
        }
        return response.getBody();
    }
    @Override
    public List<FakeStoreCartDto> getCarts(int limit){
        String url = fakeStoreUri + fakeStoreCartApiEndPoint + "?limit={limit}";
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("limit", limit);
        ResponseEntity<FakeStoreCartDto[]> fakeStoreResponse = restTemplate.getForEntity(url, FakeStoreCartDto[].class,requestParam);
        Objects.requireNonNull(fakeStoreResponse.getBody());
        return Arrays.stream(fakeStoreResponse.getBody()).toList();
    }
    @Override
    public List<FakeStoreCartDto> getSortedCarts(String sort){
        if(sort == null || (!sort.equals("desc")  && !sort.equals("asc"))){
            sort =  "asc";
        }
        String uri = fakeStoreUri + fakeStoreCartApiEndPoint + "?sort={sort}";
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("sort", sort);
        ResponseEntity<FakeStoreCartDto[]> fakeStoreResponse = restTemplate.getForEntity(uri,FakeStoreCartDto[].class,requestParam);
        Objects.requireNonNull(fakeStoreResponse.getBody());
        return Arrays.stream(fakeStoreResponse.getBody()).toList();
    }
    @Override
    public List<FakeStoreCartDto> getUserCarts(Long userId) throws NoDataFoundException{
        String specificUri = fakeStoreUri + fakeStoreCartApiEndPoint + "/user/{userid}";
        ResponseEntity<FakeStoreCartDto[]> fakeStoreResponse = restTemplate.getForEntity(specificUri,FakeStoreCartDto[].class,userId);
        FakeStoreCartDto[]  fakeStoreCarts =  fakeStoreResponse.getBody();
        if(fakeStoreCarts == null || fakeStoreCarts.length == 0){
            throw new NoDataFoundException(String.format(ConstantsUtil.NO_DATA_FOUND,userId));
        }
        return Arrays.stream(fakeStoreCarts).toList();
    }
    @Override
    public FakeStoreCartDto addCart(FakeStoreCartDto fakeStoreCartDto){
        String uri = fakeStoreUri + fakeStoreCartApiEndPoint;
        ResponseEntity<FakeStoreCartDto> response = restTemplate.postForEntity(uri, fakeStoreCartDto, FakeStoreCartDto.class);
        return response.getBody();
    }
    @Override
    public FakeStoreCartDto updateCart(Long cartId, FakeStoreCartDto fakeStoreCartDto){
        String uri = fakeStoreUri + fakeStoreCartApiEndPoint + pathVariableId;
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreCartDto,FakeStoreCartDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreCartDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreCartDto.class);
        ResponseEntity<FakeStoreCartDto> response = restTemplate.execute(uri, HttpMethod.PUT, requestCallback ,responseExtractor,cartId);
        Assert.notNull(response,"response should not be null");
        return  response.getBody();
    }
    @Override
    public FakeStoreCartDto deleteCart(Long id){
        String uri = fakeStoreUri + fakeStoreCartApiEndPoint + "/{id}";
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreCartDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreCartDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreCartDto.class);
        ResponseEntity<FakeStoreCartDto> response = restTemplate.execute(uri,HttpMethod.DELETE,requestCallback, responseExtractor,id);
        Assert.notNull(response, "response should not be null");
        return response.getBody();
    }
}
