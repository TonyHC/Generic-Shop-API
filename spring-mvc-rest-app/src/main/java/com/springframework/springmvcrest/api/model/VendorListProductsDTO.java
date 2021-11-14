package com.springframework.springmvcrest.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class VendorListProductsDTO {
    @Schema(description = "List of vendor products")
    private List<VendorProductDTO> products;
}