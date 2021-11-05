package com.springframework.springmvcrest.api.model;

import lombok.Data;

import java.util.List;

@Data
public class VendorListProductsDTO {
    private List<ProductDTO> products;
}