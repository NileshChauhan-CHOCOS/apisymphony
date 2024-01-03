package com.pi.apisymphony.user.entity;

import com.pi.apisymphony.user.dto.Address;
import com.pi.apisymphony.user.dto.Name;
import lombok.Data;

@Data
public class User {
    public static final String EMAIL_ASSERT = "Email cannot be null or blank";
    public static final String PASSWORD_ASSERT = "Password cannot be null or blank";
    public static final String USERNAME_ASSERT = "Username cannot be null or blank";
    public static final String NAME_ASSERT = "Name cannot be null";
    public static final String PHONE_ASSERT = "Phone cannot be null or blank";
    public static final String ADDRESS_ASSERT = "Address cannot be null";
    private Long id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;
}
