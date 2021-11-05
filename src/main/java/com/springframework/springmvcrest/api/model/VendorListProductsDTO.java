package com.springframework.springmvcrest.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VendorListProductsDTO {
    @ApiModelProperty(value = "List of vendor products")
    private List<VendorProductDTO> products;
}