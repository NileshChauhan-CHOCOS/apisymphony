package com.pi.apisymphony.client;

import com.pi.apisymphony.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExternalUserApiInvoker<T> {
    List<T> getUsers();
    T getUser(long userid) throws NotFoundException;
    List<T> getLimitedUsers(int limit);
    List<T> getSortedUsers(String sort);
    T addUser(T user);
    T updateUser(long userid, T user);
    T deleteUser(long userid);
}
