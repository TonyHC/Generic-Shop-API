package com.springframework.springmvcrest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductListDTO {
    @Schema(description = "List of products info")
    List<BasicProductDTO> products;
}