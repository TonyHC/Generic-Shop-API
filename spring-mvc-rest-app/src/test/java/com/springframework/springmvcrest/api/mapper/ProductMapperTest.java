package com.springframework.springmvcrest.api.mapper;

import com.springframework.springmvcrest.api.model.BasicProductDTO;
import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.domain.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    public static final long ID = 1L;
    public static final String NAME = "Orange";
    public static final String PRICE = "1.22";
    public static final String CATEGORY = "Fruits";

    final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    void productToBasicProductDTO() {
        // Given
        Product product = new Product();
        product.setId(ID);
        product.setName(NAME);
        product.setPrice(new BigDecimal(PRICE));
        product.setCategoryName(CATEGORY);

        // When
        BasicProductDTO basicProductDTO = productMapper.productToBasicProductDTO(product);

        // Then
        assertEquals(ID, basicProductDTO.getId());
        assertEquals(NAME, basicProductDTO.getName());
    }

    @Test
    void productDTOtoProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(ID);
        productDTO.setName(NAME);
        productDTO.setPrice(new BigDecimal(PRICE));

        // When
        Product product = productMapper.productDTOtoProduct(productDTO);

        // Then
        assertEquals(NAME, product.getName());
        assertEquals(PRICE, product.getPrice().toString());
    }
}