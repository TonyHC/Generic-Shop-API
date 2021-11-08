package com.springframework.springmvcrest.service;

import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.api.model.ProductListDTO;

public interface ProductService {
    ProductListDTO getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO createNewProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    ProductDTO patchProduct(Long id, ProductDTO productDTO);
    void deleteProductById(Long id);
}