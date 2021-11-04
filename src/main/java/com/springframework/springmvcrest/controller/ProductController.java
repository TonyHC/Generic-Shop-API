package com.springframework.springmvcrest.controller;

import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.api.model.ProductListDTO;
import com.springframework.springmvcrest.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductController.PRODUCT_BASE_URL)
public class ProductController {
    public static final String PRODUCT_BASE_URL = "/api/products";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getAllProducts() {
        return new ProductListDTO(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO patchProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.patchProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}