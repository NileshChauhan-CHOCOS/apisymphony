package com.pi.apisymphony.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.util.Validator;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericUserDto {
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
    public List<String> validateObject(){
        List<String> errors = new ArrayList<>();
        if(this.username == null || this.username.isBlank()){
            errors.add(USERNAME_ASSERT);
        }
        if(this.password == null || this.password.isBlank()){
            errors.add(PASSWORD_ASSERT);
        }
        validateName(errors);
        validateAddress(errors);
        validateEmail(errors);
        validatePhone(errors);
        return errors;
    }
    private void validateName(List<String> errors){
        if(this.name == null){
            errors.add(NAME_ASSERT);
        }
        else{
            this.name.validateName(errors);
        }
    }
    private void validateEmail(List<String> errors){
        if(this.email == null || this.email.isBlank()){
            errors.add(EMAIL_ASSERT);
        }
        else{
            if(!Validator.validateEmail(this.email)){
                errors.add(ConstantsUtil.EMAIL_VALIDATION_ASSERT);
            }
        }
    }
    private void validatePhone(List<String> errors){
        if(this.phone == null || this.phone.isBlank()){
            errors.add(PHONE_ASSERT);
        }
        else{
            if(!Validator.validatePhone(this.phone)){
                errors.add(ConstantsUtil.PHONE_VALIDATION_ASSERT);
            }
        }
    }
    private void validateAddress(List<String> errors){
        if(this.address == null){
            errors.add(ADDRESS_ASSERT);
        }
        else{
            this.address.validateAddress(errors);
        }
    }
}
