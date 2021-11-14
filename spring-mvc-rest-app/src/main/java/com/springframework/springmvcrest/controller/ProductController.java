package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.api.model.ProductListDTO;
import com.springframework.springmvcrest.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Product", description = "Product API")
@RestController
@RequestMapping(ProductController.PRODUCT_BASE_URL)
public class ProductController {
    public static final String PRODUCT_BASE_URL = "/api/products";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "List all products")
    @ApiResponse(responseCode = "200", description = "Found products",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ProductListDTO.class))))
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found product",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@Parameter(description = "Product id", required = true) @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new product",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @Operation(summary = "Update existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated product",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "409", description = "Product not found", content = @Content)})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@Parameter(description = "Product id", required = true) @PathVariable Long id,
                                    @Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @Operation(summary = "Patch existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patched product",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "409", description = "Product not found", content = @Content)})
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO patchProduct(@Parameter(description = "Product id", required = true) @PathVariable Long id,
                                   @Valid @RequestBody ProductDTO productDTO) {
        return productService.patchProduct(id, productDTO);
    }

    @Operation(summary = "Delete existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted product", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@Parameter(description = "Product id", required = true) @PathVariable Long id) {
        productService.deleteProductById(id);
    }
}