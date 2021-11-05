package com.springframework.springmvcrest.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String category;
}