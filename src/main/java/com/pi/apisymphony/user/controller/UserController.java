package com.pi.apisymphony.user.controller;

import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.constans.HttpConstant;
import com.pi.apisymphony.dto.ExceptionDto;
import com.pi.apisymphony.exception.InvalidArgumentException;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.response.BaseHttpResponse;
import com.pi.apisymphony.response.BaseHttpResponseBuilder;
import com.pi.apisymphony.user.dto.GenericUserDto;
import com.pi.apisymphony.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@SuppressWarnings("unused")
public class UserController {
    private final UserService userService;
    @GetMapping("/all")
    @ApiOperation(value = "This API endpoint retrieves all the users", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK,response = GenericUserDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    public ResponseEntity<BaseHttpResponse> getGetUsers(){
        List<GenericUserDto> users = userService.getUsers();
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(users);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @GetMapping("/{userid}")
    @ApiOperation(value = "This API endpoint retrieves user for provided userid",httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK,response = GenericUserDto.class),
            @ApiResponse(code = 400, message = HttpConstant.NOT_FOUND, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR,response = ExceptionDto.class)
    })
    public ResponseEntity<BaseHttpResponse> getUser(@PathVariable long userid) throws NotFoundException {
        GenericUserDto user = userService.getUser(userid);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(user);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @GetMapping("/limit/{limit}")
    @ApiOperation(value = "This API endpoint retrieves limited users", notes = "If limit <= 0 then default limit 5 is set",
            httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = GenericUserDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    public ResponseEntity<BaseHttpResponse> getLimitedUsers(@PathVariable  int limit){
        List<GenericUserDto> users = userService.getLimitedUsers(limit);
        BaseHttpResponse response = BaseHttpResponseBuilder.successResponse(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/sort")
    @ApiOperation(value = "This API endpoint retrieves the sorted users",
            notes = "The default value is in ascending mode. If key is not asc/desc then default asc key is uses",
            httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = GenericUserDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    public ResponseEntity<BaseHttpResponse> getSortedUsers(@RequestParam String sort){
        List<GenericUserDto> users = userService.getSortedUsers(sort);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(users);
        return new  ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "This API endpoint add a user in the system",httpMethod = HttpConstant.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpConstant.CREATED, response = GenericUserDto.class),
            @ApiResponse(code = 400, message = HttpConstant.BAD_REQUEST,response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR,response = ExceptionDto.class)
    })
    public ResponseEntity<BaseHttpResponse> addUser(@RequestBody GenericUserDto user) throws InvalidArgumentException{
        List<String> errors = user.validateObject();
        if(errors != null && !errors.isEmpty()){
            throw new InvalidArgumentException(String.join(",",errors));
        }
        user = userService.addUser(user);
        BaseHttpResponse response = BaseHttpResponseBuilder.successResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/update/{userid}")
    @ApiOperation(value = "This API endpoint updates an user in the system", httpMethod = HttpConstant.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK,response = GenericUserDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR,response = ExceptionDto.class)
    })
    public ResponseEntity<BaseHttpResponse> updateUser(@PathVariable long userid, @RequestBody GenericUserDto user){
        user = userService.updateUser(userid, user);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(user);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{userid}")
    @ApiOperation(value = "This API endpoint deletes an user in the system", httpMethod = HttpConstant.DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = GenericUserDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    public ResponseEntity<BaseHttpResponse> deleteUser(@PathVariable long userid){
        GenericUserDto user = userService.deleteUser(userid);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(user);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
}
