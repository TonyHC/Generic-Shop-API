package com.springframework.springmvcrest.api.mapper;

import com.springframework.springmvcrest.api.model.BasicProductDTO;
import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    BasicProductDTO productToBasicProductDTO(Product product);
    Product basicProductDTOtoProduct(BasicProductDTO basicProductDTO);

    ProductDTO productToProductDTO(Product product);
    Product productDTOtoProduct(ProductDTO productDTO);
}