package com.springframework.springmvcrest.api.mapper;

import com.springframework.springmvcrest.api.model.BasicProductDTO;
import com.springframework.springmvcrest.api.model.ProductDTO;
import com.springframework.springmvcrest.api.model.VendorProductDTO;
import com.springframework.springmvcrest.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    BasicProductDTO productToBasicProductDTO(Product product);

    ProductDTO productToProductDTO(Product product);
    Product productDTOtoProduct(ProductDTO productDTO);

    VendorProductDTO productToVendorProductDTO(Product product);
    Product vendorProductDTOtoProduct(VendorProductDTO vendorProductDTO);
}