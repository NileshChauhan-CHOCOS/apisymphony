package com.pi.apisymphony.response;

import com.pi.apisymphony.dto.ExceptionDto;
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
    public static @NotNull ErrorHttpBaseResponse errorResponse(Integer statusCode, ExceptionDto exceptionDto){
        return new ErrorHttpBaseResponse(statusCode,exceptionDto,LocalDateTime.now());
    }
}
