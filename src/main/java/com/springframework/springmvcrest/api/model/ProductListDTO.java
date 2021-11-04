package com.springframework.springmvcrest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductListDTO {
    List<BasicProductDTO> products;
}