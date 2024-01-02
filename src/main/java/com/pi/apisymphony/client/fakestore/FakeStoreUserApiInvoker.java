package com.pi.apisymphony.client.fakestore;

import com.pi.apisymphony.client.ExternalUserApiInvoker;
import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.user.dto.FakestoreUserDto;
import lombok.Data;
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

@Data
@Service
public class FakeStoreUserApiInvoker implements ExternalUserApiInvoker<FakestoreUserDto> {
    private final RestTemplate restTemplate;
    
    private final RestTemplateBuilder restTemplateBuilder;
    
    @Value("${fakestore.api.uri}")
    private String fakestoreUri;
    
    @Value("${fakestore.api.paths.users}")
    private String fakestoreUserApiEndPoint;
    
    @Value("${fakestore.api.paths.pathVariable.id}")
    private String pathVariableId;
    public FakeStoreUserApiInvoker(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = this.restTemplateBuilder.build();
    }
    
    @Override
    public List<FakestoreUserDto> getUsers(){
        String uri = fakestoreUri + fakestoreUserApiEndPoint;
        ResponseEntity<FakestoreUserDto[]> fakestoreResponse = restTemplate.getForEntity(uri,FakestoreUserDto[].class);
        if(fakestoreResponse.getBody() != null){
            return Arrays.stream(fakestoreResponse.getBody()).toList();
        }
        return new ArrayList<>();
    }
    @Override
    public FakestoreUserDto getUser(long userid) throws NotFoundException {
        String uri = fakestoreUri + fakestoreUserApiEndPoint + pathVariableId;
        ResponseEntity<FakestoreUserDto> fakeStoreUserResponse = restTemplate.getForEntity(uri,FakestoreUserDto.class,userid);
        if(fakeStoreUserResponse.getBody() == null){
            String errorMessage = String.format(ConstantsUtil.USER_NOT_FOUND,userid);
            throw new NotFoundException(errorMessage);
        }
        return fakeStoreUserResponse.getBody();
    }
    @Override
    public List<FakestoreUserDto> getLimitedUsers(int limit){
        if(limit <= 0){
            limit = 5;
        }
        String uri = fakestoreUri + fakestoreUserApiEndPoint + "?limit={limit}";
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("limit", limit);
        ResponseEntity<FakestoreUserDto[]> fakestoreResponse =  restTemplate.getForEntity(uri, FakestoreUserDto[].class,requestParam);
        Assert.notNull(fakestoreResponse.getBody(),ConstantsUtil.RESPONSE_ASSERT);
        return Arrays.stream(fakestoreResponse.getBody()).toList();
    }
    @Override
    public List<FakestoreUserDto> getSortedUsers(String sort){
        if(sort == null || (!sort.equals("desc")  && !sort.equals("asc"))){
            sort =  "asc";
        }
        String uri = fakestoreUri + fakestoreUserApiEndPoint + "?sort={sort}";
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("sort",sort);
        ResponseEntity<FakestoreUserDto[]> fakestoreResponse = restTemplate.getForEntity(uri,FakestoreUserDto[].class,requestParam);
        Assert.notNull(fakestoreResponse.getBody(), ConstantsUtil.RESPONSE_ASSERT);
        return Arrays.stream(fakestoreResponse.getBody()).toList();
    }
    @Override
    public FakestoreUserDto addUser(FakestoreUserDto fakestoreUserDto){
        String uri = fakestoreUri + fakestoreUserApiEndPoint;
        ResponseEntity<FakestoreUserDto> fakestoreResponse = restTemplate.postForEntity(uri,fakestoreUserDto,FakestoreUserDto.class);
        return fakestoreResponse.getBody();
    }
    @Override
    public FakestoreUserDto updateUser(long userid, FakestoreUserDto fakestoreUserDto){
        String uri = fakestoreUri + fakestoreUserApiEndPoint + pathVariableId;
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakestoreUserDto,FakestoreUserDto.class);
        ResponseExtractor<ResponseEntity<FakestoreUserDto>> responseExtractor = restTemplate.responseEntityExtractor(FakestoreUserDto.class);
        ResponseEntity<FakestoreUserDto> fakestoreResponse = restTemplate.execute(uri, HttpMethod.PUT,requestCallback,responseExtractor,userid);
        Assert.notNull(fakestoreResponse, ConstantsUtil.RESPONSE_ASSERT);
        return fakestoreResponse.getBody();
    }
    @Override
    public FakestoreUserDto deleteUser(long userid){
        String uri = fakestoreUri + fakestoreUserApiEndPoint + pathVariableId;
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakestoreUserDto.class);
        ResponseExtractor<ResponseEntity<FakestoreUserDto>> responseExtractor = restTemplate.responseEntityExtractor(FakestoreUserDto.class);
        ResponseEntity<FakestoreUserDto> fakestoreResponse = restTemplate.execute(uri,HttpMethod.DELETE,requestCallback,responseExtractor,userid);
        Assert.notNull(fakestoreResponse, ConstantsUtil.RESPONSE_ASSERT);
        return fakestoreResponse.getBody();
    }
}
