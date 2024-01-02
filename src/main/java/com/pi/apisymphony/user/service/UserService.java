package com.pi.apisymphony.user.service;

import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.user.dto.GenericUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<GenericUserDto> getUsers();
    GenericUserDto getUser(long userid) throws NotFoundException;
    List<GenericUserDto> getLimitedUsers(int limit);
    List<GenericUserDto> getSortedUsers(String sort);
    GenericUserDto addUser(GenericUserDto user);
    GenericUserDto updateUser(long userid, GenericUserDto user);
    GenericUserDto deleteUser(long userid);
}
