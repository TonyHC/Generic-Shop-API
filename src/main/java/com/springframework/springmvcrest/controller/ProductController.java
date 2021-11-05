package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.api.model.ProductListDTO;
import com.springframework.springmvcrest.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Product", description = "Product API")
@RestController
@RequestMapping(ProductController.PRODUCT_BASE_URL)
public class ProductController {
    public static final String PRODUCT_BASE_URL = "/api/products";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "List all products")
    @ApiResponse(code = 200, message = "Successfully retrieved products",
            response = ProductListDTO.class, responseContainer = "List")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAllProducts() {
        return new ProductListDTO(productService.getAllProducts());
    }

    @ApiOperation(value = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Product not found") })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @ApiOperation(value = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created new product"),
            @ApiResponse(code = 400, message = "Invalid request")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @ApiOperation(value = "Update existing product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Product not found") })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @ApiOperation(value = "Patch existing product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully patched product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Product not found") })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.patchProduct(id, productDTO);
    }

    @ApiOperation(value = "Delete existing product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Product not found") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}