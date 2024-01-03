package com.pi.apisymphony.product.controller;

import com.pi.apisymphony.constans.ConstantsUtil;
import com.pi.apisymphony.constans.HttpConstant;
import com.pi.apisymphony.dto.ExceptionDto;
import com.pi.apisymphony.dto.GenericProductDto;
import com.pi.apisymphony.exception.InvalidArgumentException;
import com.pi.apisymphony.exception.NotFoundException;
import com.pi.apisymphony.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@SuppressWarnings("unused")
public class ProductController {
    private final ProductService productService;
    private final Logger logger;
    
    @ApiOperation(value = "This API endpoint retrieves a product based on id", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = GenericProductDto.class),
            @ApiResponse(code = 404, message = HttpConstant.NOT_FOUND, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GenericProductDto> getProductById(@PathVariable(value = "id") Long productId) throws NotFoundException, HttpServerErrorException.InternalServerError {
        GenericProductDto genericProductDto = productService.getProductById(productId);
        return new ResponseEntity<>(genericProductDto, HttpStatus.OK);
    }
    
    @ApiOperation(value = "This API endpoint retrieves a list of all products from the system with a default size of 50 products per request", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = GenericProductDto.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<GenericProductDto>> getAllProducts() throws HttpServerErrorException.InternalServerError , NullPointerException{
        List<GenericProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    @ApiOperation(value = "This API endpoint retrieves a list of products from the system based on the specified limit", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, responseContainer = ConstantsUtil.CONTAINER_LIST, response = GenericProductDto.class),
            @ApiResponse(code = 400, message = HttpConstant.BAD_REQUEST, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class),
            @ApiResponse(code = 404, message = HttpConstant.NOT_FOUND, response = ExceptionDto.class)
    })
    @GetMapping("")
    public ResponseEntity<List<GenericProductDto>> getProducts(@RequestParam(value = "limit") Integer limit) throws HttpServerErrorException.InternalServerError {
        List<GenericProductDto> genericProducts = productService.getProducts(limit);
        return new ResponseEntity<>(genericProducts, HttpStatus.OK);
    }
    
    @ApiOperation(value = "This API endpoint retrieves a comprehensive list of all categories available within the product system", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, response = String.class, responseContainer = ConstantsUtil.CONTAINER_LIST),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() throws HttpServerErrorException.InternalServerError {
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }
    
    @ApiOperation(value = "This API endpoint retrieves a list of products that belong to a specific category within the system", httpMethod = HttpConstant.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpConstant.OK, responseContainer = ConstantsUtil.CONTAINER_LIST, response = GenericProductDto.class),
            @ApiResponse(code = 404, message = HttpConstant.NOT_FOUND, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<GenericProductDto>> getProductsInACategory(@PathVariable(value = "category") String category) throws HttpServerErrorException.InternalServerError {
        List<GenericProductDto> response = productService.getProductsInACategory(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @ApiOperation(value = "This API endpoint creates new product in the system",httpMethod = HttpConstant.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = HttpConstant.CREATED,response = GenericProductDto.class),
            @ApiResponse(code = 400,message = HttpConstant.BAD_REQUEST, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @PostMapping("/add")
    public ResponseEntity<GenericProductDto> addProduct(@RequestBody GenericProductDto genericProductDto) throws InvalidArgumentException {
        if(genericProductDto == null || genericProductDto.validate()){
            logger.error("product is null");
            throw new InvalidArgumentException("Product cannot be null");
        }
        genericProductDto = productService.addProduct(genericProductDto);
        return new ResponseEntity<>(genericProductDto,HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "This API endpoint updates an existing product based on id",httpMethod = HttpConstant.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = HttpConstant.OK, response = GenericProductDto.class),
            @ApiResponse(code = 500,message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GenericProductDto> updateProduct(@PathVariable Long id, @RequestBody GenericProductDto genericProductDto){
        genericProductDto = productService.updateProduct(id,genericProductDto);
        return new ResponseEntity<>(genericProductDto,HttpStatus.OK);
    }
    @ApiOperation(value = "This API endpoint deletes an existing product based on id", httpMethod = HttpConstant.DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = HttpConstant.OK, response = GenericProductDto.class),
            @ApiResponse(code = 404, message = HttpConstant.NOT_FOUND, response = ExceptionDto.class),
            @ApiResponse(code = 500, message = HttpConstant.SERVER_ERROR, response = ExceptionDto.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericProductDto> deleteProduct(@PathVariable Long id) throws NotFoundException{
        GenericProductDto genericProductDto = productService.deleteProduct(id);
        return new ResponseEntity<>(genericProductDto,HttpStatus.OK);
    }
}

