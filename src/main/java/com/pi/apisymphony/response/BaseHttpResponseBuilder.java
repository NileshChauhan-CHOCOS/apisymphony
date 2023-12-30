package com.pi.apisymphony.response;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class BaseHttpResponseBuilder {
    @Contract("_ -> new")
    public static @NotNull BaseHttpResponse successResponse(@NonNull Object result){
        return new BaseHttpResponse(HttpStatus.OK.value(), "Ok",result, LocalDateTime.now());
    }
    @Contract("_, _ -> new")
    public static @NotNull BaseHttpResponse errorResponse(Integer statusCode, String message){
        return new BaseHttpResponse(statusCode,message,null, LocalDateTime.now());
    }
}
