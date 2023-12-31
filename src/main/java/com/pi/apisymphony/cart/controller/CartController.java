package com.pi.apisymphony.cart.controller;

import com.pi.apisymphony.cart.service.CartService;
import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.constans.HttpConstant;
import com.pi.apisymphony.dto.ExceptionDto;
import com.pi.apisymphony.cart.dto.GenericCartDto;
import com.pi.apisymphony.exception.InvalidArgumentException;
import com.pi.apisymphony.exception.NoDataFoundException;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.response.BaseHttpResponse;
import com.pi.apisymphony.response.BaseHttpResponseBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
@SuppressWarnings("unused")
public class CartController {
    private final CartService cartService;
    @ApiOperation(value = "This API endpoint retrieves all the cart", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = HttpConstant.OK,responseContainer = ConstantsUtil.CONTAINER_LIST, response = GenericCartDto.class),
            @ApiResponse(code = 500,message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/all")
    public ResponseEntity<BaseHttpResponse> allCarts(){
        List<GenericCartDto> carts = cartService.allCarts();
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(carts);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint retrieves a cart based on id", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = HttpConstant.OK,response = GenericCartDto.class),
            @ApiResponse(code = 404,message = HttpConstant.NOT_FOUND, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseHttpResponse> getCartById(@PathVariable Long id) throws NotFoundException {
        GenericCartDto genericCartDto = cartService.getCartById(id);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(genericCartDto);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint retrieves limited carts", notes = "If limit is less then or equal to zero then limit will be default = 5", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = GenericCartDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR,response = ExceptionDto.class)
    })
    @GetMapping("/limit")
    public ResponseEntity<BaseHttpResponse> getCarts(@RequestParam int limit){
        List<GenericCartDto> limitedCarts = cartService.getCarts(limit);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(limitedCarts);
        return new ResponseEntity<>(baseHttpResponse,HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint retrieves carts in the sorted order",notes = "asc/desc, default mode and not equal to asc/desc order is ascending",httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = GenericCartDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/sort")
    public ResponseEntity<BaseHttpResponse> getSortedCarts(@RequestParam String sort){
        List<GenericCartDto> sortedCarts = cartService.getSortedCarts(sort);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(sortedCarts);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint retrieves all the carts of a user",httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK,response = GenericCartDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 404, message = HttpConstant.NOT_FOUND, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<BaseHttpResponse> getUserCarts(@PathVariable Long userId) throws NoDataFoundException {
        List<GenericCartDto> userCarts = cartService.getUserCarts(userId);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(userCarts);
        return new ResponseEntity<>(baseHttpResponse,HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint adds a new cart in the system",
            notes = "remember that nothing in real will insert into the database. so if you want to access the new id you will get a 404 error",
            httpMethod = HttpConstant.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = HttpConstant.CREATED,response = GenericCartDto.class),
            @ApiResponse(code = 400,message = HttpConstant.BAD_REQUEST, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseHttpResponse> addCart(@RequestBody @NonNull GenericCartDto genericCartDto) throws InvalidArgumentException{
        List<String> validated = genericCartDto.validate();
        if(validated != null && !validated.isEmpty()){
            throw new InvalidArgumentException(String.join(", ",validated));
        }
        GenericCartDto cartResponse = cartService.addCart(genericCartDto);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(cartResponse);
        return new ResponseEntity<>(baseHttpResponse,HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint updates a cart in the system", notes = "Nothing in real will update in the database", httpMethod = HttpConstant.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = HttpConstant.OK, response = GenericCartDto.class),
            @ApiResponse(code = 500,message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @PutMapping("/update/{cartId}")
    public ResponseEntity<BaseHttpResponse> updateCart(@PathVariable Long cartId,@RequestBody GenericCartDto genericCartDto){
        GenericCartDto cartResponse = cartService.updateCart(cartId,genericCartDto);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(cartResponse);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint deletes a cart in the system",notes = "The cart will not be deleted on the database", httpMethod = HttpConstant.DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = HttpConstant.OK, response = GenericCartDto.class),
            @ApiResponse(code = 500,message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<BaseHttpResponse> deleteCart(@PathVariable long cartId){
        GenericCartDto genericCartDto = cartService.deleteCart(cartId);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(genericCartDto);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint retrieves all the carts between the dates",
            notes = "If you don't add any start date it will fetch from the beginning of time and if you don't add any end date it will fetch until now.",
            httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = HttpConstant.OK, response = GenericCartDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 400, message = HttpConstant.BAD_REQUEST,response = ExceptionDto.class),
            @ApiResponse(code = 500,message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/range/date")
    public ResponseEntity<BaseHttpResponse> getCartsInDateRange(@RequestParam String startDate, @RequestParam String endDate){
        List<GenericCartDto> genericCarts = cartService.getCartsInDateRange(startDate,endDate);
        BaseHttpResponse baseHttpResponse = BaseHttpResponseBuilder.successResponse(genericCarts);
        return new ResponseEntity<>(baseHttpResponse, HttpStatus.OK);
    }
}

