package com.pi.apisymphony.controlleradvice;

import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.dto.ExceptionDto;
import com.pi.apisymphony.exception.InvalidArgumentException;
import com.pi.apisymphony.exception.NoDataFoundException;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.response.BaseHttpResponse;
import com.pi.apisymphony.response.BaseHttpResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.client.RestClientException;

@org.springframework.web.bind.annotation.ControllerAdvice
@AllArgsConstructor
public class ControllerAdvice {
    private final Logger logger;
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(@NonNull NotFoundException notFoundException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ExceptionDto> handleInternalServerErrorException(HttpServerErrorException.InternalServerError internalServerError){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsUtil.SOMETHING_WENT_WRONG),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionDto> handleNullPointerException(NullPointerException nullPointerException){
        String message = ExceptionUtils.getStackTrace(nullPointerException);
        logger.error(ConstantsUtil.INTERNAL_SERVER_ERROR_LOGGING,message);
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR,ConstantsUtil.SOMETHING_WENT_WRONG),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ExceptionDto> handleInvalidArgumentException(@NonNull InvalidArgumentException invalidArgumentException){
        String message = ExceptionUtils.getStackTrace(invalidArgumentException);
        logger.error(ConstantsUtil.INTERNAL_SERVER_ERROR_LOGGING,message);
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST,invalidArgumentException.getMessage()),HttpStatus.OK);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException e){
        String message = ExceptionUtils.getStackTrace(e);
        logger.error(ConstantsUtil.INTERNAL_SERVER_ERROR_LOGGING,message);
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR,ConstantsUtil.SOMETHING_WENT_WRONG),HttpStatus.OK);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGlobalException(Exception e){
        String message = ExceptionUtils.getStackTrace(e);
        logger.error(ConstantsUtil.INTERNAL_SERVER_ERROR_LOGGING,message);
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsUtil.SOMETHING_WENT_WRONG),HttpStatus.OK);
    }
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ExceptionDto> handleHttpClientException(RestClientException e){
        String message = ExceptionUtils.getStackTrace(e);
        logger.error(ConstantsUtil.HTTP_CLIENT_EXCEPTION_ERROR_MESSAGE, message);
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsUtil.SOMETHING_WENT_WRONG), HttpStatus.OK);
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<BaseHttpResponse> handleNoDataFoundException(@NonNull NoDataFoundException e){
        String message = e.getMessage();
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.errorResponse(HttpStatus.NOT_FOUND.value(), message);
        return new ResponseEntity<>(baseHttpResponse,HttpStatus.NOT_FOUND);
    }
}
