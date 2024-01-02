package com.pi.apisymphony.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FakestoreUserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;
}
