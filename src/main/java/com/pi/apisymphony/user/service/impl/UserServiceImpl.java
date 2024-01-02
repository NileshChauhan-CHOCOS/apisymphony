package com.pi.apisymphony.user.service.impl;

import com.pi.apisymphony.client.fakestore.FakeStoreUserApiInvoker;
import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.user.dto.FakestoreUserDto;
import com.pi.apisymphony.user.dto.GenericUserDto;
import com.pi.apisymphony.user.service.UserService;
import com.pi.apisymphony.util.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Data
@Service
public class UserServiceImpl implements UserService {
    private final FakeStoreUserApiInvoker fakeStoreUserApiInvoker;
    private final Random random = new Random();
    
    public UserServiceImpl(FakeStoreUserApiInvoker fakeStoreUserApiInvoker) {
        this.fakeStoreUserApiInvoker = fakeStoreUserApiInvoker;
    }
    
    @Override
    public List<GenericUserDto> getUsers() {
        List<FakestoreUserDto> fakestoreUsers = fakeStoreUserApiInvoker.getUsers();
        return fakestoreUsers.stream()
                .map(ObjectMapper::mapToGenericUserDto)
                .toList();
    }
    
    @Override
    public GenericUserDto getUser(long userid) throws NotFoundException {
       FakestoreUserDto fakestoreUser = fakeStoreUserApiInvoker.getUser(userid);
       return ObjectMapper.mapToGenericUserDto(fakestoreUser);
    }
    @Override
    public List<GenericUserDto> getLimitedUsers(int limit){
        List<FakestoreUserDto> fakestoreResponse = fakeStoreUserApiInvoker.getLimitedUsers(limit);
        return fakestoreResponse.stream()
                .map(ObjectMapper::mapToGenericUserDto)
                .toList();
    }
    @Override
    public List<GenericUserDto> getSortedUsers(String sort){
        List<FakestoreUserDto> fakestoreResponse = fakeStoreUserApiInvoker.getSortedUsers(sort);
        return fakestoreResponse.stream()
                .map(ObjectMapper::mapToGenericUserDto)
                .toList();
    }
    @Override
    public GenericUserDto addUser(GenericUserDto user){
        if(user != null){
            user.setId(random.nextLong());
            FakestoreUserDto fakestoreUserDto = ObjectMapper.mapToFakeStoreUserDto(user);
            FakestoreUserDto fakestoreResponse = fakeStoreUserApiInvoker.addUser(fakestoreUserDto);
            return ObjectMapper.mapToGenericUserDto(fakestoreResponse);
        }
        throw new IllegalArgumentException(ConstantsUtil.ARGUMENT_OBJECT_ASSERT);
    }
    @Override
    public GenericUserDto updateUser(long userid, GenericUserDto user){
        FakestoreUserDto fakestoreUserDto = ObjectMapper.mapToFakeStoreUserDto(user);
        FakestoreUserDto fakestoreResponse = fakeStoreUserApiInvoker.updateUser(userid,fakestoreUserDto);
        return ObjectMapper.mapToGenericUserDto(fakestoreResponse);
    }
    @Override
    public GenericUserDto deleteUser(long userid){
        FakestoreUserDto fakestoreResponse = fakeStoreUserApiInvoker.deleteUser(userid);
        return ObjectMapper.mapToGenericUserDto(fakestoreResponse);
    }
}
