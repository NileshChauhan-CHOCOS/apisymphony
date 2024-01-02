package com.pi.apisymphony.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Name {
    private static final String FIRSTNAME_ASSERT = "Firstname cannot be null or blank";
    private static final String LASTNAME_ASSERT = "Lastname cannot be null or blank";
    private String firstname;
    private String lastname;
    public void validateName(List<String> errors){
        if(this.firstname == null || this.firstname.isBlank()){
            errors.add(FIRSTNAME_ASSERT);
        }
        if(this.lastname == null || this.lastname.isBlank()){
            errors.add(LASTNAME_ASSERT);
        }
    }
}
