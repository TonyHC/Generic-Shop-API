package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.api.model.ProductListDTO;
import com.springframework.springmvcrest.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @ApiResponse(code = 200, message = "Found products",
            response = ProductListDTO.class, responseContainer = "List")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAllProducts() {
        return productService.getAllProducts();
    }

    @ApiOperation(value = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Product not found") })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@ApiParam(value = "Product id", required = true) @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @ApiOperation(value = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created new product"),
            @ApiResponse(code = 400, message = "Invalid request")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @ApiOperation(value = "Update existing product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 409, message = "Product not found")})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@ApiParam(value = "Product id", required = true) @PathVariable Long id,
                                    @Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @ApiOperation(value = "Patch existing product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patched product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 409, message = "Product not found")})
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO patchProduct(@ApiParam(value = "Product id", required = true) @PathVariable Long id,
                                   @Valid @RequestBody ProductDTO productDTO) {
        return productService.patchProduct(id, productDTO);
    }

    @ApiOperation(value = "Delete existing product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted product"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Product not found") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@ApiParam(value = "Product id", required = true) @PathVariable Long id) {
        productService.deleteProductById(id);
    }
}