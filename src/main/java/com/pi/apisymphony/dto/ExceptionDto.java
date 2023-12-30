package com.pi.apisymphony.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class ExceptionDto {
    private HttpStatus errorCode;
    private String message;
    public ExceptionDto(HttpStatus status, String message){
        this.errorCode = status;
        this.message= message;
    }
}

