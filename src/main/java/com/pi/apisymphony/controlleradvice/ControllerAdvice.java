package com.pi.apisymphony.controlleradvice;

import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.dto.ExceptionDto;
import com.pi.apisymphony.dto.HttpClientExceptionDto;
import com.pi.apisymphony.exception.InvalidArgumentException;
import com.pi.apisymphony.exception.NoDataFoundException;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.response.BaseHttpResponseBuilder;
import com.pi.apisymphony.response.ErrorHttpBaseResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.client.RestClientException;

@org.springframework.web.bind.annotation.ControllerAdvice
@AllArgsConstructor
@SuppressWarnings("unused")
public class ControllerAdvice {
    private final Logger logger;
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(@NonNull NotFoundException notFoundException){
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ErrorHttpBaseResponse> handleInternalServerErrorException(HttpServerErrorException.InternalServerError e){
        String errorMessage = ExceptionUtils.getStackTrace(e);
        errorMessage = String.format(ConstantsUtil.GENERIC_EXCEPTION_MESSAGE,errorMessage);
        logger.error(errorMessage);
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        ErrorHttpBaseResponse baseHttpResponse = BaseHttpResponseBuilder.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionDto);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionDto> handleNullPointerException(NullPointerException nullPointerException){
        String message = ExceptionUtils.getStackTrace(nullPointerException);
        logger.error(ConstantsUtil.INTERNAL_SERVER_ERROR_LOGGING,message);
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR,ConstantsUtil.SOMETHING_WENT_WRONG),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorHttpBaseResponse> handleInvalidArgumentException(@NonNull InvalidArgumentException e){
        String message = ExceptionUtils.getStackTrace(e);
        logger.error(ConstantsUtil.INTERNAL_SERVER_ERROR_LOGGING,message);
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage());
        ErrorHttpBaseResponse baseHttpResponse = BaseHttpResponseBuilder.errorResponse(HttpStatus.BAD_REQUEST.value(),exceptionDto);
        return new ResponseEntity<>(baseHttpResponse,HttpStatus.OK);
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
    public ResponseEntity<ErrorHttpBaseResponse> handleNoDataFoundException(@NonNull NoDataFoundException e){
        String errorMessage = e.getMessage();
        errorMessage = String.format(ConstantsUtil.GENERIC_EXCEPTION_MESSAGE,errorMessage);
        logger.error(errorMessage);
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        ErrorHttpBaseResponse baseHttpResponse = BaseHttpResponseBuilder.errorResponse(HttpStatus.NOT_FOUND.value(), exceptionDto);
        return new ResponseEntity<>(baseHttpResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ErrorHttpBaseResponse> handleClassCastException(@NonNull ClassCastException e){
        String errorMessage = ExceptionUtils.getStackTrace(e);
        errorMessage = String.format(ConstantsUtil.GENERIC_EXCEPTION_MESSAGE,errorMessage);
        logger.error(errorMessage);
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        ErrorHttpBaseResponse baseHttpResponse = BaseHttpResponseBuilder.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionDto);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorHttpBaseResponse> handleHttpClientErrorException(@NonNull HttpClientErrorException e){
        String errorMessage = ExceptionUtils.getMessage(e);
        errorMessage = String.format(ConstantsUtil.GENERIC_EXCEPTION_MESSAGE,errorMessage);
        logger.error(errorMessage);
        HttpClientExceptionDto httpClientExceptionDto = e.getResponseBodyAs(HttpClientExceptionDto.class);
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        if(httpClientExceptionDto != null){
            exceptionDto.setMessage(httpClientExceptionDto.getMessage());
        }
        ErrorHttpBaseResponse errorHttpBaseResponse = BaseHttpResponseBuilder.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionDto);
        return new ResponseEntity<>(errorHttpBaseResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ErrorHttpBaseResponse> handleBadRequestException(@NonNull HttpClientErrorException.BadRequest e){
        String errorMessage = ExceptionUtils.getStackTrace(e);
        errorMessage = String.format(ConstantsUtil.GENERIC_EXCEPTION_MESSAGE,errorMessage);
        logger.error(errorMessage);
        HttpClientExceptionDto httpClientExceptionDto = e.getResponseBodyAs(HttpClientExceptionDto.class);
        ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.getReasonPhrase());
        if(httpClientExceptionDto != null){
            exceptionDto.setMessage(httpClientExceptionDto.getMessage());
        }
        ErrorHttpBaseResponse baseHttpResponse = BaseHttpResponseBuilder.errorResponse(HttpStatus.BAD_REQUEST.value(), exceptionDto);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.BAD_REQUEST);
    }
}
